package prr.terminals;

import java.io.Serializable;

import prr.exceptions.CommunicationDestinationIsBusyException;
import prr.exceptions.CommunicationDestinationIsOffException;
import prr.exceptions.CommunicationDestinationIsSilentException;

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

    public abstract boolean canReceiveTextCommunication() throws CommunicationDestinationIsOffException;

    public abstract boolean canReceiveInteractiveCommunication() throws CommunicationDestinationIsOffException,
            CommunicationDestinationIsBusyException, CommunicationDestinationIsSilentException;

    public String status() {
        return this.getName();
    }

    public void returnToPreviusState() {
    }

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
