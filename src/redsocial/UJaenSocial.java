/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redsocial;

/**
 *
 * @author raul
 */

//import java.util.Date;
import lprj.Logger;
import java.util.*;
import java.util.List;

public class UJaenSocial {

    /**
     * @param args the command line argumentss
     */
    
    private static final Logger LOGGER = Logger.getLogger("RedSocial");
    private List<Usuario> Usuarios;
    private static List<Mensaje> Mensajes;
    
    public UJaenSocial(){
        Usuarios = new ArrayList<Usuario>();
        Mensajes = new ArrayList<Mensaje>();
        
    }
    public void nuevoUsuario(Usuario u){
       Usuarios.add( u);
    
    }
    
    public Collection<Usuario> buscarUsuario(String Termino){
       
       ArrayList<Usuario> devolucion = new ArrayList<Usuario>();
       ListIterator<Usuario> iterador = Usuarios.listIterator();
     
       
       while( iterador.hasNext()){
           if(iterador.next().getDescripcion().contains(Termino) || 
                   iterador.next().getNombre().contains(Termino)){
               devolucion.add(iterador.next());
           }
                    
       } 
        return devolucion;
      
    }
    
    public Usuario buscarUsuarioCorreoE(String CorreoE){
 
       ListIterator<Usuario> iterador = Usuarios.listIterator();
            
       while( iterador.hasNext()){
           if(iterador.next().getEmail().equalsIgnoreCase(CorreoE)){
               return iterador.next();
           }               
       } 
        return null;
      
    }
    
    public Usuario loginUsuario(String email, String clave){
               
       ListIterator<Usuario> iterador = Usuarios.listIterator();
       boolean encontrado = false;
      
       
       while(!encontrado && iterador.hasNext()){
           if(iterador.next().getEmail().equalsIgnoreCase(email)){
               encontrado = true;
               if (iterador.next().claveValida(clave)) return iterador.next();
           }
       } 
       
       
        return null;
    }
    
    public static void nuevoMensaje(Mensaje m){
        Mensajes.add(m);
    }
    
}


