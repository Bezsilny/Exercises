package messenger;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


public class MockitoJUnit5Test {

    private MessageService mockService;
    private Messenger messenger;



    private final String VALID_SERVER = "dobry.server.pl";
    private final String INVALID_SERVER = "abc.de.wooden.ja";

    private final String VALID_MESSAGE = "nice one";
    private final String INVALID_MESSAGE = "zz";

    @BeforeEach
     public void setUpJUnit5() {
        mockService = mock(MessageService.class);//JUnit5
        messenger = new Messenger(mockService);
    }


    @Test
     public void ValidServerConnectionJUnit5Test() {
        when(mockService.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.SUCCESS);
        int result = messenger.testConnection(VALID_SERVER);
        assertEquals(0, result);
        verify(mockService).checkConnection(VALID_SERVER);
    }
    @Test//AssertJ with assertThat
    public void InvalidServerConnectionJUnit5Test() {
        when(mockService.checkConnection(INVALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
        assertThat(messenger.testConnection(INVALID_SERVER)).isEqualTo(1);
        verify(mockService).checkConnection(INVALID_SERVER);
    }
    @Test
    public void ServerNullJUnit5Test() throws MalformedRecipientException {
        when(mockService.send(null,VALID_MESSAGE)).thenThrow(new MalformedRecipientException());
        int result = messenger.sendMessage(null,VALID_MESSAGE);
        assertEquals(2, result);
        verify(mockService).send(null,VALID_MESSAGE);
    }
    @Test
    public void NullInvalidJUnit5Test() throws MalformedRecipientException {
        when(mockService.send(null, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
        assertThat(messenger.sendMessage(null, INVALID_MESSAGE)).isEqualTo(2);
        verify(mockService).send(null, INVALID_MESSAGE);
    }
    @Test
    public void ValidNullJUnit5Test() throws MalformedRecipientException {
        when(mockService.send(VALID_SERVER, null)).thenThrow(new MalformedRecipientException());
        assertThat(messenger.sendMessage(VALID_SERVER, null)).isEqualTo(2);
        verify(mockService).send(VALID_SERVER, null);
    }
    @Test
    public void MessageNullJUnit5Test() throws MalformedRecipientException {
        when(mockService.send(INVALID_SERVER, null)).thenThrow(new MalformedRecipientException());
        assertThat(messenger.sendMessage(INVALID_SERVER, null)).isEqualTo(2);
        verify(mockService).send(INVALID_SERVER, null);
    }

    @AfterEach
    public void KonJUnit5() {
        mockService = null;
        messenger = null;
    }

}
