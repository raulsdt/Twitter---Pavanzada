/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author raul
 */
public class NoEsAmigo extends RuntimeException{
    
    public NoEsAmigo(String Ex){
        System.out.println(Ex);
    }
}
