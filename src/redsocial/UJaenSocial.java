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
    private HashMap<String, Usuario> Usuarios;
    private static List<Mensaje> Mensajes;

    /**
     * @see Contructor de la clase
     */
    public UJaenSocial() {
        Usuarios = new HashMap<String, Usuario>();
        Mensajes = new ArrayList<Mensaje>();

    }

    /**
     * @see Introduce un Usuario nuevo dentro de la lista de usuarios
     * @param u Nuevo usuario a incorporar
     * @throws Campos anteriores requeridos de forma obligatoria
     */
    public void nuevoUsuario(Usuario u) {
        Usuarios.put(u.getEmail(), u);

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
        Iterator it = Usuarios.entrySet().iterator();

        Map.Entry e;
        Usuario o = new Usuario();
        while (it.hasNext()) {
            e = (Map.Entry) it.next();
            o = (Usuario) e.getValue();
            if (o.getDescripcion().contains(Termino)
                    || o.getNombre().contains(Termino)) {
                devolucion.add(o);
            }

        }
        return devolucion;

    }

    /**
     * @see Busqueda mediante correo electronico
     * @param CorreoE Correo electronico de la persona en cuestion
     * @return Devuelve el usuario buscado o null en caso de no existir
     */
    public Usuario buscarUsuarioCorreoE(String CorreoE) {

        return Usuarios.get(CorreoE);


    }

    /**
     * @see Loguea a un usuario con una clave e email
     * @param email Correo electronico del usuario a loguear
     * @param clave Clave del usuario a loguear (encriptado en SHA1)
     * @return Devuelve el usuario logueado o null en caso de que algunos de los 
     * parametros anteriores sean incorrectos
     */
    public Usuario loginUsuario(String email, String clave) {

      
        if(Usuarios.get(email).claveValida(clave)) return Usuarios.get(email);
        else return null;
    }

    /**
     * @see Introduce un nuevo mensaje
     * @param m Mensaje a introducir en la lista
     */
    public static void nuevoMensaje(Mensaje m) {
        Mensajes.add(m);
    }
}
