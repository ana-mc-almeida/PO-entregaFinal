package prr.clients;

public class ClientNormal extends ClientLevel {

    public ClientNormal(Client client) {
        super(client);
        setTariffPlan(new TariffPlanPB());
    }

    public String getName() {
        return "NORMAL";
    }
}
