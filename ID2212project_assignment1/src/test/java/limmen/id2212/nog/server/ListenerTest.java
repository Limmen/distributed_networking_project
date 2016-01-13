/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server;

import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;
import org.fest.swing.timing.Pause;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author kim
 */
public class ListenerTest {
    
    public ListenerTest() {
    }
    /**
     * Test of run method, of class Listener.
     */
    @Test
    public void testRun() {
        final ServerSocket socket = mock(ServerSocket.class);
        String path = "src/main/resources/participants.tsv";
        Listener instance = new Listener(path);
        new Thread(instance).start();
        Pause.pause(2, TimeUnit.MILLISECONDS);
        assertEquals(instance.getPort(),8080);
        assertEquals(instance.getHost(),"0.0.0.0/0.0.0.0");
    }
    
    
}
