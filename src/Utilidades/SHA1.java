/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

/**
 * Created by IntelliJ IDEA.
 * User: alopez
 * Date: 04-jul-2006
 * Time: 11:06:38
 * To change this template use File | Settings | File Templates.
 */
import sun.misc.BASE64Encoder;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SHA1 {

    public static String encriptarBase64(String textoplano) throws IllegalStateException {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA"); // Instancia de generador SHA-1
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage());
        }

        try {
            md.update(textoplano.getBytes("UTF-8")); // Generación de resumen de mensaje
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage());
        }

        byte raw[] = md.digest(); // Obtención del resumen de mensaje
        return (new BASE64Encoder()).encode(raw); // Traducción a BASE64
    }

    public static String encriptarHexadecimal(String textoplano) throws IllegalStateException {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA"); // Instancia de generador SHA-1
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage());
        }

        try {
            md.update(textoplano.getBytes("UTF-8")); // Generación de resumen de mensaje
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage());
        }

        byte raw[] = md.digest(); // Obtención del resumen de mensaje
        return toHexadecimal(raw); // Traducción a HEXADECIMAL
    }

//metodo que devuelve el valor hexadecimal (String) de una array de byte.
    private static String toHexadecimal(byte[] datos) {
        String resultado = "";
        ByteArrayInputStream input = new ByteArrayInputStream(datos);
        String cadAux;
        int leido = input.read();
        while (leido != -1) {
            cadAux = Integer.toHexString(leido);
            if (cadAux.length() < 2) //Hay que añadir un 0
            {
                resultado += "0";
                resultado += cadAux;
                leido = input.read();
            }
            return resultado;
        }
        return null;
    }
}
