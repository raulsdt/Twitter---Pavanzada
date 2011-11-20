/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author raul
 */
public class UsuarioYaRegistrado extends RuntimeException {
    public UsuarioYaRegistrado(String e){
        System.out.println(e);
    }
}
