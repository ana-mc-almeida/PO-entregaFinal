package prr.terminals;

import java.io.Serializable;

import prr.exceptions.CommunicationDestinationIsBusyException;
import prr.exceptions.CommunicationDestinationIsOffException;
import prr.exceptions.CommunicationDestinationIsSilentException;
import prr.exceptions.TerminalAlreadyOffException;
import prr.exceptions.TerminalAlreadyOnException;
import prr.exceptions.TerminalAlreadySilentException;

/**
 * Abstract terminal state.
 */
public abstract class TerminalState implements Serializable {

    protected Terminal terminal;

    public TerminalState(Terminal terminal) {
        this.terminal = terminal;
    }

    public abstract String getName();

    public abstract boolean canStartCommunication();

    public abstract boolean canEndCurrentCommunication();

    public abstract boolean canReceiveTextCommunication(Terminal originTerminal)
            throws CommunicationDestinationIsOffException;

    public abstract boolean canReceiveInteractiveCommunication(Terminal originTerminal)
            throws CommunicationDestinationIsOffException,
            CommunicationDestinationIsBusyException, CommunicationDestinationIsSilentException;

    public String status() {
        return this.getName();
    }

    public void turnOff() throws TerminalAlreadyOffException {
    }

    public void turnOn() throws TerminalAlreadyOnException {
    }

    public void silence() throws TerminalAlreadySilentException {
    }

    public void startInterativeCommunication() {
    }

    public void receiveInterativeCommunication() {
    }

    public void endCommunication() {
    }

    public void sendNotificationsFromOff() {
    }

    public void sendNotificationsFromBusy() {
    }

    public void sendNotificationsFromSilence() {
    }

    // public void returnToPreviousState() {
    // }

    // public void addInteractiveNotification() {
    // if (terminal.getClient().wantNotifications()) {
    // addInteractiveNotification(getClient());
    // }
    // }

    // public void addTextNotification() {
    // if (getClient().wantNotifications()) {
    // addTextNotification(getClient());
    // }
    // }

    // @Override
    // public boolean equals(Object o) {
    // if (o instanceof TerminalState) {
    // TerminalState state = (TerminalState) o;
    // return getName().equals(o.getName); // nao funciona porque TerminalState tem
    // getName como abstract
    // }
    // return false;
    // }
}
