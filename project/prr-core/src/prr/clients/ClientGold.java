package prr.clients;

public class ClientGold extends ClientLevel {

    public ClientGold(Client client) {
        super(client);
        setTariffPlan(new TariffPlanGB());
    }

    public String getName() {
        return "GOLD";
    }
}
