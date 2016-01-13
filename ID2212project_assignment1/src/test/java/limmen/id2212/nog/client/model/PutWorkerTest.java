/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.nog.client.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import limmen.id2212.nog.client.view.GuiController;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author kim
 */
public class PutWorkerTest {
    
    public PutWorkerTest() {
    }
    /**
     * Test of doInBackground method, of class PutWorker.
     */
    @Test
    public void testDoInBackground() {
        final Socket socket = mock(Socket.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        } catch (IOException ex) {
            fail("Exception thrown");
        }
        final GuiController contr = mock(GuiController.class);
        PutWorker instance = new PutWorker(contr,contr.getParticipantsList()){
            @Override
            protected Socket createSocket(String server, int port) throws IOException{
                return socket;
            }
        };
        Assert.assertTrue("Put request to server successful", instance.doInBackground());
        
    }
    
}
