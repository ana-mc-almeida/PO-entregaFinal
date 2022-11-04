package prr.clients;

public class ClientGold extends ClientLevel {

    private int streaksVideo = 0;

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
        return streaksVideo >= 5
                && !(getClient().getBalance() < 0);
    }

    public void tryUpgradeAfterPayment() {
    }

    public void addStreakText() {
        streaksVideo = 0;
    }

    public void addStreakVideo() {
        streaksVideo++;
    }

    public void addStreakVoice() {
        streaksVideo = 0;
    }
}
