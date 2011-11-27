/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial;

import java.util.*;
import Utilidades.Logger;
import Excepciones.*;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import Persistencia.*;
import javax.management.Query;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

/**
 *
 * @author Raul Salazar de Torres
 * @date 18/10/2011
 * @signature Programacion Avanzada
 * @file Usuario.java
 * 
 */
@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {

    private static final Logger LOGGER = Logger.getLogger("RedSocial"); //Logger
    @Id
    @Column(name = "email")
    private String email; //Correo electronico del usuario
    @Column(name = "clave")
    private String clave; // Clave del usuario
    @Column(name = "nombre")
    private String nombre; //Nombre del usuario
    @Column(name = "descripcion")
    private String descripcion; //Descripcion del usuario
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "solicitudes")
    private List<Usuario> solicitudesAmistad; //Conjunto de peticiones de 
    //amistad realizadas al usuario
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> amigos;//Conjunto de amigos del usuario
    @ManyToMany(fetch = FetchType.LAZY) //No se puede poner perezoso porque nada mas cargar muestro todos los mensajes
    private List<Mensaje> mensajesRecibidos;//Conjunto de mensajes recibidos

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

        solicitudesAmistad = new LinkedList<Usuario>(u.solicitudesAmistad);
        amigos = new LinkedList<Usuario>(u.amigos);
        mensajesRecibidos = new LinkedList<Mensaje>(u.mensajesRecibidos);
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
    public void mensajeMuro(String msg) throws MensajeVacio {
        LOGGER.info("Nuevo mensaje en el muro" + msg);
        if (!msg.equals("")) {
            Mensaje mensaje = new Mensaje(msg, this);
            UJaenSocial.nuevoMensaje(mensaje);//Agregar el mensaje dentro de la clase UJaenSocial
            for (int i = 0; i < getAmigos().size(); i++) {

                amigos.get(i).recibirMensaje(mensaje);//Llamada a recibir mensaje
            }
            this.recibirMensaje(mensaje);// El mensaje tambien me lo tengo que enviar a mi mismo
        } else {
            throw new MensajeVacio("El mensaje que quiere dar en el muro esta vacio");
        }

    }

    /**
     * @see Envio de mensaje privado a un usuario
     * @param msg Contenido del mensaje en texto
     * @param amigo Usuario al que se le quiere enviar el mensaje
     * @throws Los campos anteriores son obligatorios
     */
    public void mensajeAmigo(String msg, Usuario amigo) throws NoEsAmigo {
        if (amigo != null) {
            LOGGER.info("Mensaje privado" + amigo.getEmail() + " : " + msg);
            MensajePrivado mensajePrivado = new MensajePrivado(msg, this, amigo);

            UJaenSocial.nuevoMensaje(mensajePrivado);//Agregar el mensaje dentro de la clase UJaenSocial
            amigo.recibirMensaje(mensajePrivado);     //Llamada a recibir mensaje
        } else {
            throw new NoEsAmigo("El usuario introducido no es amigo");
        }
    }

    /**
     * @see Pedir ser amigo de cualquier usuario
     * @param u Usuario del cual se quiere ser amigo
     */
    public void solicitudAmistad(Usuario u) {//Le pido la solicitud de amistad a una pesona
        if (!existeSolicitudAmigo(u) && !existeAmigo(u) && !u.equals(this)) {
            u.solicitudesAmistad.add(this);// getSolicitudesAmistad().push(u);
            ManejadorJPA.instancia().em.getTransaction().begin();
            ManejadorJPA.instancia().em.merge(u);
            ManejadorJPA.instancia().em.getTransaction().commit();
        }
    }

    /**
     * @see Admisión de un amigo, previamente en la lista de solicitudesAmistad
     * @param Usuario a admitir
     */
    public void admitirAmigo(Usuario u) throws NoExisteUsuario {//Acepto la solicitud de amistad de otra persona

        LOGGER.info("Admitir a amigo: " + email);
        ListIterator<Usuario> iterador = getSolicitudesAmistad().listIterator();
        boolean encontrado = false;


        while (!encontrado && iterador.hasNext()) {
            if (iterador.next().equals(u)) {
                encontrado = true;
                // iterador.remove();
            }
        }
        if (encontrado) {
            u.getAmigos().add(this);//o push - u  El me añade como amigo a mi
            amigos.add(u);//Yo lo añado como amigo a él
            solicitudesAmistad.remove(solicitudesAmistad.lastIndexOf(u));
            ManejadorJPA.instancia().em.getTransaction().begin();
            ManejadorJPA.instancia().em.merge(this);
            ManejadorJPA.instancia().em.merge(u);
            ManejadorJPA.instancia().em.getTransaction().commit();
        } else {
            throw new NoExisteUsuario("El usuario introducido no existe en la lista de solicitud de amistad");
        }

    }

    public boolean existeSolicitudAmigo(Usuario u) {

        for (int i = 0; i < solicitudesAmistad.size(); i++) {
            if (solicitudesAmistad.get(i).email.equals(u.email)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeAmigo(Usuario u) {
        for (int i = 0; i < amigos.size(); i++) {
            if (amigos.get(i).email.equals(u.email)) {
                return true;
            }
        }
        return false;

    }

    /**
     * @see Recepcion de mensaje
     * @param m Mensaje a recibir
     */
    public void recibirMensaje(Mensaje m) {


        mensajesRecibidos.add(m);// o push modificadoo //getMensajesRecibidosss
        ManejadorJPA.instancia().em.getTransaction().begin();
        ManejadorJPA.instancia().em.merge(this);
        ManejadorJPA.instancia().em.getTransaction().commit();
        LOGGER.info("Usuario: " + getEmail() + " recibe: " + m.getContenido());


    }

    /**
     * Nos muestra el email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Inserta el email del usuario
     * @param email the email to set
     */
    public void setEmail(String email) {

        this.email = email;
        ManejadorJPA.instancia().em.getTransaction().begin();
        ManejadorJPA.instancia().em.merge(this);
        ManejadorJPA.instancia().em.getTransaction().commit();
    }

    /**
     * Nos ofrece la clave del usuario
     * @return the clave
     */
    protected String getClave() {
        return clave;
    }

    /**
     * Nos permite establecer la contraseña del usuario
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
        ManejadorJPA.instancia().em.getTransaction().begin();
        ManejadorJPA.instancia().em.merge(this);
        ManejadorJPA.instancia().em.getTransaction().commit();
    }

    /**
     * Nos muestra la descripcion del usuario
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        ManejadorJPA.instancia().em.getTransaction().begin();
        ManejadorJPA.instancia().em.merge(this);
        ManejadorJPA.instancia().em.getTransaction().commit();
    }

    /**
     * Nos aporta la lista de solicitudes de amistad de un usuario
     * @return the solicitudesAmistad
     */
    public List<Usuario> getSolicitudesAmistad() {
        return solicitudesAmistad;
    }

    /**
     * Nos aporta la lista de amigos de un usuario
     * @return the amigos
     */
    public List<Usuario> getAmigos() {
        return amigos;
    }

    /**
     * Obtener la lista de mensajes recibidos
     * @return the mensajesRecibidos
     */
    public List<Mensaje> getMensajesRecibidos() {
        //Query q = ManejadorJPA.instancia().em.createQuery("select u from Usuario u WHERE descripcion ='" + Termino + "'");
        //List<Usuario> devolucion = q.getResultList();
        return mensajesRecibidos;
    }

    /**
     * Devuelve el nombre de usuario
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
}
