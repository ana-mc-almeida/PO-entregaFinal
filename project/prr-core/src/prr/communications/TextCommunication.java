package prr.communications;

import prr.Network;
import prr.exceptions.CommunicationDestinationIsOffException;
import prr.terminals.Terminal;

public class TextCommunication extends Communication {

    // private String body;

    public TextCommunication(Terminal origin, Terminal destination, int key, String body)
            throws CommunicationDestinationIsOffException {

        super(origin, destination, key);

        destination.canReceiveTextCommunication();

        // this.body = body;

        end(body.length());
    }

    public double getPrice() {
        return getOriginTerminal().getClient().getPriceTextComm(getUnits());
    }

    public String getTypeName() {
        return "TEXT";
    }

}
