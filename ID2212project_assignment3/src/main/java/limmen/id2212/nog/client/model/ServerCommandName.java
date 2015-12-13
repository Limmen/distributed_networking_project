/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

/**
 * Enum that represents name of server-commands
 * @author kim
 */
public enum ServerCommandName {
    
    /**
     * Command to fetch list of clients connected to the server.
     */
    getClients,
    
    /**
     * Command to register a client at the server.
     */
    registerClient,
    
    /**
     * Command to deRegister a client at the server.
     */
    deRegisterClient,
    
    /**
     * Command to fetch list of chatrooms from the server.
     */
    getChatRooms,
    
    /**
     * Command to add a chatroom at the server.
     */
    addChatRoom,
    
    /**
     * Command to send message to a existing chatroom.
     */
    sendMessage,
    
    /**
     * Command to join a existing chatroom.
     */
    joinChat,
    
    /**
     * Command to destroy a chatroom.
     */
    destroyChatRoom,
    
    /**
     * Command to leave a chatroom.
     */
    leaveChatRoom,
    
    /**
     * Command to create a private chatroom.
     */
    privateChatRoom;
}
