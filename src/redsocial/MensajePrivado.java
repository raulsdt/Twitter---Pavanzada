/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial;

/**
 *
 * @author raul
 */
public class MensajePrivado extends Mensaje{
    
 private Usuario receptor;   
 
  public MensajePrivado(String Contenido, Usuario emisor,Usuario ereceptor){
      super(Contenido,emisor); //Llamada al contructor de la superclase
      setReceptor(ereceptor);
      setPublicMessage(false);
  }

    /**
     * @return the receptor
     */
    public Usuario getReceptor() {
        return receptor;
    }

    /**
     * @param receptor the receptor to set
     */
    private void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }
  
}
