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


public class Usuario {
  
    private String email;
    private String clave;
    private String nombre;
    private String descripcion;
    private LinkedList<Usuario> solicitudesAmistad;
    private LinkedList<Usuario> amigos;
    private LinkedList<Mensaje> mensajesRecibidos;
    
    
    
    /**
     * 
     * @param mail
     * @param clave
     * @param nombre
     * @param descripcion 
     */
    public Usuario(String mail, String clave, String nombre, String descripcion){
        
        //Asignamos las variables de la clase
        this.email = mail;
        this.clave = clave;
        this.nombre = nombre;
        this.descripcion = descripcion;
        
        solicitudesAmistad = new LinkedList<Usuario>();
        amigos = new LinkedList<Usuario>();
        mensajesRecibidos = new LinkedList<Mensaje>();
    }
    
    public Usuario (){
        this.email="";
        this.clave = "";
        this.nombre = "";
        this.descripcion = "";
        
        solicitudesAmistad = new LinkedList<Usuario>();
        amigos = new LinkedList<Usuario>();
        mensajesRecibidos = new LinkedList<Mensaje>();
    }
    
    public Usuario (Usuario u){
        this.email = u.email;
        this.clave = u.clave;
        this.nombre = u.nombre;
        this.descripcion = u.descripcion;
       
        solicitudesAmistad = new LinkedList<Usuario>();
        amigos = new LinkedList<Usuario>();
        mensajesRecibidos = new LinkedList<Mensaje>();
    }
    
   public boolean claveValida(String clave){
        return (this.getClave().equals(clave)); //Despues pasar a MD5 por ejemplo
    }
   
   public void mensajeMuro(String msg){
                
       Mensaje mensaje = new Mensaje(msg, this);
       for (int i=0; i<getAmigos().size();i++){
            UJaenSocial.nuevoMensaje(mensaje);//Agregar el mensaje dentro de la clase UJaenSocial
            amigos.get(i).recibirMensaje(mensaje);//Llamada a recibir mensaje
       }
       this.recibirMensaje(mensaje);// El mensaje tambien me lo tengo que enviar a mi mismo
   
   }
   
   
   public void mensajeAmigo(String msg, Usuario amigo){
       
       MensajePrivado mensajePrivado = new MensajePrivado(msg, this, amigo);
       UJaenSocial.nuevoMensaje(mensajePrivado);//Agregar el mensaje dentro de la clase UJaenSocial
       amigo.recibirMensaje(mensajePrivado);     //Llamada a recibir mensaje
   }
   
   public void solicitudAmistad(Usuario u){//Le pido la solicitud de amistad a una pesona
       u.solicitudesAmistad.add(this);// getSolicitudesAmistad().push(u);
   }
   
   public void admitirAmigo(Usuario u){//Acepto la solicitud de amistad de otra persona
       ListIterator<Usuario> iterador = u.getSolicitudesAmistad().listIterator();
       boolean encontrado = false;
      
       
       while(!encontrado && iterador.hasNext()){
           if(iterador.next().equals(u)){
               encontrado = true;
               iterador.remove();
           }
       } 
       
        u.getAmigos().add(this);//o push - u  El me añade como amigo a mi
        amigos.add(u);//Yo lo añado como amigo a él
   }
   
   public void recibirMensaje(Mensaje m){
        getMensajesRecibidos().add(m);// o push
              
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
