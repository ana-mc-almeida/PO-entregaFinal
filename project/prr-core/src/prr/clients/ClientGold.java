package prr.clients;

public class ClientGold extends ClientLevel {

    public ClientGold(Client client) {
        super(client);
        setTariffPlan(new TariffPlanGB());
    }

    public String getName() {
        return "GOLD";
    }

    public void tryUpgradeAfterEndingCommunication() {
        if (canUpgradeToNormal())
            getClient().setLevel(new ClientNormal(getClient()));
        if (canUpgradeToPlatinum())
            getClient().setLevel(new ClientPlatinum(getClient()));
    }

    private boolean canUpgradeToNormal() {
        return getClient().getBalance() < 0;
    }

    private boolean canUpgradeToPlatinum() {
        return getClient().getStreaksVideo() >= 5
                && !(getClient().getBalance() < 0);
    }

    public void tryUpgradeAfterPayment() {
    }
}
