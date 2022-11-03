package prr.terminals;

import prr.exceptions.CommunicationDestinationIsBusyException;

public class StateBusy extends TerminalState {

    private String name = "BUSY";
    private boolean isOwner;
    private TerminalState previousState;

    public StateBusy(Terminal terminal, boolean isOwner) {
        super(terminal);
        previousState = terminal.getState();
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

    public boolean canReceiveTextCommunication(Terminal originTerminal) {
        return true;
    }

    public boolean canReceiveInteractiveCommunication(Terminal originTerminal)
            throws CommunicationDestinationIsBusyException {
        if (originTerminal.getClient().wantNotifications())
            terminal.addInteractiveNotification(originTerminal.getClient());
        throw new CommunicationDestinationIsBusyException(terminal.getKey());
    }

    // @Override
    // public void returnToPreviousState() {
    // terminal.setState(previousState);
    // }

    public void endCommunication() {
        terminal.setState(previousState);
        terminal.getState().sendNotificationsFromBusy();
    }
}
