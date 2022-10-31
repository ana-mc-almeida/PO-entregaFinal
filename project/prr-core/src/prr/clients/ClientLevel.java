package prr.clients;

import java.io.Serializable;

public abstract class ClientLevel implements Serializable {

    private Client client;
    private TariffPlan tariffPlan;

    public ClientLevel(Client client) {
        this.client = client;
    }

    public abstract String getName();

    public String show() {
        return this.getName();
    }

    public void setTariffPlan(TariffPlan tariffPlan) {
        this.tariffPlan = tariffPlan;
    }

    public double getPriceTextComm(int numChars) {
        // System.out.println("aaaa" + tariffPlan.textCommPrice(numChars));
        return tariffPlan.textCommPrice(numChars);
    }

    public double getPriceVideoComm(int duration, boolean hasDiscount) {
        return tariffPlan.videoCommPrice(duration, hasDiscount);
    }

    public double getPriceVoiceComm(int duration, boolean hasDiscount) {
        return tariffPlan.voiceCommPrice(duration, hasDiscount);

    }

}
