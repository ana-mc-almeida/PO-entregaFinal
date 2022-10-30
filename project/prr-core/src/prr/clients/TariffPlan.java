package prr.clients;

public abstract class TariffPlan {

    public abstract double textCommPrice(int numChars);

    public abstract double videoCommPrice(int duration, boolean hasDiscount);

    public abstract double voiceCommPrice(int duration, boolean hasDiscount);
}
