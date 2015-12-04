/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.util;

/**
 *
 * @author kim
 */
public class ServerCommand {
    ServerCommandName commandName;
    public ServerCommand(ServerCommandName commandName){
        this.commandName = commandName;
    }

    public ServerCommandName getCommandName() {
        return commandName;
    }
    
}
