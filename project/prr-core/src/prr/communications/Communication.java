package prr.communications;

import java.io.Serializable;

import prr.terminals.Terminal;

public abstract class Communication implements Serializable {
    private int key;
    private Terminal originTerminal;
    private Terminal destinationTerminal;
    private boolean ongoing;
    private double price = 0;
    private int units = 0;

    public abstract String getTypeName();

    public double end(int units) {
        setOngoing(false);
        this.units = units;
        price = getPrice();
        originTerminal.returnToPreviusState();
        destinationTerminal.returnToPreviusState();
        return price;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public Communication(Terminal origin, Terminal destination, int key) {
        this.key = key;
        originTerminal = origin;
        destinationTerminal = destination;
        ongoing = true;
    }

    public String getStatus() {
        if (ongoing)
            return "ONGOING";
        return "FINISHED";
    }

    public int getKey() {
        return key;
    }

    public int getUnits() {
        return units;
    }

    // public void setPrice(double price) {
    // this.price = price;
    // }

    public Terminal getOriginTerminal() {
        return originTerminal;
    }

    public Terminal getDestinationTerminal() {
        return destinationTerminal;
    }

    public boolean getOnGoing() {
        return ongoing;
    }

    public abstract double getPrice();

    public void setOngoing(boolean value) {
        ongoing = value;
    }

    @Override
    public String toString() {
        return getTypeName() + "|" + key + "|" + originTerminal.getKey()
                + "|" + destinationTerminal.getKey()
                + "|" + units
                + "|" + Math.round(price)
                + "|" + getStatus();

    }

}
