/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import Excepciones.*;
import javax.persistence.PersistenceContext;
 
/**
 *
 * @author raul
 */
public class ManejadorJPA {
    public EntityManagerFactory emf;
    
    @PersistenceContext
    public EntityManager em;
    
    static ManejadorJPA instancia = null;
   
    private ManejadorJPA(){
        emf = Persistence.createEntityManagerFactory("RedSocialPU");
        em = emf.createEntityManager();
    }
    
    public EntityManager getEntityManager(){
        return em;
    }
    
    public static void crearConexion() throws ErrorConexionBD{
        if(instancia == null){
            instancia = new ManejadorJPA();
        }else{
            throw new ErrorConexionBD("La conexion ya esta creada");
        }
    }
    
    public static ManejadorJPA instancia(){
        return instancia;
    }
    
    public static void desconectar() throws ErrorConexionBD{
        if(instancia!=null){
            instancia.em.getTransaction().begin();
            instancia.em.createNativeQuery("shutdown").executeUpdate();
            instancia.em.getTransaction().commit();
            
            instancia.em.close();
            instancia.emf.close();
            instancia = null;
        }else{
            throw new ErrorConexionBD("No hay ninguna conexion establecidad");
        }
    }
}
