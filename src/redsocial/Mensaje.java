/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redsocial;
import java.util.*;

/**
 *
 * @author raul
 */
public class Mensaje {
    private Date fecha;
    private String contenido;
    private Usuario emisor;
    private boolean  publicMessage; //Lo podria haber realizado tambien con un
    //enum


    public Mensaje(String contenido, Usuario aUsuario){
        fecha= new Date();
        this.setContenido(contenido);
        this.setEmisor(aUsuario);
        this.publicMessage = true;
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
