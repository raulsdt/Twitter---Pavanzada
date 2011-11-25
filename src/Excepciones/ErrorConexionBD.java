/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author raul
 */
public class ErrorConexionBD extends RuntimeException {
    public ErrorConexionBD(String e ){
        System.out.println(e);
    }
}
