/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.nog.client.model;

import java.util.ArrayList;

/**
 * Class that represents a command to send to the server and contains
 * neccessary data to invoke the corresponding method on the server.
 * @author kim
 */
public class ServerCommand {
    private final ServerCommandName commandName;
    private ArrayList<ParticipantDTO> participants;
    private ParticipantDTO participant;
    
    /**
     * Class constructor
     * @param commandName name of the command
     */
    public ServerCommand(ServerCommandName commandName){
        this.commandName = commandName;
    }

    /**
     * getCommandName
     * @return commandName
     */
    public ServerCommandName getCommandName() {
        return commandName;
    }

    /**
     * getParticipants
     * @return list of participant data 
     */
    public ArrayList<ParticipantDTO> getParticipants() {
        return participants;
    }

    /**
     * setParticipants
     * @param participants list of participant-data
     */
    public void setParticipants(ArrayList<ParticipantDTO> participants) {
        this.participants = participants;
    }

    /**
     * getParticipant
     * @return participant
     */
    public ParticipantDTO getParticipant() {
        return participant;
    }

    /**
     * setParticipant
     * @param participant participant data
     */
    public void setParticipant(ParticipantDTO participant) {
        this.participant = participant;
    }
    
}
