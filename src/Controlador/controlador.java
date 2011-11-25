/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author Raul Salazar de Torres
 * @date 18/10/2011
 * @signature Programacion Avanzada
 * @file controlador.java
 * 
 */
import Excepciones.MensajeVacio;
import Excepciones.NoEsAmigo;
import Excepciones.NoExisteUsuario;
import Excepciones.UsuarioYaRegistrado;
import Persistencia.ManejadorJPA;
import java.util.*;
import redsocial.*;
import java.io.*;
import Utilidades.SHA1;
import Utilidades.Validator;
import java.io.Console;

public final class controlador {

    private static redsocial.UJaenSocial principal = new UJaenSocial();

    /**
     * @see Lee clave cifrada desde la consola
     * @return La clave captada
     */
    private static String leerClave() {
        try {
            Console console = System.console();
            if (console == null) {
                System.err.println("No se puede obtener la consola.");
            }
            return new String(console.readPassword("Introduzca la contraseña"));

        } catch (Exception e) {
            System.out.println("Error al leer la clave: " + e.getMessage());
        }
        return null;
    }

    /**
     * @see Logueamos e mostramos la informacion inicial del usuario
     * @return Devuelve el usuario con el cual hemos sido logueados
     */
    private static Usuario logueoRegistroInit() {
        try {
            InputStreamReader sr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(sr);

            Usuario usuario = new Usuario();
            String opcion = CaptarDatos(10, br);

            switch (Integer.parseInt(opcion)) {
                case 1:

                    do {

                        String correo = CaptarDatos(11, br);
                        //System.out.println("Introduzca su clave");
                        //String clave = br.readLine();
                        String clave = leerClave();

                        //Adaptacion a SHA1 
                        usuario = principal.loginUsuario(correo, SHA1.encriptarBase64(clave));
                        if (usuario == null) {
                            System.out.println("La direccion o contraseña"
                                    + " introducida no es validad");
                        }
                    } while (usuario == null);
                    break;


                case 2:
                    String correo = CaptarDatos(11, br);
                    String nombre = CaptarDatos(12, br);
                    String descripcion = CaptarDatos(13, br);

                    //System.out.println("Introduzca su clave");
                    //String clave = br.readLine();
                    String clave = leerClave();

                    //Adaptacion a SHA1
                    usuario = new Usuario(correo, SHA1.encriptarBase64(clave), nombre, descripcion);
                    principal.nuevoUsuario(usuario);
                    break;

            }

            return usuario;
        } catch (NoExisteUsuario e) {
            System.out.println(e);
            return logueoRegistroInit();
        }catch(UsuarioYaRegistrado e){
            System.out.println(e);
            return logueoRegistroInit();
        }
//        } catch (Exception e) {
//            return null;
//        }

    }

    private static void mostrarTablon(Usuario usuario, BufferedReader br) {

        //***************VISUALIZACION DE DATOS PERSONALES*****************//
        System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<< Usuario: "
                + usuario.getNombre() + ">>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("");
        System.out.println("Correo " + usuario.getEmail());
        System.out.println("Descripcion: " + usuario.getDescripcion());
        System.out.println("");

        List<Mensaje> tablon = new LinkedList<Mensaje>();
        tablon = usuario.getMensajesRecibidos();

        //***************VISUALIZACION DE TABLON*****************//
        System.out.println("");
        System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<  TABLÓN  >>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("");
        int j = 0;
        for (int i = 0; i < tablon.size(); i++) {
            if (!(tablon.get(i) instanceof MensajePrivado)) {
                j++;
                System.out.println(" <<<<<<<     Mensaje: " + j + "   >>>>>> "
                        + tablon.get(i).getEmisor().getNombre() + ":  "
                        + tablon.get(i).getContenido());
            }

        }

        //***************VISUALIZACION DE MENSAJES PRIVADOS*****************//
        String valida = CaptarDatos(9, br);


        if (valida.equalsIgnoreCase("Si")) {
            j = 0;
            for (int i = 0; i < tablon.size(); i++) {
                if (tablon.get(i) instanceof MensajePrivado) {
                    j++;
                    System.out.println(" <<<<<<<     Mensaje:" + j + "   >>>>>> "
                            + tablon.get(i).getEmisor().getNombre() + " -  "
                            + tablon.get(i).getFecha() + " : "
                            + tablon.get(i).getContenido());
                }

            }
            System.out.println();
        }
    }

    private static void menu(Usuario usuario, BufferedReader br) {


        try {
            String eleccion;
            do {
                do {
                    /************MUESTRA DIVERSAS OPCIONES A REALIZAR****************/
                    System.out.println("Indique que opcion desea realizar:");
                    System.out.println("1. Enviar un mensaje a mi muro");
                    System.out.println("2. Enviar un mensaje privado a un amigo");
                    System.out.println("3. Ver la lista de amigos");
                    System.out.println("4. Ver solicitudes de amistad");
                    System.out.println("5. Buscar a un usuario en la red");
                    System.out.println("6. Desea introducir o registrar un nuevo usuario");
                    System.out.println("0. Salir");
                    System.out.println("*********************************************");

                    eleccion = br.readLine();
                } while (Integer.parseInt(eleccion) > 6 || Integer.parseInt(eleccion) < 0);

                switch (Integer.parseInt(eleccion)) {
                    case 1://Mensaje al muro
                        MensajeMuro(usuario, br);
                        break;

                    case 2://Mensaje privado a algun amigo
                        MensajePrivado(usuario, br);
                        break;

                    case 3://Imprimir lista de amigos
                        MostrarListaAmigos(usuario, br);
                        break;

                    case 4://Obtener lista de solicitudes de amitad y aceptar 
                        //peticion si se considera oportuno
                        MostratSolicitudesAmistad(usuario, br);
                        break;

                    case 5://Buscar usuarios en la red por un termino, y aceptar si
                        //se considera oportuno
                        BuscarUsuariosRed(usuario, br);
                        break;

                    case 6:
                        usuario = logueoRegistroInit();
                        mostrarTablon(usuario, br);
                        break;

                }
            } while (!eleccion.equalsIgnoreCase("0"));
        } catch (Exception ex) {
            System.out.println("El valor introducido no es correcto");
            menu(usuario, br);
        }
    }

    /**
     * @see Función inicial de todo el sistema, posee toda la funcionalidad.
     */
    public static void initialise() {

        InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(sr);
        ManejadorJPA.crearConexion();
        Usuario usuario = new Usuario(logueoRegistroInit());
        mostrarTablon(usuario, br);
        menu(usuario, br);
        ManejadorJPA.desconectar();
    }

    /**
     * @see Permite la escritura de un un nuevo mensaje en el muro nuestro
     * y en el de nuestros amigos
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void MensajeMuro(Usuario usuario, BufferedReader br) {
        try {
            usuario.mensajeMuro(CaptarDatos(8, br));
        } catch (MensajeVacio e) {
            System.out.println(e);
        }
    }

    /**
     * @see Permite el envio de un mensaje privado a un destinatario concreto
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void MensajePrivado(Usuario usuario, BufferedReader br) {
        try {
            String msg_private, email;
            msg_private = CaptarDatos(6, br);
            email = CaptarDatos(7, br);

            usuario.mensajeAmigo(msg_private, principal.buscarUsuarioCorreoE(email));
        } catch (NoEsAmigo e) {
            System.out.println(e);
            MensajePrivado(usuario, br);
        }
    }

    /**
     * @see Nos muestra la lista de nuestros amigos
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void MostrarListaAmigos(Usuario usuario, BufferedReader br) {

        List<Usuario> Amigos = new LinkedList<Usuario>();
        Amigos = usuario.getAmigos();

        System.out.println("<<<<<<<<  LISTA DE AMIGOS  >>>>>>>");
        for (int k = 0; k < Amigos.size(); k++) {
            System.out.println(Amigos.get(k).getNombre() + " - "
                    + Amigos.get(k).getEmail());

        }
        System.out.println("");

    }

    /**
     * Permite aceptar a un nuevo usuario ya existente en la lista de solicitudes de amistad
     * @param usuario Usuario actual precargado
     * @param br Buffer que ha sido creado
     */
    private static void AceptarUsuario(Usuario usuario, BufferedReader br) {
        try {
            String valida, email;
            valida = CaptarDatos(4, br);

            if (valida.equalsIgnoreCase("Si")) {
                email = CaptarDatos(5, br);
                usuario.admitirAmigo(principal.buscarUsuarioCorreoE(email));
            }
        } catch (NoExisteUsuario e) {
            System.out.println(e);
            AceptarUsuario(usuario, br);
        }

    }

    /**
     * @see Muestra el conjunto de solicitudes de amistad, permitiendonos aceptar
     * la que nosotros consideremos oportuna
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void MostratSolicitudesAmistad(Usuario usuario, BufferedReader br) {


        List<Usuario> Peticiones = new LinkedList<Usuario>();
        Peticiones = usuario.getSolicitudesAmistad();

        System.out.println("<<<<<<<<  LISTA DE SOLICITUDES AMISTAD  >>>>>>>");
        for (int k = 0; k < Peticiones.size(); k++) {
            System.out.println(Peticiones.get(k).getNombre() + " - "
                    + Peticiones.get(k).getEmail() + " - "
                    + Peticiones.get(k).getDescripcion());

        }
        if (Peticiones.size() > 0) {
            AceptarUsuario(usuario, br);
        }

    }

    private static void AgregarUsuario(Usuario usuario, BufferedReader br) {

        String valida, email;
        valida = CaptarDatos(2, br);

        if (valida.equalsIgnoreCase("Si")) {
            email = CaptarDatos(3, br);
            Usuario user = new Usuario(principal.buscarUsuarioCorreoE(email));
            usuario.solicitudAmistad(user);
        }

    }

    /**
     * @see Nos permite buscar un usuario en la red mediante un termino, buscando
     * por su Nombre y Descripcion
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void BuscarUsuariosRed(Usuario usuario, BufferedReader br) {
        Collection<Usuario> UsuariosRed = new LinkedList<Usuario>();
        //No estoy muy seguro
        String termino = CaptarDatos(1, br);
        UsuariosRed = principal.buscarUsuario(termino);
        List<Usuario> u = (List<Usuario>) UsuariosRed;
        int cantidad = 0;
        for (int jj = 0; jj < u.size(); jj++) {

            if (!u.get(jj).getEmail().equals(usuario.getEmail())) {
                System.out.println(u.get(jj).getNombre() + " - "
                        + u.get(jj).getEmail() + " - "
                        + u.get(jj).getDescripcion());
                cantidad++;
            }
        }
        if (cantidad > 0) {
            AgregarUsuario(usuario, br);
        }

    }

    private static String CaptarDatos(int opcion, BufferedReader br) {
        String termino;
        try {
            switch (opcion) {

                case 1:
                    System.out.println("Escriba el termino por el que desea "
                            + "buscar usuarios en la red");
                    termino = br.readLine();
                    return termino;

                case 2:
                    do {
                        System.out.println("¿Desea agregar a algun nuevo usuario? (Si/No)");
                        termino = br.readLine();
                    } while (!termino.equalsIgnoreCase("Si") && !termino.equalsIgnoreCase("No"));
                    return termino;

                case 3:
                    do {
                        System.out.println("Escriba el email de la persona a la "
                                + "que desea agregar");
                        termino = br.readLine();
                        if (!Validator.isEmail(termino) || principal.buscarUsuarioCorreoE(termino) == null) {
                            System.out.println("El correo introducido no es valido");
                        }
                    } while (!Validator.isEmail(termino) || principal.buscarUsuarioCorreoE(termino) == null);
                    return termino;

                case 4:
                    do {
                        System.out.println("¿Desea aceptar a algun usuario? (Si/No)");
                        termino = br.readLine();
                    } while (!termino.equalsIgnoreCase("Si") && !termino.equalsIgnoreCase("No"));
                    return termino;

                case 5:
                    do {
                        System.out.println("Escriba el email de la persona a la "
                                + "que desea aceptar");
                        termino = br.readLine();
                        if (!Validator.isEmail(termino) || principal.buscarUsuarioCorreoE(termino) == null) {
                            System.out.println("El correo introducido no es valido");
                        }
                    } while (!Validator.isEmail(termino) || principal.buscarUsuarioCorreoE(termino) == null);
                    return termino;
                case 6:
                    do {
                        System.out.println("Escriba el mensaje privado que quiere "
                                + "enviar");
                        termino = br.readLine();
                    } while (termino.equals(""));
                    return termino;
                case 7:
                    do {
                        System.out.println("Escriba el email de la persona a la que"
                                + " le quiere enviar el mensaje");
                        termino = br.readLine();
                    } while (principal.buscarUsuarioCorreoE(termino) == null);
                    return termino;
                case 8:
                    do {
                        System.out.println("Escriba el mensaje que quiere enviar a "
                                + "su muro");
                        termino = br.readLine();
                    } while (termino.equals(""));
                    return termino;
                case 9:
                    do {
                        System.out.println("¿Desea ver sus mensajes privados? (Si/No)");
                        termino = br.readLine();
                    } while (!termino.equalsIgnoreCase("Si") && !termino.equalsIgnoreCase("No"));
                    return termino;
                case 10:
                    do {
                        System.out.println("Esta ya logueado: Introduzca 1, o desea "
                                + "registrarse: Introduzca 2");

                        termino = br.readLine();
                    } while (!(Integer.parseInt(termino) == 1 || Integer.parseInt(termino) == 2));
                    return termino;
                case 11:
                    do {
                        System.out.println("Introduzca su direccion de correo");
                        termino = br.readLine();
                    } while (termino.equals("") || !Validator.isEmail(termino));
                    return termino;
                case 12:
                    do {
                        System.out.println("Introduzca su nombre");
                        termino = br.readLine();
                    } while (termino.equals(""));
                    return termino;
                case 13:
                    System.out.println("Introduzca una descripcion");
                    termino = br.readLine();
                    return termino;
            }

        } catch (Exception e) {
            System.out.println("No corresponde a ninguno de los datos solicitados por el teclado");
            CaptarDatos(opcion, br);
        }
        return null;
    }
}
