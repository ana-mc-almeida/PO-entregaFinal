package prr.terminals;

import prr.clients.Client;

public class BasicTerminal extends Terminal {

    public BasicTerminal(String key, Client client) {
        super(key, client);
    }

    public String getTypeName() {
        return "BASIC";
    }
}
