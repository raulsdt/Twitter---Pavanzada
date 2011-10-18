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

    public UJaenSocial() {
        Usuarios = new ArrayList<Usuario>();
        Mensajes = new ArrayList<Mensaje>();

    }

    public void nuevoUsuario(Usuario u) {
        Usuarios.add(u);

    }

    public Collection<Usuario> buscarUsuario(String Termino) {

        LinkedList<Usuario> devolucion = new LinkedList<Usuario>();
        //ListIterator<Usuario> iterador = Usuarios.listIterator();

        for (int i = 0; i < Usuarios.size(); i++) {
            if (Usuarios.get(i).getDescripcion().contains(Termino) || 
                    Usuarios.get(i).getNombre().contains(Termino)) {
                devolucion.add(Usuarios.get(i));
            }
        }
        /* while (iterador.hasNext()) {
        if (iterador.next().getDescripcion().contains(Termino)
        || iterador.next().getNombre().contains(Termino)) {
        devolucion.add(iterador.next());
        }
        
        }*/
        return devolucion;

    }

    public Usuario buscarUsuarioCorreoE(String CorreoE) {

        for (int it=0;it< Usuarios.size();it++){
            if(Usuarios.get(it).getEmail().equalsIgnoreCase(CorreoE)){
                return Usuarios.get(it);
            }
        }
        /*ListIterator<Usuario> iterador = Usuarios.listIterator();

        while (iterador.hasNext()) {
            if (iterador.next().getEmail().equalsIgnoreCase(CorreoE)) {
                return iterador.next();
            }
        }*/
        return null;

    }

    public Usuario loginUsuario(String email, String clave) {

        //ListIterator<Usuario> iterador = Usuarios.listIterator();
        boolean encontrado = false;
        int i = 0;

        while (!encontrado && i < Usuarios.size()) {
            if (Usuarios.get(i).getEmail().equalsIgnoreCase(email)) {
                if (Usuarios.get(i).claveValida(clave)) {
                    return Usuarios.get(i);
                }
                encontrado = true;
            }else{
                i++;
            }
        }

        /* while(!encontrado && iterador.hasNext()){
        if(iterador.next().getEmail().equalsIgnoreCase(email)){
        if (iterador.next().claveValida(clave)) return iterador.next();
        encontrado=true;
        }
        } 
         */

        return null;
    }

    public static void nuevoMensaje(Mensaje m) {
        Mensajes.add(m);
    }
}
