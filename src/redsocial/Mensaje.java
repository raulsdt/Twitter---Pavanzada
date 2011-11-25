/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial;

import Persistencia.ManejadorJPA;
import java.io.Serializable;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Raul Salazar de Torres
 * @date 18/10/2011
 * @signature Programacion Avanzada
 * @file Mensaje.java
 * 
 */

@Entity
@Table(name="Mensaje")
public class Mensaje implements Serializable {
    @Id
    @GeneratedValue
    protected long idMensaje;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Fecha")
    private Date fecha;
    @Column(name="Contenido")
    private String contenido;
    @OneToOne
    private Usuario emisor;

    public Mensaje(){
        
    }
    /**
     * Constructor de Mensaje
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
     * Obtiene la fecha del mensaje
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Obtiene el emisor del mensaje
     * @return Usuario que envia este mensaje
     */ 
    public Usuario getEmisor() {
        return emisor;
    }

    /**
     * Introduce el emisor del mensaje
     */ 
    private void setEmisor(Usuario aUsuario) {
        this.emisor = aUsuario;
        ManejadorJPA.instancia().em.getTransaction().begin();
        ManejadorJPA.instancia().em.merge(this);
        ManejadorJPA.instancia().em.getTransaction().commit();
    }

    /**
     * Obtiene el contenido del mensaje
     * @return the contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     *  Permite introducir el contenido del mensaje
     * @param contenido the contenido to set
     */
    protected void setContenido(String contenido) {
        this.contenido = contenido;
        ManejadorJPA.instancia().em.getTransaction().begin();
        ManejadorJPA.instancia().em.merge(this);
        ManejadorJPA.instancia().em.getTransaction().commit();
    }


}
