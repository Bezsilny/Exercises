package messenger;

import messenger.ConnectionStatus;
import messenger.MalformedRecipientException;
import messenger.MessageService;
import messenger.SendingStatus;

public class Messenger {

	private MessageService ms;

	public Messenger(MessageService ms) {
		this.ms = ms;
	}

	///*Tymczasowo -> Pozniej to zniknie */
	//public Messenger() {
	//	// TODO Auto-generated constructor stub
	//}

	//Testowanie polaczenia z serwerem
	public int testConnection(String server) {
		if (ms.checkConnection(server) == ConnectionStatus.FAILURE) {
			return 1;
		} else {
			return 0;
		}

	}


	public int sendMessage(String server, String message) {

		int result = -1;
		try{
			SendingStatus status = ms.send(server, message);
			if(status == SendingStatus.SENDING_ERROR){
				result = 1;
			}
			else if(status == SendingStatus.SENT) {
				result = 0;
			}

		}catch (MalformedRecipientException ex){
			result = 2;
		}
		return result;
	}
}
