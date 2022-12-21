package com.api.cartaovr.util;

import org.springframework.stereotype.Component;

@Component
public class ValidacaoUtil {

    private static boolean isInteger(String str) {
        return str != null && str.matches("[0-9]*");
    }

    public boolean verificacaoNumeros(String numero){
        numero = numero.replace(" ", "");
        if(numero.length() == 16){
            return isInteger(numero);
        }
        return false;
    }



}
