/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.util;

import java.util.ArrayList;
import limmen.id2212.proj.util.Participant;

/**
 *
 * @author kim
 */
public class ServerCommand {
    private final ServerCommandName commandName;
    private ArrayList<Participant> participants;
    
    public ServerCommand(ServerCommandName commandName){
        this.commandName = commandName;
    }
    public ServerCommandName getCommandName() {
        return commandName;
    }
    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }
    
}
