package prr.clients;

import java.io.Serializable;

public abstract class TariffPlan implements Serializable {

    public abstract double textCommPrice(int numChars);

    public abstract double videoCommPrice(int duration, boolean hasDiscount);

    public abstract double voiceCommPrice(int duration, boolean hasDiscount);
}
