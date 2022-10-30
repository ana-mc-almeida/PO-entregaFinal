package prr.clients;

public class ClientPlatinum extends ClientLevel {

    public ClientPlatinum(Client client) {
        super(client);
        setTariffPlan(new TariffPlanPB());
    }

    public String getName() {
        return "PLATINUM";
    }
}