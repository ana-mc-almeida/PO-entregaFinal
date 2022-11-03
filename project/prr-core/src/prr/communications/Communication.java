package prr.communications;

import java.io.Serializable;

import prr.exceptions.InvalidCommunicationException;
import prr.terminals.Terminal;

public abstract class Communication implements Serializable {
    private int key;
    private Terminal originTerminal;
    private Terminal destinationTerminal;
    private boolean ongoing;
    private double price = 0;
    private int units = 0;
    private boolean paid;

    public abstract String getTypeName();

    public double end(int units) {
        // setOngoing(false);
        // System.out.println("EEEEEEENNNNNNDDD");
        setOngoing(null);
        this.units = units;
        price = getPrice();
        paid = false;
        // originTerminal.returnToPreviousState();
        // destinationTerminal.returnToPreviousState();
        return price;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public boolean hasEnded() {
        return !ongoing;
    }

    public boolean hasBeenPaind() {
        return paid;
    }

    public double performPayment() throws InvalidCommunicationException {
        if (!this.hasEnded() || this.hasBeenPaind())
            throw new InvalidCommunicationException(key);

        paid = true;
        return price;
    }

    public Communication(Terminal origin, Terminal destination, int key) {
        this.key = key;

        originTerminal = origin;
        origin.addMadeCommunication(this);

        destinationTerminal = destination;
        destination.addReceiveCommunication(this);

        setOngoing(this);
    }

    private void setOngoing(Communication communication) {
        if (communication == null) {
            ongoing = false;
            // System.out.println("AAAAAAAAAA");
        } else
            ongoing = true;
        originTerminal.setOngoing(communication);
        destinationTerminal.setOngoing(communication);
    }

    // public void setOngoing(boolean value) {
    // ongoing = value;
    // }

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

    @Override
    public String toString() {
        return getTypeName() + "|" + key + "|" + originTerminal.getKey()
                + "|" + destinationTerminal.getKey()
                + "|" + units
                + "|" + Math.round(price)
                + "|" + getStatus();

    }

}
