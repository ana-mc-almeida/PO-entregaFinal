package prr.terminals;

import prr.exceptions.CommunicationDestinationIsOffException;
import prr.exceptions.TerminalAlreadyOffException;

public class StateOff extends TerminalState {

    private String name = "OFF";

    public StateOff(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return name;
    }

    public boolean canStartCommunication() {
        return false;
    }

    public boolean canEndCurrentCommunication() {
        return false;
    }

    public boolean canReceiveTextCommunication(Terminal originTerminal) throws CommunicationDestinationIsOffException {
        if (originTerminal.getClient().wantNotifications())
            terminal.addTextNotification(originTerminal.getClient());
        throw new CommunicationDestinationIsOffException(terminal.getKey());
    }

    public boolean canReceiveInteractiveCommunication(Terminal originTerminal)
            throws CommunicationDestinationIsOffException {
        if (originTerminal.getClient().wantNotifications())
            terminal.addInteractiveNotification(originTerminal.getClient());
        throw new CommunicationDestinationIsOffException(terminal.getKey());
    }

    public void turnOff() throws TerminalAlreadyOffException {
        throw new TerminalAlreadyOffException();
    }

    public void turnOn() {
        terminal.setState(new StateIdle(terminal));
        terminal.getState().sendNotificationsFromOff();
    }

    public void silence() {
        terminal.setState(new StateSilent(terminal));
        terminal.getState().sendNotificationsFromOff();
    }
}
