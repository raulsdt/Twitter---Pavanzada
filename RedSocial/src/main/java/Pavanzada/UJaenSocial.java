/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redsocial;
import java.io.InputStreamReader;
import java.io.BufferedReader;
/**
 *
 * @author raul
 */
public class UJaenSocial {

    /**
     * @param args the command line argumentss
     */
    public static void main(String[] args) {
        InputStreamReader sr= new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(sr);
        try{
        String texto = br.readLine();

        Mensaje sa = new Mensaje(texto);
        System.out.println("La fecha es " + sa.getFecha());
        System.out.println("El mensaje es " + sa.getContenido());

        }
        catch (Exception e)
        {

        }
        }
    }


