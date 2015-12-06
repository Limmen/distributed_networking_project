/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.server.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import limmen.id2212.proj.client.util.TableDTO;
import limmen.id2212.proj.util.Participant;
import limmen.id2212.proj.util.ParticipantImpl;

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
    synchronized public void putParticipants(ArrayList<TableDTO> tableParticipants){
        EntityManager em = null;
        System.out.println("size: " + tableParticipants.size());
        try{
            for(TableDTO p : tableParticipants){
                em = beginTransaction();
                ParticipantImpl participant  = new ParticipantImpl(p.getID(),p.getName(), p.getGender()
                        ,p.getCountry(),p.getBirthday(), p.getHeight(),p.getWeight(),p.getSport());
                em.merge(participant);
                commitTransaction(em);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    synchronized public void editParticipant(TableDTO p) throws RemoteException{
        EntityManager em = null;
        em = beginTransaction();
        ParticipantImpl participant  = new ParticipantImpl(p.getID(),p.getName(), p.getGender()
                ,p.getCountry(),p.getBirthday(), p.getHeight(),p.getWeight(),p.getSport());
        em.merge(participant);
        commitTransaction(em);
    }
    synchronized public void deleteParticipant(int id) throws RemoteException{
        EntityManager em = beginTransaction();
        ParticipantImpl p = em.find(ParticipantImpl.class, id);
        em.remove(p);
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
