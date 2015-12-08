/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.proj.client.util;

/**
 * Enum that represents name of server-commands
 * @author kim
 */
public enum ServerCommandName {

    /**
     * Command to fetch participants-data from server
     */
    getParticipants,

    /**
     * Command to save a list of participants-data to server
     */
    putParticipants,

    /**
     * Command to add a participant to the server
     */
    addParticipant,

    /**
     * Command to delete a participant from the server
     */
    deleteParticipant,

    /**
     * Edit participant-data at the server
     */
    editParticipant;
}
