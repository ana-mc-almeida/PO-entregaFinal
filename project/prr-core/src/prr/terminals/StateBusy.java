package prr.terminals;

import prr.exceptions.CommunicationDestinationIsBusyException;

public class StateBusy extends TerminalState {

    private String name = "BUSY";
    private boolean isOwner;
    private TerminalState previusState;

    public StateBusy(Terminal terminal, boolean isOwner) {
        super(terminal);
        previusState = terminal.getState();
        this.isOwner = isOwner;
    }

    public String getName() {
        return name;
    }

    public boolean canStartCommunication() {
        return false;
    }

    public boolean canEndCurrentCommunication() {
        return isOwner;
    }

    public boolean canReceiveTextCommunication() {
        return true;
    }

    public boolean canReceiveInteractiveCommunication() throws CommunicationDestinationIsBusyException {
        throw new CommunicationDestinationIsBusyException(terminal.getKey());
    }

    @Override
    public void returnToPreviusState() {
        terminal.setState(previusState);
    }
}
