package prr.terminals;

import prr.exceptions.CommunicationDestinationIsOffException;

public class StateOff extends TerminalState {

    private String name = "OFF";

    public StateOff(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return name;
    }

    public boolean canReceiveTextCommunication() throws CommunicationDestinationIsOffException {
        throw new CommunicationDestinationIsOffException(terminal.getKey());
    }

    public boolean canStartCommunication() {
        return false;
    }

    public boolean canEndCurrentCommunication() {
        return false;
    }

    public boolean canReceiveInteractiveCommunication() throws CommunicationDestinationIsOffException {
        throw new CommunicationDestinationIsOffException(terminal.getKey());
    }
}
