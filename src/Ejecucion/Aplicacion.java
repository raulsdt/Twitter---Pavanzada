/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejecucion;

import Controlador.controlador;
import Interfaz.EntornoGrafico;

/**
 *
 * @author Raul Salazar de Torres
 * @date 18/10/2011
 * @signature Programacion Avanzada
 * @file Aplicacion.java
 * 
 */
public class Aplicacion {

    /**
     * @see Función main del sistema
     * @param args No se tiene en cuenta los parametros de entrada
     */
    public static void main(String[] args) {

        //
        
        //controlador.initialise();
        new EntornoGrafico().setVisible(true);
        
    }
}
