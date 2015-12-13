/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

/**
 * Class that represents a command to send to the server.
 * @author kim
 */
public class ServerCommand {
    private final ServerCommandName commandName;
    
    /**
     * Class constructor
     * @param commandName name of the server command.
     */
    public ServerCommand(ServerCommandName commandName){
        this.commandName = commandName;
    }
    
    /**
     * getCommandName
     * @return name of the command
     */
    public ServerCommandName getCommandName() {
        return commandName;
    }
    
}
