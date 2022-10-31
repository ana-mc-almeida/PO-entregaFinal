package prr.communications;

import prr.terminals.Terminal;

public abstract class Communication {
    private int key;
    private Terminal originTerminal;
    private Terminal destinationTerminal;
    private boolean ongoing;
    private double price = 0;
    private int units = 0;

    public abstract String getTypeName();

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

    public Terminal getOriginTerminal() {
        return originTerminal;
    }

    public Terminal getDestinationTerminal() {
        return destinationTerminal;
    }

    public abstract double getPrice();

    public void setOngoing(boolean value) {
        ongoing = value;
    }

    @Override
    public String toString() {
        String s = getTypeName() + "|" + key + "|" + originTerminal.getKey() + "|" + destinationTerminal.getKey()
                + "|" + units
                + "|" + price
                + "|" + getStatus();
        return s;
    }

}
