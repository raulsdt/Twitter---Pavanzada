package redsocial;

/**
 *
 * @author Raul Salazar de Torres
 * @date 18/10/2011
 * @signature Programacion Avanzada
 * @file UJaenSocial.java
 * 
 */
import Excepciones.NoExisteUsuario;
import Persistencia.ManejadorJPA;
import Utilidades.Logger;
import java.util.*;
import java.util.List;
import javax.persistence.*;
import Excepciones.UsuarioYaRegistrado;

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
        LOGGER.info("Inicializamos la clase UJAEN");

        Usuarios = new HashMap<String, Usuario>();
        Mensajes = new ArrayList<Mensaje>();
    }

    /**
     * @see Introduce un Usuario nuevo dentro de la lista de usuarios
     * @param u Nuevo usuario a incorporar
     * @throws Campos anteriores requeridos de forma obligatoria
     */
    public void nuevoUsuario(Usuario u) throws Excepciones.UsuarioYaRegistrado{
        LOGGER.info("Introducido al usuario: " + u.getEmail());
        if (buscarUsuarioCorreoE(u.getEmail()) == null) {
            Usuarios.put(u.getEmail(), u);
            ManejadorJPA.instancia().em.getTransaction().begin();
            ManejadorJPA.instancia().em.persist(u);
            ManejadorJPA.instancia().em.getTransaction().commit();
        }else{
            throw new Excepciones.UsuarioYaRegistrado("El usuario introducido "
                    + "ya se encuentra registrado");
        }

    }

    /**
     * @see Permite la busqueda de un usuario mediante un termino (busca en el nombre
     * y en la descripcion del usuario)
     * @param Termino Valor de busqueda
     * @return Devuelve collecion con el conjunto de Usuarios con los que corresponde
     * dicho valor de busqueda -> Termino
     */
    public Collection<Usuario> buscarUsuario(String Termino) {
        LOGGER.info("Buscando usuario por: " + Termino);

        //Iterator it = Usuarios.entrySet().iterator();
        //return ManejadorJPA.instancia().em.createQuery("select u from Usuario u WHERE descripcion = :descrip", Usuario.class).getResultList();
        Query q = ManejadorJPA.instancia().em.createQuery("select u from Usuario u WHERE descripcion LIKE '%" + Termino + "%' OR  nombre LIKE '%"  + Termino + "%'");
        List<Usuario> devolucion = q.getResultList();
        //TypedQuery<Usuario> usuario= ManejadorJPA.instancia().em.createQuery("select u from Usuario u ", Usuario.class);
        /*Map.Entry e;
        Usuario o = new Usuario();
        while (it.hasNext()) {
        e = (Map.Entry) it.next();
        o = (Usuario) e.getValue();
        if (o.getDescripcion().contains(Termino)
        || o.getNombre().contains(Termino)) {
        devolucion.add(o);
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
        Usuario user = ManejadorJPA.instancia().em.find(Usuario.class, CorreoE);
        Usuarios.put(CorreoE, user);
        return user;


    }

    /**
     * @see Loguea a un usuario con una clave e email
     * @param email Correo electronico del usuario a loguear
     * @param clave Clave del usuario a loguear (encriptado en SHA1)
     * @return Devuelve el usuario logueado o null en caso de que algunos de los 
     * parametros anteriores sean incorrectos
     */
    public Usuario loginUsuario(String email, String clave) throws NoExisteUsuario {

        LOGGER.info("Logueando al usuario: " + email);
        if (buscarUsuarioCorreoE(email) != null) {
            if (Usuarios.get(email).claveValida(clave)) {
                return Usuarios.get(email);
            } else {
                return null;
            }
        } else {
            throw new NoExisteUsuario("LoginUsuario: No existe el usuario con el que nos hemos logueado");
        }
    }

    /**
     * @see Introduce un nuevo mensaje
     * @param m Mensaje a introducir en la lista
     */
    public static void nuevoMensaje(Mensaje m) {
        Mensajes.add(m);
        
        ManejadorJPA.instancia().em.getTransaction().begin();
        ManejadorJPA.instancia().em.persist(m);
        ManejadorJPA.instancia().em.getTransaction().commit();
    }
}
