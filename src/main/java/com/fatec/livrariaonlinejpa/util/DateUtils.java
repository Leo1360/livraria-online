package com.fatec.livrariaonlinejpa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date yesterday(){
       return asDate(LocalDate.now().minusDays(1));
    }

    public static Date dateOf(String data, String formato){
        if(formato.isEmpty()){
            formato = "yyyy-MM-dd";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        Date d = null;
        try {
            d = formatter.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return d;
    }

    public static Date dateOf(String data){
        String formato = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        Date d = null;
        try {
            d = formatter.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return d;
    }
}
