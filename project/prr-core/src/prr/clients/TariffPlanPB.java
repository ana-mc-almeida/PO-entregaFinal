package prr.clients;

/* Base Tariff Plan for Platinum Clients */
public class TariffPlanPB extends TariffPlan {
    private static final double lessThan50 = 0;
    private static final double between50and100 = 4;
    private static final double moreThan100 = 4;

    private static final double voice = 10;
    private static final double video = 10;

    private static final double discount = 0.5;

    public double textCommPrice(int numChars) {
        if (numChars >= 100)
            return moreThan100;
        if (numChars >= 50)
            return between50and100;
        return lessThan50;
    }

    public double videoCommPrice(int duration, boolean hasDiscount) {
        Double price = video * duration;
        if (hasDiscount)
            return price * discount;
        return price;
    }

    public double voiceCommPrice(int duration, boolean hasDiscount) {
        // System.out.println("Voice4 -> " + duration);
        Double price = voice * duration;
        if (hasDiscount)
            return price * discount;
        return price;
    }
}
