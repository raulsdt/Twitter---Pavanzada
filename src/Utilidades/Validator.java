/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Raul Salazar de Torres
 */
public final class Validator {
    public Validator(){
                
    }
    
    public static boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            return true;
        }else{
            return false;
        }
    }
}
