package com.eat.untils;

import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean isEmpty(String text){
        return text==null||text.length()==0;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").substring(0,15);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if ((phoneNumber != null) && (!phoneNumber.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
        }
        return false;
    }


    public static void main(String[] args) {

    }

}
