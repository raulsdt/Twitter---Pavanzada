package redsocial;

/**
 *
 * @author Raul Salazar de Torres
 * @date 18/10/2011
 * @signature Programacion Avanzada
 * @file UJaenSocial.java
 * 
 */
import Utilidades.Logger;
import java.util.*;
import java.util.List;

public class UJaenSocial {

    /**
     * @param args the command line argumentss
     */
    private static final Logger LOGGER = Logger.getLogger("RedSocial"); //Logger 
    //para almacenar las trazas durante todo el documento
    private List<Usuario> Usuarios;
    private static List<Mensaje> Mensajes;

    /**
     * @see Contructor de la clase
     */
    public UJaenSocial() {
        Usuarios = new ArrayList<Usuario>();
        Mensajes = new ArrayList<Mensaje>();

    }

    /**
     * @see Introduce un Usuario nuevo dentro de la lista de usuarios
     * @param u Nuevo usuario a incorporar
     * @throws Campos anteriores requeridos de forma obligatoria
     */
    public void nuevoUsuario(Usuario u) {
        Usuarios.add(u);

    }

    /**
     * @see Permite la busqueda de un usuario mediante un termino (busca en el nombre
     * y en la descripcion del usuario)
     * @param Termino Valor de busqueda
     * @return Devuelve collecion con el conjunto de Usuarios con los que corresponde
     * dicho valor de busqueda -> Termino
     */
    public Collection<Usuario> buscarUsuario(String Termino) {

        LinkedList<Usuario> devolucion = new LinkedList<Usuario>();
        //ListIterator<Usuario> iterador = Usuarios.listIterator();

        for (int i = 0; i < Usuarios.size(); i++) {
            if (Usuarios.get(i).getDescripcion().contains(Termino)
                    || Usuarios.get(i).getNombre().contains(Termino)) {
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

    /**
     * @see Busqueda mediante correo electronico
     * @param CorreoE Correo electronico de la persona en cuestion
     * @return Devuelve el usuario buscado o null en caso de no existir
     */
    public Usuario buscarUsuarioCorreoE(String CorreoE) {

        for (int it = 0; it < Usuarios.size(); it++) {
            if (Usuarios.get(it).getEmail().equalsIgnoreCase(CorreoE)) {
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

    /**
     * @see Loguea a un usuario con una clave e email
     * @param email Correo electronico del usuario a loguear
     * @param clave Clave del usuario a loguear (encriptado en SHA1)
     * @return Devuelve el usuario logueado o null en caso de que algunos de los 
     * parametros anteriores sean incorrectos
     */
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
            } else {
                i++;
            }
        }

        return null;
    }

    /**
     * @see Introduce un nuevo mensaje
     * @param m Mensaje a introducir en la lista
     */
    public static void nuevoMensaje(Mensaje m) {
        Mensajes.add(m);
    }
}
