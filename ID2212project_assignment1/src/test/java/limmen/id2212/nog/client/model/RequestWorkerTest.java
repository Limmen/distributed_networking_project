/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

import java.io.BufferedReader;
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
public class RequestWorkerTest {
    
    public RequestWorkerTest() {
    }
    /**
     * Test of doInBackground method, of class RequestWorker.
     */
    @Test
    public void testDoInBackground() {
        final Socket socket = mock(Socket.class);
        final BufferedReader reader = mock(BufferedReader.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        } catch (IOException ex) {
            fail("Exception thrown");
        }
        final GuiController contr = mock(GuiController.class);
        RequestWorker instance = new RequestWorker(contr){
            @Override
            protected Socket createSocket(String server, int port) throws IOException{
                return socket;
            }
            @Override
            protected BufferedReader getReader() throws IOException{
                return reader;
            }
        };
        Assert.assertTrue("Get request to server successful", instance.doInBackground());
        
    }
    
}
