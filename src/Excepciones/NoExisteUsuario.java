/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author raul
 */
public class NoExisteUsuario extends RuntimeException{
    public NoExisteUsuario(String e){
        System.out.println(e);
    }
}
