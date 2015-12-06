/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.util;

import java.util.ArrayList;

/**
 *
 * @author kim
 */
public class ServerCommand {
    private final ServerCommandName commandName;
    private ArrayList<TableDTO> participants;
    private TableDTO participant;
    
    public ServerCommand(ServerCommandName commandName){
        this.commandName = commandName;
    }
    public ServerCommandName getCommandName() {
        return commandName;
    }
    public ArrayList<TableDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<TableDTO> tableParticipants) {
        this.participants = tableParticipants;
    }

    public TableDTO getParticipant() {
        return participant;
    }

    public void setParticipant(TableDTO participant) {
        this.participant = participant;
    }
    
}
