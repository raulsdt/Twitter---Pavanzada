/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.*;
import redsocial.*;

/**
 *
 * @author raul
 */
public class Aplicacion {

    public static void main(String[] args) {
        InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(sr);



        try {
            Usuario usuario = new Usuario();
            UJaenSocial principal = new UJaenSocial();


            System.out.println("Esta ya logueado: Introduzca 1, o desea "
                    + "registrarse: Introduzca 2");
            //String opcion = new String();

            String opcion = br.readLine();


            switch (Integer.parseInt(opcion)) {
                case 1:

                    do {
                        System.out.println("Introduzca su direccion de correo");
                        //String correo = new String();
                        String correo = br.readLine();
                        System.out.println("Introduzca su clave");
                        //String clave = new String();
                        String clave = br.readLine();

                        usuario = principal.loginUsuario(correo, clave);
                        if (usuario == null) {
                            System.out.println("La direccion o contraseña"
                                    + " introducida no es validad");
                        }
                    } while (usuario == null);
                    break;


                case 2:
                    System.out.println("Introduzca su direccion de correo");
                    //String correo = new String();
                    String correo = br.readLine();
                    System.out.println("Introduzca su nombre");
                    //String nombre = new String();
                    String nombre = br.readLine();
                    System.out.println("Introduzca una descripcion");
                    //String descripcion = new String();
                    String descripcion = br.readLine();
                    System.out.println("Introduzca su clave");
                    //String clave = new String();
                    String clave = br.readLine();

                    usuario = new Usuario(correo, clave, nombre, descripcion);
                    principal.nuevoUsuario(usuario);
                    System.out.println("Introduzca su clave");
                //break;

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
                if (tablon.get(i).getPublicMessage()) {
                    j++;
                    System.out.println(" <<<<<<<   Mensaje:" + j + "   >>>>>> "
                            + tablon.get(i).getEmisor().getNombre() + ":  "
                            + tablon.get(i).getContenido());
                }

            }

            //***************VISUALIZACION DE MENSAJES PRIVADOS*****************//

            System.out.println("¿Desea ver sus mensajes privados? (Si/No)");
            //String valida = new String();
            String valida = br.readLine();

            if (valida.equalsIgnoreCase("Si")) {
                j = 0;
                for (int i = 0; i < tablon.size(); i++) {
                    if (!tablon.get(i).getPublicMessage()) {
                        j++;
                        System.out.println(" <<<<<<<   Mensaje:" + j + "   >>>>>>"
                                + tablon.get(i).getEmisor().getNombre() + ":  "
                                + tablon.get(i).getContenido());
                    }

                }
            }

            String eleccion;// = new String();
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

                switch (Integer.parseInt(eleccion)) {
                    case 1://Mensaje al muro
                        System.out.println("Escriba el mensaje que quiere enviar a "
                                + "su muro");
                        String msg = br.readLine();
                        usuario.mensajeMuro(msg);
                        break;

                    case 2://Mensaje privado a algun amigo
                        System.out.println("Escriba el mensaje privado que quiere "
                                + "enviar");
                        String msg_private = br.readLine();

                        System.out.println("Escriba el email de la persona a la que"
                                + " le quiere enviar el mensaje");
                        String email = br.readLine();
                        usuario.mensajeAmigo(msg_private,
                                principal.buscarUsuarioCorreoE(email));//Voy a poner
                        //que busca por su correo electronico
                        break;

                    case 3://Imprimir lista de amigos
                        LinkedList<Usuario> Amigos = new LinkedList<Usuario>();
                        Amigos = usuario.getAmigos();

                        System.out.println("<<<<<<<<  LISTA DE AMIGOS  >>>>>>>");
                        for (int k = 0; k < Amigos.size(); k++) {
                            System.out.println(Amigos.get(k).getNombre() + " - "
                                    + Amigos.get(k).getEmail());

                        }
                        break;

                    case 4://Obtener lista de solicitudes de amitad y aceptar 
                        //peticion si se considera oportuno
                        LinkedList<Usuario> Peticiones = new LinkedList<Usuario>();
                        Peticiones = usuario.getSolicitudesAmistad();

                        System.out.println("<<<<<<<<  LISTA DE AMIGOS  >>>>>>>");
                        for (int k = 0; k < Peticiones.size(); k++) {
                            System.out.println(Peticiones.get(k).getNombre() + " - "
                                    + Peticiones.get(k).getEmail() + " - "
                                    + Peticiones.get(k).getDescripcion());

                        }

                        System.out.println("¿Desea aceptar a algun usuario? (Si/No)");
                        valida = br.readLine();
                        if (valida.equalsIgnoreCase("Si")) {
                            System.out.println("Escriba el email de la persona a la "
                                    + "que desea aceptar");
                            email = br.readLine();
                            if (principal.buscarUsuarioCorreoE(email) != null) {
                                usuario.admitirAmigo(principal.buscarUsuarioCorreoE(email));
                            } else {
                                System.out.println("La direccion introducida es incorrecta");
                            }
                        }
                        break;

                    case 5://Buscar usuarios en la red por un termino, y aceptar si
                        //se considera oportuno
                        Collection<Usuario> UsuariosRed = new LinkedList<Usuario>();
                        //No estoy muy seguro
                        System.out.println("Escriba el termino por el que desea "
                                + "buscar usuarios en la red");
                        String termino = br.readLine();
                        UsuariosRed = principal.buscarUsuario(termino);

                            
                        for(int jj=0;jj<UsuariosRed.size();jj++) {//while(UsuariosRed.iterator().hasNext())
                            System.out.println(UsuariosRed.iterator().next().getNombre() + " - "
                                    + UsuariosRed.iterator().next().getEmail() + " - "
                                    + UsuariosRed.iterator().next().getDescripcion());
                            UsuariosRed.iterator().next();
                        }

                        System.out.println("¿Desea admitir a alguien? (Si/No)");
                        valida = br.readLine();
                        if (valida.equalsIgnoreCase("Si")) {
                            System.out.println("Escriba el email de la persona a la "
                                    + "que desea aceptar");
                            email = br.readLine();
                            if (principal.buscarUsuarioCorreoE(email) != null) {
                                usuario.solicitudAmistad(principal.buscarUsuarioCorreoE(email));
                            } else {
                                System.out.println("El correo introducido es incorrecto");
                            }
                        }
                        break;

                    case 6:
                        System.out.println("Esta ya logueado: Introduzca 1, o desea "
                                + "registrarse: Introduzca 2");
                        //String opcion = new String();

                        opcion = br.readLine();


                        switch (Integer.parseInt(opcion)) {
                            case 1:

                                do {
                                    System.out.println("Introduzca su direccion de correo");
                                    //String correo = new String();
                                    String correo = br.readLine();
                                    System.out.println("Introduzca su clave");
                                    //String clave = new String();
                                    String clave = br.readLine();

                                    usuario = principal.loginUsuario(correo, clave);
                                    //System.out.println("adios");
                                    if (usuario == null) {
                                        System.out.println("La direccion o contraseña"
                                                + " introducida no es validad");
                                    }
                                } while (usuario == null);
                                break;


                            case 2:
                                System.out.println("Introduzca su direccion de correo");
                                //String correo = new String();
                                String correo = br.readLine();
                                System.out.println("Introduzca su nombre");
                                //String nombre = new String();
                                String nombre = br.readLine();
                                System.out.println("Introduzca una descripcion");
                                //String descripcion = new String();
                                String descripcion = br.readLine();
                                System.out.println("Introduzca su clave");
                                //String clave = new String();
                                String clave = br.readLine();

                                usuario = new Usuario(correo, clave, nombre, descripcion);
                                principal.nuevoUsuario(usuario);
                                System.out.println("Introduzca su clave");
                            //break;

                        }
                        //***************VISUALIZACION DE DATOS PERSONALES*****************//
                        System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<< Usuario: "
                                + usuario.getNombre() + ">>>>>>>>>>>>>>>>>>>>>>>");
                        System.out.println("Correo " + usuario.getEmail());
                        System.out.println("Descripcion: " + usuario.getDescripcion());

                        
                        tablon = usuario.getMensajesRecibidos();

                        //***************VISUALIZACION DE TABLON*****************//
                        System.out.println("");
                        System.out.println(" <<<<<<<<<<<<<<<<<<<<<<<<  TABLÓN  >>>>>>>>>>>>>>>>>>>>>>>");
                        j = 0;
                        for (int i = 0; i < tablon.size(); i++) {
                            if (tablon.get(i).getPublicMessage()) {
                                j++;
                                System.out.println(" <<<<<<<   Mensaje:" + j + "   >>>>>> "
                                        + tablon.get(i).getEmisor().getNombre() + ":  "
                                        + tablon.get(i).getContenido());
                            }

                        }

                        //***************VISUALIZACION DE MENSAJES PRIVADOS*****************//

                        System.out.println("¿Desea ver sus mensajes privados? (Si/No)");
                        //String valida = new String();
                        valida = br.readLine();

                        if (valida.equalsIgnoreCase("Si")) {
                            j = 0;
                            for (int i = 0; i < tablon.size(); i++) {
                                if (!tablon.get(i).getPublicMessage()) {
                                    j++;
                                    System.out.println(" <<<<<<<   Mensaje:" + j + "   >>>>>>"
                                            + tablon.get(i).getEmisor().getNombre() + ":  "
                                            + tablon.get(i).getContenido());
                                }

                            }
                        }
                        break;

                }
            } while (!eleccion.equalsIgnoreCase("0"));




        } catch (Exception e) {
        }
    }
}
