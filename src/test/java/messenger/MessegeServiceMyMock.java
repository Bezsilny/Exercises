package messenger;

public class MessegeServiceMyMock implements MessageService{

    private boolean connection = false;
    private boolean sending = false;

    @Override
    public ConnectionStatus checkConnection(String server) {
        if (server != null && connection  && server.length() > 7 && server.length() < 30 && (server.contains("com") || server.contains("pl"))) {
            return ConnectionStatus.SUCCESS;
        }
        return ConnectionStatus.FAILURE;
    }

    @Override
    public SendingStatus send(String server, String message)
            throws MalformedRecipientException {
        if(message.length() < 4 || message.length() > 300){
            throw new MalformedRecipientException();
        }
        if (server == null) {
            throw new MalformedRecipientException();
        }
        if (message == null){
            throw new MalformedRecipientException();
        }
        if (sending){
            return SendingStatus.SENT;
        }
        return SendingStatus.SENDING_ERROR;
    }

    public void MakeConnection(boolean state) {
        connection = state;
    }

    public void SendMessage(boolean state) {
        sending = state;
    }

}
