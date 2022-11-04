package prr.clients;

/* Base Tariff Plan for Normal Clients */
public class TariffPlanNB extends TariffPlan {
    private static final double lessThan50 = 10;
    private static final double between50and100 = 16;
    private static final double moreThan100 = 2;

    private static final double voice = 20;
    private static final double video = 30;

    private static final double discount = 0.5;

    public double textCommPrice(int numChars) {
        if (numChars >= 100)
            return numChars * moreThan100;
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
        Double price = voice * duration;
        if (hasDiscount)
            return price * discount;
        return price;
    }
}
