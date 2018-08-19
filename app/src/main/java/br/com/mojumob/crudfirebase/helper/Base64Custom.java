package br.com.mojumob.crudfirebase.helper;

import android.util.Base64;

public class Base64Custom {

    public static String codificarNaBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }

    public static String decodificaNaBase64(String textoCodificado){
        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }

}
