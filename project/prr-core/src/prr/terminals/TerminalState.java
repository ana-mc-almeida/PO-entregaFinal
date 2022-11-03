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
}
