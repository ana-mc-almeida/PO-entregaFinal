package prr.clients;

public class ClientPlatinum extends ClientLevel {

    public ClientPlatinum(Client client) {
        super(client);
        setTariffPlan(new TariffPlanPB());
    }

    public String getName() {
        return "PLATINUM";
    }

    public void tryUpgradeAfterEndingCommunication() {
        if (canUpgradeToNormal())
            getClient().setLevel(new ClientNormal(getClient()));
        if (canUpgradeToGold())
            getClient().setLevel(new ClientGold(getClient()));
    }

    private boolean canUpgradeToNormal() {
        return getClient().getBalance() < 0;
    }

    private boolean canUpgradeToGold() {
        return getClient().getStreaksText() >= 2
                && !(getClient().getBalance() < 0);
    }

    public void tryUpgradeAfterPayment() {
    }
}