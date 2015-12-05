/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.server.model;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import limmen.id2212.proj.util.Participant;

/**
 *
 * @author kim
 */
public class QueryManager {
    private static EntityManagerFactory emFactory;
    public QueryManager(){
        emFactory = Persistence.createEntityManagerFactory("ID2212.projectPU");
    }
    synchronized public ArrayList<Participant> getParticipants(){
        EntityManager em = null;
        em = beginTransaction();
        ArrayList<Participant> participants = new ArrayList(em.createQuery("SELECT p FROM ParticipantImpl p").getResultList());
        commitTransaction(em);
        return participants;
    }
    synchronized public void putParticipants(ArrayList<Participant> participants){
        EntityManager em = null;
        em = beginTransaction();
        em.createNativeQuery("DELETE FROM ParticipantImpl;").executeUpdate();
        for(Participant p : participants){
            em.persist(p);
        }
        commitTransaction(em);
    }
    synchronized public void initialStore(ArrayList<Participant> participants){
        EntityManager em = null;
        em = beginTransaction();
        for(Participant p : participants){
            em.persist(p);
        }
        commitTransaction(em);
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
