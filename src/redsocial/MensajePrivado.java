/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial;

/**
 *
 * @author Raul Salazar de Torres
 * @date 18/10/2011
 * @signature Programacion Avanzada
 * @file MensajePrivado.java
 * 
 */
public class MensajePrivado extends Mensaje {

    private Usuario receptor; //Receptor del mensaje privado   

    /**
     * @see Constructor de mensaje privado
     * @param Contenido Texto del mensaje privado
     * @param emisor Creador del mensaje
     * @param ereceptor Destinatario del mensaje
     * @throws Todos los campos anteriores son obligatorios
     */
    public MensajePrivado(String Contenido, Usuario emisor, Usuario ereceptor) {
        super(Contenido, emisor); //Llamada al contructor de la superclase
        setReceptor(ereceptor);
    }

    /**
     * @return the receptor
     */
    public Usuario getReceptor() {
        return receptor;
    }

    /**
     * @param receptor the receptor to set
     */
    private void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }
}
