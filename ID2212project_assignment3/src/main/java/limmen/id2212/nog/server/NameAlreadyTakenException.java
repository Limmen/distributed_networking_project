/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.nog.server;

/**
 *
 * @author kim
 */
public class NameAlreadyTakenException extends Exception {

    /**
     * Creates a new instance of <code>NameAlreadyTakenException</code> without
     * detail message.
     */
    public NameAlreadyTakenException() {
    }
    /**
     * Constructs an instance of <code>NameAlreadyTakenException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NameAlreadyTakenException(String msg) {
        super(msg);
    }
}
