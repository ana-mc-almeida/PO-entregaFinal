package prr.terminals;

import prr.exceptions.CommunicationDestinationIsSilentException;

public class StateSilent extends TerminalState {

    private String name = "SILENCE";

    public StateSilent(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return name;
    }

    public boolean canStartCommunication() {
        return true;
    }

    public boolean canReceiveTextCommunication() {
        return true;
    }

    public boolean canEndCurrentCommunication() {
        return false;
    }

    public boolean canReceiveInteractiveCommunication() throws CommunicationDestinationIsSilentException {
        throw new CommunicationDestinationIsSilentException(terminal.getKey());
    }

}
