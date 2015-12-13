/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server.integration;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import limmen.id2212.nog.client.model.ParticipantDTO;

/**
 * Class to handle queries and interactions with the database.
 * Methods are declared synchronized to get some thread-safety (only one
 * thread is allowed to execute the methods at a time, the rest of the threads
 * get queued up until it's their turn.
 * @author kim
 */
public class QueryManager {
    private static EntityManagerFactory emFactory;

    /**
     * Class constructor. Created a EntityManagerFactory from a specified context.
     * 
     */
    public QueryManager(){
        emFactory = Persistence.createEntityManagerFactory("ID2212.projectPU");        
    }

    /**
     * Method to fetch participants from the database.
     * @return list of participants
     */
    public synchronized ArrayList<Participant> getParticipants(){
        EntityManager em = null;
        em = beginTransaction();
        ArrayList<Participant> participants = new ArrayList(em.createQuery("SELECT p FROM ParticipantImpl p").getResultList());
        commitTransaction(em);
        return participants;
    }

    /**
     * Method to store a list of participants at the database
     * @param tableParticipants
     */
    public synchronized void putParticipants(ArrayList<ParticipantDTO> tableParticipants){
        EntityManager em = null;
        try{
            for(ParticipantDTO p : tableParticipants){
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

    /**
     * Method to edit (update) a specified participant.
     * @param p Participant to be updated.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public synchronized void editParticipant(ParticipantDTO p) throws RemoteException{
        EntityManager em = null;
        em = beginTransaction();
        ParticipantImpl participant  = new ParticipantImpl(p.getID(),p.getName(), p.getGender()
                ,p.getCountry(),p.getBirthday(), p.getHeight(),p.getWeight(),p.getSport());
        em.merge(participant);
        commitTransaction(em);
    }

    /**
     * Method to delete a participant from the database
     * @param id primary key of the participant.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public synchronized void deleteParticipant(int id) throws RemoteException{
        EntityManager em = beginTransaction();
        ParticipantImpl p = em.find(ParticipantImpl.class, id);
        em.remove(p);
        commitTransaction(em);
    }

    /**
     * Method to store a list of participants in a empty database.
     * @param participants list of participants
     */
    public synchronized void initialStore(ArrayList<Participant> participants){
        EntityManager em = null;
        em = beginTransaction();
        for(Participant p : participants){
            em.persist(p);
        }
        commitTransaction(em);
    }
    //Method that returns a connection to the database and start a transaction
    private EntityManager beginTransaction()
    {
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        return em;
    }
    //Method that commits a transaction.
    private void commitTransaction(EntityManager em)
    {
        em.getTransaction().commit();
    }
}
