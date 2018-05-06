package messenger;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

//import messenger.ConnectionStatus;
//import messenger.MessageService;


import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.runner.RunWith;
//import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.junit.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

	//co zastępujemy
	@Mock
	private MessageService mockService;
	//nasza atrapa
	@InjectMocks
	private Messenger messenger;

	//Messenger messenger = new Messenger();

	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.eu";

	private final String VALID_MESSAGE = "valid message";
	private final String INVALID_MESSAGE = "zs";

	@Before
	public void setUp() {
	//	MockitoAnnotations.initMocks(this);  //albo to albo @RunWith(MockitoJUnitRunner.class)
		messenger = new Messenger(mockService);
	}


	@Test
	public void ValidServerConnectionTest() {
		when(mockService.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.SUCCESS);
		int result = messenger.testConnection(VALID_SERVER);
		assertEquals(0, result);
		verify(mockService).checkConnection(VALID_SERVER);
	}
	@Test
	public void ValidServerConnectionFailureTest() {
		when(mockService.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
		int result = messenger.testConnection(VALID_SERVER);
		assertEquals(1, result);
		verify(mockService).checkConnection(VALID_SERVER);
	}
	@Test//AssertJ with assertThat
	public void InvalidServerConnectionTest() {
		when(mockService.checkConnection(INVALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
		assertThat(messenger.testConnection(INVALID_SERVER)).isEqualTo(1);
		verify(mockService).checkConnection(INVALID_SERVER);
	}
//NEW HERE
	@Test
	public void ValidAndSendSuccess() throws MalformedRecipientException {
		when(mockService.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENT);
		int result = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertEquals(0, result);
		verify(mockService).send(VALID_SERVER, VALID_MESSAGE);
}
	@Test
	public void sendMessageWithValidReturnOne() throws MalformedRecipientException {
		when(mockService.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENDING_ERROR);
		assertThat(messenger.sendMessage(VALID_SERVER, VALID_MESSAGE)).isEqualTo(1);
		verify(mockService).send(VALID_SERVER, VALID_MESSAGE);
	}
	@Test
	public void InvalidServer() throws MalformedRecipientException {
		when(mockService.send(INVALID_SERVER, VALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		int result = messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		assertEquals(2, result);
		verify(mockService).send(INVALID_SERVER, VALID_MESSAGE);
	}
	@Test
	public void InvalidMessage() throws MalformedRecipientException {
		when(mockService.send(VALID_SERVER, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		int result = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertEquals(2, result);
		verify(mockService).send(VALID_SERVER, INVALID_MESSAGE);
	}
	@Test
	public void BothInvalid() throws MalformedRecipientException {
		when(mockService.send(INVALID_SERVER, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		assertThat(messenger.sendMessage(INVALID_SERVER, INVALID_MESSAGE)).isEqualTo(2);
		verify(mockService).send(INVALID_SERVER, INVALID_MESSAGE);
	}
	@Test
	public void BothNull() throws MalformedRecipientException {
		when(mockService.send(null,null)).thenThrow(new MalformedRecipientException());
		int result = messenger.sendMessage(null,null);
		assertEquals(2, result);
		verify(mockService).send(null,null);
	}
	@Test
	public void ServerNull() throws MalformedRecipientException {
		when(mockService.send(null,VALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		int result = messenger.sendMessage(null,VALID_MESSAGE);
		assertEquals(2, result);
		verify(mockService).send(null,VALID_MESSAGE);
	}
	@Test
	public void NullInvalid() throws MalformedRecipientException {
		when(mockService.send(null, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		assertThat(messenger.sendMessage(null, INVALID_MESSAGE)).isEqualTo(2);
		verify(mockService).send(null, INVALID_MESSAGE);
	}
	@Test
	public void ValidNull() throws MalformedRecipientException {
		when(mockService.send(VALID_SERVER, null)).thenThrow(new MalformedRecipientException());
		assertThat(messenger.sendMessage(VALID_SERVER, null)).isEqualTo(2);
		verify(mockService).send(VALID_SERVER, null);
	}
	@Test
	public void MessageNull() throws MalformedRecipientException {
		when(mockService.send(INVALID_SERVER, null)).thenThrow(new MalformedRecipientException());
		assertThat(messenger.sendMessage(INVALID_SERVER, null)).isEqualTo(2);
		verify(mockService).send(INVALID_SERVER, null);
	}

	@After
	public void Koniec() throws Exception {
		mockService = null;
		messenger = null;
	}
}
