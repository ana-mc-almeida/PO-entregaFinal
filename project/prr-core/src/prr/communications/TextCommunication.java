package prr.communications;

import prr.Network;
import prr.exceptions.CommunicationDestinationIsOffException;
import prr.terminals.Terminal;

public class TextCommunication extends Communication {

    private String body;

    public TextCommunication(Terminal origin, Terminal destination, int key, String body)
            throws CommunicationDestinationIsOffException {

        super(origin, destination, key);

        destination.canReceiveTextCommunication();

        this.body = body;
        // setPrice(getPrice(body.length()));
        setOngoing(false);
    }

    public double getPrice(int numChars) {
        return 0.0; /* FIXME */
    }

    public String getTypeName() {
        return "TEXT";
    }
}
