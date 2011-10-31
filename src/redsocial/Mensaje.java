/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial;

import java.util.*;

/**
 *
 * @author Raul Salazar de Torres
 * @date 18/10/2011
 * @signature Programacion Avanzada
 * @file Mensaje.java
 * 
 */
public class Mensaje {

    private Date fecha;
    private String contenido;
    private Usuario emisor;


    /**
     * @see Constructor de Mensaje
     * @param contenido Texto del mensaje
     * @param aUsuario Emisor del mensaje
     * @exception Ambos cambos son obligatorios
     */
    public Mensaje(String contenido, Usuario aUsuario) {
        fecha = new Date();
        this.setContenido(contenido);
        this.setEmisor(aUsuario);

    }

    /**
     * @see Obtiene la fecha del mensaje
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @see Obtiene el emisor del mensaje
     * @return Usuario que envia este mensaje
     */ 
    public Usuario getEmisor() {
        return emisor;
    }

    /**
     * @see Introduce el emisor del mensaje
     */ 
    private void setEmisor(Usuario aUsuario) {
        this.emisor = aUsuario;
    }

    /**
     * @see Obtiene el contenido del mensaje
     * @return the contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * @see Permite introducir el contenido del mensaje
     * @param contenido the contenido to set
     */
    private void setContenido(String contenido) {
        this.contenido = contenido;
    }


}
