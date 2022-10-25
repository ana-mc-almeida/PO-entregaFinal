package prr.clients;

public class ClientPlatinum extends ClientLevel {

    public ClientPlatinum(Client client) {
        super(client);
    }

    public String getName() {
        return "PLATINUM";
    }
}