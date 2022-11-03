package prr.clients;

public class ClientNormal extends ClientLevel {

    public ClientNormal(Client client) {
        super(client);
        setTariffPlan(new TariffPlanNB());
    }

    public String getName() {
        return "NORMAL";
    }

    public void tryUpgradeAfterEndingCommunication() {

    }

    public void tryUpgradeAfterPayment() {
        if (canUpgradeToGold()) {
            getClient().setLevel(new ClientGold(getClient()));
        }
    }

    private boolean canUpgradeToGold() {
        return getClient().getBalance() > 500;
    }
}
