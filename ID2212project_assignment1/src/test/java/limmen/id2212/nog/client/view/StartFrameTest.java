/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.util.concurrent.TimeUnit;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author kim
 */
public class StartFrameTest {
    private FrameFixture testFrame;
    final GuiController contr = mock(GuiController.class);
    //private AllTypesFrame  frame;
    public StartFrameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        //FailOnThreadViolationRepaintManager.install();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testFrame = new FrameFixture(new StartFrame(contr));
        testFrame.show();
    }
    
    @After
    public void tearDown() {
        testFrame.cleanUp();
    }
    
    @Test
    public void testGui() {
        testFrame.label("title_label").requireText("Connect to a server");
        testFrame.label("host_label").requireText("Host: ");
        testFrame.label("port_label").requireText("Port: ");
        testFrame.textBox("host_field").setText("");
        testFrame.textBox("port_field").setText("");
        testFrame.button("connect_button").click();
        //testFrame.dialog("invalid_input").requireVisible();
        testFrame.requireVisible();
    }
    
}
