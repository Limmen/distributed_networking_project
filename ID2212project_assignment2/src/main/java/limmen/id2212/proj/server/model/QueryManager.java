/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.server.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author kim
 */
public class QueryManager {
    private static EntityManagerFactory emFactory;
    public QueryManager(){
        emFactory = Persistence.createEntityManagerFactory("ID2212.projectPU");
    }
    
    
    
    
    private EntityManager beginTransaction()
    {
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        return em;
    }
    
    private void commitTransaction(EntityManager em)
    {
        em.getTransaction().commit();
    }
}
