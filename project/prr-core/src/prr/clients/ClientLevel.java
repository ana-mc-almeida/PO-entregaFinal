package prr.clients;

import java.io.Serializable;

public abstract class ClientLevel implements Serializable {

    private Client client;

    public ClientLevel(Client client) {
        this.client = client;
    }

    public abstract String getName();

    public String show() {
        return this.getName();
    }

}
