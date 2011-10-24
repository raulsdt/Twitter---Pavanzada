/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial;

import java.util.*;
import Utilidades.Logger;

/**
 *
 * @author Raul Salazar de Torres
 * @date 18/10/2011
 * @signature Programacion Avanzada
 * @file Usuario.java
 * 
 */
public class Usuario {
    private static final Logger LOGGER = Logger.getLogger("RedSocial"); //Logger
    private String email; //Correo electronico del usuario
    private String clave; // Clave del usuario
    private String nombre; //Nombre del usuario
    private String descripcion; //Descripcion del usuario
    private LinkedList<Usuario> solicitudesAmistad; //Conjunto de peticiones de 
    //amistad realizadas al usuario
    private LinkedList<Usuario> amigos;//Conjunto de amigos del usuario
    private LinkedList<Mensaje> mensajesRecibidos;//Conjunto de mensajes recibidos

    /**
     * @see Constructor de la clase
     * @param mail Correo electronico del usuario
     * @param clave Contraseña (encriptada)
     * @param nombre Nombre del usuario
     * @param descripcion Descripcion del mismio
     */
    public Usuario(String mail, String clave, String nombre, String descripcion) {

        //Asignamos las variables de la clase
        this.email = mail;
        this.clave = clave;
        this.nombre = nombre;
        this.descripcion = descripcion;

        solicitudesAmistad = new LinkedList<Usuario>();
        amigos = new LinkedList<Usuario>();
        mensajesRecibidos = new LinkedList<Mensaje>();
    }

    /**
     * @see Constructor sin parametros
     */
    public Usuario() {
        this.email = "";
        this.clave = "";
        this.nombre = "";
        this.descripcion = "";

        solicitudesAmistad = new LinkedList<Usuario>();
        amigos = new LinkedList<Usuario>();
        mensajesRecibidos = new LinkedList<Mensaje>();
    }

    /**
     * @see Contructor copia del usuario
     * @param u Usuario a introducir
     */
    public Usuario(Usuario u) {
        this.email = u.email;
        this.clave = u.clave;
        this.nombre = u.nombre;
        this.descripcion = u.descripcion;

        solicitudesAmistad = new LinkedList<Usuario>();
        amigos = new LinkedList<Usuario>();
        mensajesRecibidos = new LinkedList<Mensaje>();
    }

    /**
     * @see Validacion de la contraseña
     * @param clave Valor de la contraseña
     * @return Devuelve true si es correcta, de lo contrario devuelve false
     */
    public boolean claveValida(String clave) {
        return (this.getClave().equals(clave)); //Despues pasar a MD5 por ejemplo
    }

    /**
     * @see Introduce un mensaje en el muro del usuario y en el de todos sus amigos
     * @param msg Contenido del mensaje (texto)
     */
    public void mensajeMuro(String msg) {
        LOGGER.info("Nuevo mensaje en el muro" + msg);
        Mensaje mensaje = new Mensaje(msg, this);
        for (int i = 0; i < getAmigos().size(); i++) {
            UJaenSocial.nuevoMensaje(mensaje);//Agregar el mensaje dentro de la clase UJaenSocial
            amigos.get(i).recibirMensaje(mensaje);//Llamada a recibir mensaje
        }
        this.recibirMensaje(mensaje);// El mensaje tambien me lo tengo que enviar a mi mismo

    }

    /**
     * @see Envio de mensaje privado a un usuario
     * @param msg Contenido del mensaje en texto
     * @param amigo Usuario al que se le quiere enviar el mensaje
     * @throws Los campos anteriores son obligatorios
     */
    public void mensajeAmigo(String msg, Usuario amigo) {
        try {
            LOGGER.info("Mensaje privado" + amigo.getEmail() + " : "+ msg);
            MensajePrivado mensajePrivado = new MensajePrivado(msg, this, amigo);
            UJaenSocial.nuevoMensaje(mensajePrivado);//Agregar el mensaje dentro de la clase UJaenSocial
            amigo.recibirMensaje(mensajePrivado);     //Llamada a recibir mensaje
        } catch (Exception e) {
            System.out.println("No se puede enviar ningun mensaje");
        }
    }

    /**
     * @see Pedir ser amigo de cualquier usuario
     * @param u Usuario del cual se quiere ser amigo
     */
    public void solicitudAmistad(Usuario u) {//Le pido la solicitud de amistad a una pesona
        u.solicitudesAmistad.add(this);// getSolicitudesAmistad().push(u);
    }

    /**
     * @see Admisión de un amigo, previamente en la lista de solicitudesAmistad
     * @param Usuario a admitir
     */
    public void admitirAmigo(Usuario u) {//Acepto la solicitud de amistad de otra persona
        try {
            LOGGER.info("Admitir a amigo: " + email);
            ListIterator<Usuario> iterador = u.getSolicitudesAmistad().listIterator();
            boolean encontrado = false;


            while (!encontrado && iterador.hasNext()) {
                if (iterador.next().equals(u)) {
                    encontrado = true;
                    iterador.remove();
                }
            }

            u.getAmigos().add(this);//o push - u  El me añade como amigo a mi
            amigos.add(u);//Yo lo añado como amigo a él
        } catch (Exception e) {
            System.out.println("Error al admitir amigo");
        }
    }

    /**
     * @see Recepcion de mensaje
     * @param m Mensaje a recibir
     */
    public void recibirMensaje(Mensaje m) {
        getMensajesRecibidos().add(m);// o push
        LOGGER.info("Usuario: " + getEmail() + " recibe: " + m.getContenido());

    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the solicitudesAmistad
     */
    public LinkedList<Usuario> getSolicitudesAmistad() {
        return solicitudesAmistad;
    }

    /**
     * @return the amigos
     */
    public LinkedList<Usuario> getAmigos() {
        return amigos;
    }

    /**
     * @return the mensajesRecibidos
     */
    public LinkedList<Mensaje> getMensajesRecibidos() {
        return mensajesRecibidos;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
}
