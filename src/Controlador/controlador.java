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
import java.util.*;
import redsocial.*;
import java.io.*;
import Utilidades.SHA1;
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
                System.err.println("No puedo obtener la consola.");
            }
            return new String(console.readPassword("Introduzca la contraseña"));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * @see Logueamos e mostramos la informacion inicial del usuario
     * @return Devuelve el usuario con el cual hemos sido logueados
     */
    private static Usuario logueoRegistroInit() {
        InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(sr);

        try {
            Usuario usuario = new Usuario();
            String opcion;

            do {
                System.out.println("Esta ya logueado: Introduzca 1, o desea "
                        + "registrarse: Introduzca 2");

                opcion = br.readLine();
            } while (Integer.parseInt(opcion) > 2);

            switch (Integer.parseInt(opcion)) {
                case 1:

                    do {
                        System.out.println("Introduzca su direccion de correo");
                        String correo = br.readLine();

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
                    System.out.println("Introduzca su direccion de correo");
                    String correo = br.readLine();

                    System.out.println("Introduzca su nombre");
                    String nombre = br.readLine();

                    System.out.println("Introduzca una descripcion");
                    String descripcion = br.readLine();

                    //System.out.println("Introduzca su clave");
                    // String clave = br.readLine();
                    String clave = leerClave();

                    //Adaptacion a SHA1
                    usuario = new Usuario(correo, SHA1.encriptarBase64(clave), nombre, descripcion);
                    principal.nuevoUsuario(usuario);
                    break;

            }

            //***************VISUALIZACION DE DATOS PERSONALES*****************//
            System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<< Usuario: "
                    + usuario.getNombre() + ">>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("Correo " + usuario.getEmail());
            System.out.println("Descripcion: " + usuario.getDescripcion());

            LinkedList<Mensaje> tablon = new LinkedList<Mensaje>();
            tablon = usuario.getMensajesRecibidos();

            //***************VISUALIZACION DE TABLON*****************//
            System.out.println("");
            System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<  TABLÓN  >>>>>>>>>>>>>>>>>>>>>>>");
            int j = 0;
            for (int i = 0; i < tablon.size(); i++) {
                if (tablon.get(i) instanceof Mensaje) {
                    j++;
                    System.out.println(" <<<<<<<   Mensaje:" + j + "   >>>>>> "
                            + tablon.get(i).getEmisor().getNombre() + ":  "
                            + tablon.get(i).getContenido());
                }

            }

            //***************VISUALIZACION DE MENSAJES PRIVADOS*****************//
            String valida;
            do {
                System.out.println("¿Desea ver sus mensajes privados? (Si/No)");
                valida = br.readLine();
            } while (!valida.equalsIgnoreCase("Si") && !valida.equalsIgnoreCase("No"));

            if (valida.equalsIgnoreCase("Si")) {
                j = 0;
                for (int i = 0; i < tablon.size(); i++) {
                    if (tablon.get(i) instanceof MensajePrivado) {
                        j++;
                        System.out.println(" <<<<<<<   Mensaje:" + j + "   >>>>>>"
                                + tablon.get(i).getEmisor().getNombre() + ":  "
                                + tablon.get(i).getContenido());
                    }

                }
                System.out.println();
            }

            return usuario;

        } catch (Exception e) {

            System.out.println("Los datos introducidos no son correctos: " + e.getMessage());
            return logueoRegistroInit();
        }
        //return null;
    }

    /**
     * @see Función inicial de todo el sistema, posee toda la funcionalidad.
     */
    public static void initialise() {
        try {
            InputStreamReader sr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(sr);

            Usuario usuario = new Usuario();
            usuario = logueoRegistroInit();

            String eleccion, valida;

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
                } while (Integer.parseInt(eleccion) > 6);

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
                        break;

                }
            } while (!eleccion.equalsIgnoreCase("0"));




        } catch (Exception ex) {
            System.out.println("El valor introducido no es correcto");
            initialise();
        }
    }

    /**
     * @see Permite la escritura de un un nuevo mensaje en el muro nuestro
     * y en el de nuestros amigos
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void MensajeMuro(Usuario usuario, BufferedReader br) {
        try {
            System.out.println("Escriba el mensaje que quiere enviar a "
                    + "su muro");
            String msg = br.readLine();
            usuario.mensajeMuro(msg);

        } catch (IOException e) {
            System.out.println("Error en: " + e.getMessage());;
        }
    }

    /**
     * @see Permite el envio de un mensaje privado a un destinatario concreto
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void MensajePrivado(Usuario usuario, BufferedReader br) {
        try {
            System.out.println("Escriba el mensaje privado que quiere "
                    + "enviar");
            String msg_private = br.readLine();
            String email;
            do {
                System.out.println("Escriba el email de la persona a la que"
                        + " le quiere enviar el mensaje");
                email = br.readLine();
            } while (principal.buscarUsuarioCorreoE(email) == null);

            usuario.mensajeAmigo(msg_private,
                    principal.buscarUsuarioCorreoE(email));//Voy a poner
            //que busca por su correo electronico
        } catch (Exception e) {
            System.out.println("El correo introducido es incorrecto");
            MensajePrivado(usuario, br);
        }
    }

    /**
     * @see Nos muestra la lista de nuestros amigos
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void MostrarListaAmigos(Usuario usuario, BufferedReader br) {

        try {
            LinkedList<Usuario> Amigos = new LinkedList<Usuario>();

            Amigos = usuario.getAmigos();

            System.out.println("<<<<<<<<  LISTA DE AMIGOS  >>>>>>>");
            for (int k = 0; k < Amigos.size(); k++) {
                System.out.println(Amigos.get(k).getNombre() + " - "
                        + Amigos.get(k).getEmail());

            }

            System.out.println("");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @see Muestra el conjunto de solicitudes de amistad, permitiendonos aceptar
     * la que nosotros consideremos oportuna
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void MostratSolicitudesAmistad(Usuario usuario, BufferedReader br) {
        try {
            String valida, email;
            LinkedList<Usuario> Peticiones = new LinkedList<Usuario>();
            Peticiones = usuario.getSolicitudesAmistad();

            System.out.println("<<<<<<<<  LISTA DE SOLICITUDES AMISTAD"
                    + "  >>>>>>>");
            for (int k = 0; k < Peticiones.size(); k++) {
                System.out.println(Peticiones.get(k).getNombre() + " - "
                        + Peticiones.get(k).getEmail() + " - "
                        + Peticiones.get(k).getDescripcion());

            }
            do {
                System.out.println("¿Desea aceptar a algun usuario? (Si/No)");
                valida = br.readLine();
            } while (!valida.equalsIgnoreCase("Si") && !valida.equalsIgnoreCase("No"));

            if (valida.equalsIgnoreCase("Si")) {
                do {
                    System.out.println("Escriba el email de la persona a la "
                            + "que desea aceptar");
                    email = br.readLine();
                } while (principal.buscarUsuarioCorreoE(email) == null);

                usuario.admitirAmigo(principal.buscarUsuarioCorreoE(email));

            }
        } catch (Exception e) {
            System.out.println("El dato introducido no es correcto: " + e.getMessage());
            MostratSolicitudesAmistad(usuario, br);
        }
    }

    /**
     * @see Nos permite buscar un usuario en la red mediante un termino, buscando
     * por su Nombre y Descripcion
     * @param usuario Usuario con el que estamos logueados
     * @param br Buffer de escritura
     */
    private static void BuscarUsuariosRed(Usuario usuario, BufferedReader br) {
        try {
            String valida, email;
            Collection<Usuario> UsuariosRed = new LinkedList<Usuario>();
            //No estoy muy seguro
            System.out.println("Escriba el termino por el que desea "
                    + "buscar usuarios en la red");
            String termino = br.readLine();

            UsuariosRed = principal.buscarUsuario(termino);
            LinkedList<Usuario> u = (LinkedList<Usuario>) UsuariosRed;


            for (int jj = 0; jj < u.size(); jj++) {
                System.out.println(u.get(jj).getNombre() + " - "
                        + u.get(jj).getEmail() + " - "
                        + u.get(jj).getDescripcion());
            }
            do {
                System.out.println("¿Desea admitir a alguien? (Si/No)");
                valida = br.readLine();
            } while (!valida.equalsIgnoreCase("Si") && !valida.equalsIgnoreCase("No"));

            if (valida.equalsIgnoreCase("Si")) {
                do {
                    System.out.println("Escriba el email de la persona a la "
                            + "que desea aceptar");
                    email = br.readLine();

                    if (principal.buscarUsuarioCorreoE(email) != null) {
                        usuario.solicitudAmistad(principal.buscarUsuarioCorreoE(email));
                    } else {
                        System.out.println("El correo introducido es incorrecto");
                    }
                } while (principal.buscarUsuarioCorreoE(email) == null);
            }
        } catch (Exception e) {
            System.out.println("El dato introducido no es correcto " + e.getMessage());
            BuscarUsuariosRed(usuario, br);
        }
    }
}
