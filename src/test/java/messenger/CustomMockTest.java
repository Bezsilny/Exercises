package messenger;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;

public class CustomMockTest {

    private final String VALID_SERVER = "inf.ug.edu.pl";
    private final String INVALID_SERVER = "heh.he.heh.heh";
    private final String INVALID_SERVER2 = "com";

    private final String VALID_MESSAGE = "valid message";
    private final String INVALID_MESSAGE = "zs";



    private Messenger messenger;
    private MessegeServiceMyMock myMock;


    @Before
    public void setUp() {
        myMock = new MessegeServiceMyMock();
        messenger = new Messenger(myMock);
    }

    //Connection Tests
    @Test
    public void ConnectionCorrectTest() {
        myMock.MakeConnection(true);
        assertEquals(0, messenger.testConnection(VALID_SERVER));
    }
    @Test
    public void ConnectionFailValidServerTest() {
        myMock.MakeConnection(false);
        assertEquals(1, messenger.testConnection(VALID_SERVER));
    }
    @Test
    public void ConnectionFailInvalidServerTest(){
        myMock.MakeConnection(true);
        assertThat(messenger.testConnection(INVALID_SERVER), is(1));
    }
    @Test
    public void ConnectionFailInvalidServer2Test(){
        myMock.MakeConnection(true);
        assertThat(messenger.testConnection(INVALID_SERVER2), is(1));
    }
    @Test
    public void ConnectionFailNullServerTest(){
        myMock.MakeConnection(true);
        assertThat(messenger.testConnection(null), is(1));
    }

    //Message Tests
    @Test
    public void SendMessageFine() {
        myMock.SendMessage(true);
        assertEquals(0, messenger.sendMessage(VALID_SERVER, VALID_MESSAGE));
    }
    @Test
    public void ValidDataSendingError() {
        myMock.SendMessage(false);
        assertEquals(1, messenger.sendMessage(VALID_SERVER, VALID_MESSAGE));
    }
    @Test
    public void InvalidData() {
        myMock.SendMessage(false);
        assertEquals(2, messenger.sendMessage(INVALID_SERVER, INVALID_MESSAGE));
    }
    @Test
    public void InvalidData2() {
        myMock.SendMessage(true);
        assertThat(messenger.sendMessage(INVALID_SERVER, INVALID_MESSAGE), is(2));
    }
    @Test
    public void SendingErrorTest() throws MalformedRecipientException {
        myMock.SendMessage(false);
        assertEquals(SendingStatus.SENDING_ERROR, myMock.send(VALID_SERVER, VALID_MESSAGE));
    }


    @Test
    public void SendingValidData() throws MalformedRecipientException {
        myMock.SendMessage(true);
        assertEquals(SendingStatus.SENT, myMock.send(VALID_SERVER, VALID_MESSAGE));
    }
}
