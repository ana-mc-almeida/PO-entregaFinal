package prr.clients;

import java.io.Serializable;

public abstract class ClientLevel implements Serializable {

    private Client client;
    private TariffPlan tariffPlan;

    public ClientLevel(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public abstract String getName();

    public String show() {
        return this.getName();
    }

    public void setTariffPlan(TariffPlan tariffPlan) {
        this.tariffPlan = tariffPlan;
    }

    public double getPriceTextComm(int numChars) {
        return tariffPlan.textCommPrice(numChars);
    }

    public double getPriceVideoComm(int duration, boolean hasDiscount) {
        return tariffPlan.videoCommPrice(duration, hasDiscount);
    }

    public double getPriceVoiceComm(int duration, boolean hasDiscount) {
        return tariffPlan.voiceCommPrice(duration, hasDiscount);
    }

    public abstract void tryUpgradeAfterPayment();

    public abstract void tryUpgradeAfterEndingCommunication();

    public abstract void addStreakText();

    public abstract void addStreakVideo();

    public abstract void addStreakVoice();

}
