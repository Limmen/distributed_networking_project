/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import limmen.id2212.nog.client.view.GuiController;
import org.fest.swing.timing.Pause;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author kim
 */
public class ClientHandlerTest {
    
    public ClientHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of run method, of class ClientHandler.
     */
    @Test
    public void testRun() {
        final Socket socket = mock(Socket.class);
        final File file = mock(File.class);
        final GuiController contr = mock(GuiController.class);
        final BufferedReader r = mock(BufferedReader.class);
        final OutputStream o = mock(OutputStream.class);
        final PrintWriter ow = mock(PrintWriter.class);
        ClientHandler instance = new ClientHandler(socket,file){
            @Override
            protected void setup(){
                inReader = r;
                out = o;
                outWriter = ow;
            }
        };
        instance.run();
    }
    
}
