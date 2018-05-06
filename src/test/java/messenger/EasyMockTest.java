package messenger;

import static org.junit.Assert.*;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.EasyMock;
import org.easymock.MockType;
import org.easymock.TestSubject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;


@RunWith(EasyMockRunner.class)
public class EasyMockTest {
    //Takie rozwiązanie
    @TestSubject
    MessageService mockService = new MessageService() {
        @Override
        public ConnectionStatus checkConnection(String server) {
            return null;
        }

        @Override
        public SendingStatus send(String server, String message) throws MalformedRecipientException {
            return null;
        }
    };
    //

    //Lub takie
  //  private MessageService mockService;

    @Mock(type = MockType.NICE)
    private Messenger messenger;

    private final String VALID_SERVER = "inf.ug.edu.pl";
    private final String INVALID_SERVER = "heh.he.heh.heh";

    private final String VALID_MESSAGE = "valid message";
    private final String INVALID_MESSAGE = "zs";

    @Before
    public void setUp() throws Exception {
        mockService = createMock(MessageService.class);
        messenger = new Messenger(mockService);
    }

    @Test
    public void ValidServerSuccess() {
        EasyMock.expect(mockService.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS);
        EasyMock.replay(mockService);
        int result = messenger.testConnection(VALID_SERVER);
        assertEquals(0, result);
        EasyMock.verify(mockService);
    }

    @Test
    public void ValidServerFailure() {
        expect(mockService.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.FAILURE);
        replay(mockService);
        int result = messenger.testConnection(VALID_SERVER);
        assertEquals(1, result);
        EasyMock.verify(mockService);
    }

    @Test
    public void InvalidServerFailure() {
        expect(mockService.checkConnection(INVALID_SERVER)).andReturn(ConnectionStatus.FAILURE);
        replay(mockService);
        int result = messenger.testConnection(INVALID_SERVER);
        assertThat(result).isNotSameAs(0);
        verify(mockService);
    }

    @Test
    public void Null() {
        expect(mockService.checkConnection(null)).andReturn(ConnectionStatus.FAILURE);
        replay(mockService);
        int result = messenger.testConnection(null);
        assertThat(result).isOne();
        verify(mockService);
    }

    ///NOWE NOWE
    @Test
    public void BothValidMessegeSent() throws MalformedRecipientException {
        expect(mockService.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENT);
        replay(mockService);
        int result = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
        assertEquals(0, result);
        verify(mockService);
    }
    @Test
    public void BothValidMessageSendingError() throws MalformedRecipientException {
        expect(mockService.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENDING_ERROR);
        replay(mockService);
        assertEquals(1, messenger.sendMessage(VALID_SERVER, VALID_MESSAGE));
        verify(mockService);
    }

    @Test
    public void ValidServerInvalidMessage() throws MalformedRecipientException {
        expect(mockService.send(VALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
        replay(mockService);
        assertThat(messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE)).isSameAs(2);
        verify(mockService);
    }
    @Test
    public void InvalidServerValidMessage() throws MalformedRecipientException {
        EasyMock.expect(mockService.send(INVALID_SERVER, VALID_MESSAGE)).andThrow(new MalformedRecipientException());
        EasyMock.replay(mockService);
        int result = messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
        assertEquals(2, result);
        EasyMock.verify(mockService);
    }
    @Test
    public void BothMessageAndServerInvalid() throws MalformedRecipientException {
        EasyMock.expect(mockService.send(INVALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
        EasyMock.replay(mockService);
        assertEquals(2, messenger.sendMessage(INVALID_SERVER, INVALID_MESSAGE));
        EasyMock.verify(mockService);
    }
    @Test
    public void NullServer() throws MalformedRecipientException {
        EasyMock.expect(mockService.send(null, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
        EasyMock.replay(mockService);
        assertEquals(2, messenger.sendMessage(null, INVALID_MESSAGE));
        EasyMock.verify(mockService);
    }
    @Test
    public void NullMessage() throws MalformedRecipientException {
        expect(mockService.send(VALID_SERVER, null)).andThrow(new MalformedRecipientException());
        replay(mockService);
        assertThat(messenger.sendMessage(VALID_SERVER, null)).isSameAs(2);
        verify(mockService);
    }
    @Test
    public void BothMessageAndServerNull() throws MalformedRecipientException {
        EasyMock.expect(mockService.send(null,null)).andThrow(new MalformedRecipientException());
        EasyMock.replay(mockService);
        assertEquals(2, messenger.sendMessage(null,null));
        EasyMock.verify(mockService);
    }
    @After
    public void tearDown()  {
        mockService = null;
        messenger = null;
    }
}
