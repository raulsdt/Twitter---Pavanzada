/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redsocial;
import java.util.Date;
/**
 *
 * @author raul
 */
public class Mensaje {
    private Date fecha;
    private String contenido;

    public Mensaje(String contenido){
        fecha= new Date();
        this.setContenido(contenido);

    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    private void setFecha(Date fecha) {
        this.fecha = fecha;
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
}
