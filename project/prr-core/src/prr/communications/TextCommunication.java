package prr.communications;

import prr.Network;
import prr.clients.Client;
import prr.exceptions.CommunicationDestinationIsOffException;
import prr.terminals.Terminal;
import prr.clients.Client;

public class TextCommunication extends Communication {

    // private String body;

    public TextCommunication(Terminal origin, Terminal destination, int key, String body) {

        super(origin, destination, key);

        // this.body = body;

        end(body.length());
    }

    public double getPrice() {
        return getOriginTerminal().getClient().getPriceTextComm(getUnits());
    }

    public String getTypeName() {
        return "TEXT";
    }

    public void updateClientAfterEndingCommunication(Client client) {
        client.addStreakText();
        client.tryToUpdateAfterEndingCommunication();
    }

}
