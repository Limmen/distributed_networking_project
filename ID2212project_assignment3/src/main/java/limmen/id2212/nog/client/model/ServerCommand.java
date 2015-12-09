/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.nog.client.model;

/**
 * 
 * @author kim
 */
public class ServerCommand {
    private final ServerCommandName commandName;

    public ServerCommand(ServerCommandName commandName){
        this.commandName = commandName;
    }

    public ServerCommandName getCommandName() {
        return commandName;
    }

}
