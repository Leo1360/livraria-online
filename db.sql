-- livraria.cartao definition

CREATE TABLE `cartao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) DEFAULT NULL,
  `cvv` varchar(255) DEFAULT NULL,
  `digitos` varchar(255) DEFAULT NULL,
  `vencimento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.categoria_livro definition

CREATE TABLE `categoria_livro` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.cidade definition

CREATE TABLE `cidade` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `uf` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.produto definition

CREATE TABLE `produto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `path_img` varchar(255) DEFAULT NULL,
  `valor` decimal(38,2) DEFAULT NULL,
  `ano` varchar(255) DEFAULT NULL,
  `autor` varchar(255) DEFAULT NULL,
  `cog_barras` varchar(255) DEFAULT NULL,
  `dimensao` varchar(255) DEFAULT NULL,
  `edicao` varchar(255) DEFAULT NULL,
  `editora` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `numero_paginas` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.`user` definition

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.endereco definition

CREATE TABLE `endereco` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `logradouro` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `cidade_id` bigint DEFAULT NULL,
  `preferencial` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8b1kcb3wucapb8dejshyn5fsx` (`cidade_id`),
  CONSTRAINT `FK8b1kcb3wucapb8dejshyn5fsx` FOREIGN KEY (`cidade_id`) REFERENCES `cidade` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.item_compra definition

CREATE TABLE `item_compra` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `qnt` int NOT NULL,
  `qnt_devolvida` int NOT NULL,
  `valor_unit` decimal(38,2) DEFAULT NULL,
  `produto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhi8mnlw3bmx5g5rs232xx4i3g` (`produto_id`),
  CONSTRAINT `FKhi8mnlw3bmx5g5rs232xx4i3g` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.pagamento definition

CREATE TABLE `pagamento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `valor` decimal(38,2) DEFAULT NULL,
  `cartao_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnc0od2p4xebsykrva9rwqkedn` (`cartao_id`),
  CONSTRAINT `FKnc0od2p4xebsykrva9rwqkedn` FOREIGN KEY (`cartao_id`) REFERENCES `cartao` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.produto_categoria definition

CREATE TABLE `produto_categoria` (
  `produto_id` bigint NOT NULL,
  `categoria_id` bigint NOT NULL,
  KEY `FKrv7yj7c855ons47r3vrujj12y` (`categoria_id`),
  KEY `FK1c0y58d3n6x3m6euv2j3h64vt` (`produto_id`),
  CONSTRAINT `FK1c0y58d3n6x3m6euv2j3h64vt` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`),
  CONSTRAINT `FKrv7yj7c855ons47r3vrujj12y` FOREIGN KEY (`categoria_id`) REFERENCES `categoria_livro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.relatorio_venda definition

CREATE TABLE `relatorio_venda` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `qnt_vendas` int NOT NULL,
  `produto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1rb8dslb1r3xv212olosw61s4` (`produto_id`),
  CONSTRAINT `FK1rb8dslb1r3xv212olosw61s4` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.resumo_venda definition

CREATE TABLE `resumo_venda` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `qnt_vendas` int NOT NULL,
  `produto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3jr6m076pltf6nxhjwdfiurq1` (`produto_id`),
  CONSTRAINT `FK3jr6m076pltf6nxhjwdfiurq1` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.cliente definition

CREATE TABLE `cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) DEFAULT NULL,
  `data_nascimento` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `cartao_preferencial_id` bigint DEFAULT NULL,
  `endereco_preferencial_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t1a18li06719gqn3piuksrndr` (`user_id`),
  KEY `FKiavw5esgqyb1we70wuyqghk9q` (`cartao_preferencial_id`),
  KEY `FKg8trq4bgcq7tl293h6i82l1d4` (`endereco_preferencial_id`),
  CONSTRAINT `FK5r88l8w8myx2otbcaovhf1jrb` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKg8trq4bgcq7tl293h6i82l1d4` FOREIGN KEY (`endereco_preferencial_id`) REFERENCES `endereco` (`id`),
  CONSTRAINT `FKiavw5esgqyb1we70wuyqghk9q` FOREIGN KEY (`cartao_preferencial_id`) REFERENCES `cartao` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.cliente_cartoes definition

CREATE TABLE `cliente_cartoes` (
  `cliente_id` bigint NOT NULL,
  `cartoes_id` bigint NOT NULL,
  UNIQUE KEY `UK_aa180i1b34qxs31xewuuc18wd` (`cartoes_id`),
  KEY `FKdhvuxxongeeg8vmmo9xtkk4rb` (`cliente_id`),
  CONSTRAINT `FKc1mknp4gg0hecuwxfw0rp6opp` FOREIGN KEY (`cartoes_id`) REFERENCES `cartao` (`id`),
  CONSTRAINT `FKdhvuxxongeeg8vmmo9xtkk4rb` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.cliente_enderecos_entrega definition

CREATE TABLE `cliente_enderecos_entrega` (
  `cliente_id` bigint NOT NULL,
  `enderecos_entrega_id` bigint NOT NULL,
  UNIQUE KEY `UK_co236hpiwjtcy5c1bqtanhndr` (`enderecos_entrega_id`),
  KEY `FKvgqg2mi8m0vschanr1tw5afk` (`cliente_id`),
  CONSTRAINT `FK7hcs42eid81adtjapika3y4p9` FOREIGN KEY (`enderecos_entrega_id`) REFERENCES `endereco` (`id`),
  CONSTRAINT `FKvgqg2mi8m0vschanr1tw5afk` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.cupom definition

CREATE TABLE `cupom` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) NOT NULL,
  `desconto` decimal(38,2) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `tipo` tinyint DEFAULT NULL,
  `cliente_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgq3lf4kud8ahifr3f6a8q6n1d` (`cliente_id`),
  CONSTRAINT `FKgq3lf4kud8ahifr3f6a8q6n1d` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `cupom_chk_1` CHECK ((`tipo` between 0 and 1))
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.pedido definition

CREATE TABLE `pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint DEFAULT NULL,
  `endereco_entrega_id` bigint DEFAULT NULL,
  `data` date DEFAULT NULL,
  `frete` decimal(38,2) DEFAULT NULL,
  `desconto` decimal(38,2) DEFAULT NULL,
  `cupom_id` bigint DEFAULT NULL,
  `sub_total` decimal(38,2) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `total` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK30s8j2ktpay6of18lbyqn3632` (`cliente_id`),
  KEY `FKcrxxe5rpllxbh0sfi4a6rwphb` (`endereco_entrega_id`),
  KEY `FKo3qrib5ye8a3maf9aixhhcpic` (`cupom_id`),
  CONSTRAINT `FK30s8j2ktpay6of18lbyqn3632` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FKcrxxe5rpllxbh0sfi4a6rwphb` FOREIGN KEY (`endereco_entrega_id`) REFERENCES `endereco` (`id`),
  CONSTRAINT `FKo3qrib5ye8a3maf9aixhhcpic` FOREIGN KEY (`cupom_id`) REFERENCES `cupom` (`id`),
  CONSTRAINT `pedido_chk_1` CHECK ((`status` between 0 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.pedido_cupom definition

CREATE TABLE `pedido_cupom` (
  `pedido_id` bigint NOT NULL,
  `cupom_id` bigint NOT NULL,
  KEY `FKs5yi8jnl1nutk6r3qscla6uhu` (`cupom_id`),
  KEY `FK3xn3fd4312mwt7aevqv560na1` (`pedido_id`),
  CONSTRAINT `FK3xn3fd4312mwt7aevqv560na1` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  CONSTRAINT `FKs5yi8jnl1nutk6r3qscla6uhu` FOREIGN KEY (`cupom_id`) REFERENCES `cupom` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.pedido_cupons definition

CREATE TABLE `pedido_cupons` (
  `pedido_id` bigint NOT NULL,
  `cupons_id` bigint NOT NULL,
  KEY `FKsdnnsqdgsbuucood1rfwqrbe6` (`cupons_id`),
  KEY `FK27elh00c6b3i2hj8us0yq5ek8` (`pedido_id`),
  CONSTRAINT `FK27elh00c6b3i2hj8us0yq5ek8` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  CONSTRAINT `FKsdnnsqdgsbuucood1rfwqrbe6` FOREIGN KEY (`cupons_id`) REFERENCES `cupom` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.pedido_itens definition

CREATE TABLE `pedido_itens` (
  `pedido_id` bigint NOT NULL,
  `itens_id` bigint NOT NULL,
  UNIQUE KEY `UK_mu8rg6jm44j40igjdcg5jvh5k` (`itens_id`),
  KEY `FKb5t2ga17uxph7bp32w97ssu4t` (`pedido_id`),
  CONSTRAINT `FK3aixt7s3if028ynogeejohudt` FOREIGN KEY (`itens_id`) REFERENCES `item_compra` (`id`),
  CONSTRAINT `FKb5t2ga17uxph7bp32w97ssu4t` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.pedido_pagamento_list definition

CREATE TABLE `pedido_pagamento_list` (
  `pedido_id` bigint NOT NULL,
  `pagamento_list_id` bigint NOT NULL,
  UNIQUE KEY `UK_ebgp30jn0m2y8owhg67nutpb` (`pagamento_list_id`),
  KEY `FK68l26wu2spw9mi8cjhajhgsor` (`pedido_id`),
  CONSTRAINT `FK68l26wu2spw9mi8cjhajhgsor` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  CONSTRAINT `FKrap7ntv9ng7ifiplo66hdod9u` FOREIGN KEY (`pagamento_list_id`) REFERENCES `pagamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.retorno_mercadoria definition

CREATE TABLE `retorno_mercadoria` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `motivo` varchar(255) DEFAULT NULL,
  `qnt` int NOT NULL,
  `tipo` tinyint DEFAULT NULL,
  `valor` decimal(38,2) DEFAULT NULL,
  `item_compra_id` bigint DEFAULT NULL,
  `pedido_id` bigint DEFAULT NULL,
  `cupom_id` bigint DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qy9v1fj4m5xt3diu173mhsyqa` (`cupom_id`),
  KEY `FKirepm422dhae5bpihgfwhjuuf` (`item_compra_id`),
  KEY `FKbubqmgm7k91ly1mt0e4uqxo83` (`pedido_id`),
  CONSTRAINT `FKbubqmgm7k91ly1mt0e4uqxo83` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  CONSTRAINT `FKirepm422dhae5bpihgfwhjuuf` FOREIGN KEY (`item_compra_id`) REFERENCES `item_compra` (`id`),
  CONSTRAINT `FKsepqmdp4lvphjwkxxmtvibvab` FOREIGN KEY (`cupom_id`) REFERENCES `cupom` (`id`),
  CONSTRAINT `retorno_mercadoria_chk_1` CHECK ((`tipo` between 0 and 1)),
  CONSTRAINT `retorno_mercadoria_chk_2` CHECK ((`status` between 0 and 4))
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- livraria.troca definition

CREATE TABLE `troca` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `qnt` int NOT NULL,
  `valor` double NOT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `item_compra_id` bigint DEFAULT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `pedido_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrm36qdhgchj4wt6y6v8bv66qg` (`cliente_id`),
  KEY `FKn70983jvjmpgily1ahti6x8tg` (`item_compra_id`),
  KEY `FKstyfmfcub9m90i3pk7qintved` (`pedido_id`),
  CONSTRAINT `FKn70983jvjmpgily1ahti6x8tg` FOREIGN KEY (`item_compra_id`) REFERENCES `item_compra` (`id`),
  CONSTRAINT `FKrm36qdhgchj4wt6y6v8bv66qg` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FKstyfmfcub9m90i3pk7qintved` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

---------Procedures-------
CREATE DEFINER=`root`@`%` PROCEDURE `livraria`.`get_product_sales_by_day`(in product int, in date_day date)
BEGIN
	SELECT p.`data`,sum(ic.qnt) as valor from pedido p 
	inner join pedido_itens pitem ON pitem.pedido_id = p.id 
	INNER JOIN item_compra ic on ic.id  = pitem.itens_id 
	WHERE ic.produto_id= product AND p.`data` = date_day GROUP BY p.`data` ;
END;

CREATE DEFINER=`root`@`%` PROCEDURE `livraria`.`get_sales_of`(in product int, in ini_date date, in end_date date)
BEGIN
	SELECT p.`data`,sum(ic.qnt) as valor from pedido p 
	inner join pedido_itens pitem ON pitem.pedido_id = p.id 
	INNER JOIN item_compra ic on ic.id  = pitem.itens_id 
	WHERE ic.produto_id= product AND p.`data` BETWEEN ini_date and end_date GROUP BY p.`data` ;	
END;