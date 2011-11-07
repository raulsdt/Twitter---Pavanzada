/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author raul
 */
public class MensajeVacio extends RuntimeException {
    public MensajeVacio(String e){
        System.out.println(e);
    }
}
