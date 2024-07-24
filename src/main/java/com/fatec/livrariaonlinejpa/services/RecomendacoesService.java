package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.ItemCompra;
import com.fatec.livrariaonlinejpa.model.Produto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecomendacoesService {
    private final ProdutoService produtoService;

    public JSONObject atualizarHistorico(String response, JSONObject historico){
        if(historico.isEmpty()){
            historico = new JSONObject("{\"model\":\"gpt-4o\",\"temperature\":1,\"messages\":[]}");
        }
        JSONObject msg = new JSONObject("{\"role\":\"user\",}");
        msg.put("content",response);

        historico.getJSONArray("messages").put(msg);
        return historico;
    }

    public void atualizarHistorico(JSONObject response, JSONObject historico){
        if(historico == null){
            historico = new JSONObject("{\"model\":\"gpt-4o\",\"temperature\":1,\"messages\":[]}");
        }
        historico.getJSONArray("messages").put(response);
    }

    private static Optional<String> sendRequestGPT(JSONObject body) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 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")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            return Optional.of(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
        } catch (IOException | InterruptedException e) {
            return Optional.empty();
        }
    }

    private JSONObject getBodyFirstCall(List<Produto> livros){
        StringBuilder prompt = new StringBuilder();
//        prompt.append("A partir de agora aja como um consultor de leitura, um profissional em recomendar livros. Você deve fazer  até 3 perguntas, uma por vez, para o cliente, ele ira responder e ao final você deverá retornar 3 dos títulos disponíveis abaixo que  mais fazem sentido com o gosto do cliente de acordo com a pergunta. Na resposta final os ao invés do nome voce devera informar o numero que aparece após o nome do livro, em negrito e em seguida um texto curto explicando o motivo da recomendação.\n" +
//                "Sempre que o cliente responder algo que não está relacionado a pergunta ou ao tema livros, você devera dizer \"Infelizmente só posso recomenda livros com base nas perguntas\" e em seguida refazer a pergunta. Em hipótese alguma permita a conversa a divergir do assunto livros.\n" +
//                "Livros disponiveis:");
        prompt.append("A partir de agora aja como um consultor de leitura, um profissional em recomendar livros. Você deve fazer  até 3 perguntas, uma por vez, para o cliente, ele ira responder e ao final você deverá retornar 3 dos títulos disponíveis abaixo que  mais fazem sentido com o gosto do cliente de acordo com a pergunta. Cada livros está acompanhado por um numero identificador após o titulo\n" +
                "A resposta final deve ser composta exclusivamente por:\n" +
                "- o numero identificador do livros, cercado por \"**\";\n" +
                "- motivo da recomendação\n" +
                "Sempre que o cliente responder algo que não está relacionado a pergunta ou ao tema livros, você devera dizer \"Infelizmente só posso recomenda livros com base nas perguntas\" e em seguida refazer a pergunta. Em hipótese alguma permita a conversa a divergir do assunto livros.");
        for(Produto prod: livros){
            prompt.append(prod.getNome()).append(" - ").append(prod.getId()).append("\n");
        }

        JSONObject body = atualizarHistorico(prompt.toString(),new JSONObject());

        return body;
    }
    public String iniciarChat(HttpSession session){
        List<Produto> livros = produtoService.findAll();
        JSONObject hist = getBodyFirstCall(livros);
        return getGptResponse(session, hist);
    }

    public String sendNewMsg(HttpSession session, String msg){
        JSONObject hist = (JSONObject) session.getAttribute("historico");
        atualizarHistorico(msg, hist);
        String ret = getGptResponse(session, hist);
        return tratarGptResponse(ret);
    }

    private String getGptResponse(HttpSession session, JSONObject hist) {
        Optional<String> strResp = sendRequestGPT(hist);
        if(strResp.isPresent()){
            JSONObject resp = new JSONObject(strResp.get());
            JSONObject msg = resp.getJSONArray("choices").getJSONObject(0).getJSONObject("message");
            atualizarHistorico(msg, hist);
            session.setAttribute("historico", hist);
            return msg.getString("content");
        }
        return null;
    }

    private String tratarGptResponse(String msg){
        String[] result = msg.split("\\*\\*");
        System.out.println(result.toString());
        if(result.length < 2){
            return msg;
        }
        HashMap<String, String> map = new HashMap<>();
        for(int i = 1; i< result.length; i = i+2){
            msg = msg.replaceAll(result[i+1],"\n");
            msg = msg.replaceAll("\\*\\*" + result[i] + "\\*\\*", prdNameToPrdLink(result[i],result[i+1]));

        }
        return msg;
    }

    private String prdNameToPrdLink(String s, String descricao) {
        s = s.replaceAll("[\\(\\)\\[\\]]","");
        long prdId = Long.parseLong(s);
        Produto prod = produtoService.findById(prdId);
        if(prod != null){
            return "<div class=\"card\"><div class=\"card-header\">" + prod.getNome() + "</div><div class=\"card-body\"><div class=\"row\"><div class=\"col-md-4\"><a href=\"/produto/" + prod.getId() + "\"><img src=\"" + prod.getPathImg() + "\"></a></div><div class=\"col-md-8\">" + descricao + "</div></div></div></div>\n";
        }
        return "";
    }

}
