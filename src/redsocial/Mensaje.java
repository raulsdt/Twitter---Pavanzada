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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    /**
     * @param fecha the fecha to set
     */
    private void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    private void setEmisor(Usuario aUsuario) {
        this.emisor = aUsuario;
    }

    /**
     * @return the contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    private void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * @return the publicMessage
     */
    public boolean getPublicMessage() {
        return publicMessage;
    }

    /**
     * @param publicMessage the publicMessage to set
     */
    public void setPublicMessage(boolean publicMessage) {
        this.publicMessage = publicMessage;
    }
}
