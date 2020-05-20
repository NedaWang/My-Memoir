package com.example.memoir.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class DateUtil {

    public final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    public static String convertDobToServerFormat(String dob){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dobServerFormat = "";
        try {
            Date dateObject = sdf.parse(dob);
            dobServerFormat = formatter.format(dateObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally{
            return dobServerFormat;
        }
    }

    public static String toServerFormat(Date date){
        return formatter.format(date);
    }
}
