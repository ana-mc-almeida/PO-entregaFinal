package prr.terminals;

import prr.exceptions.CommunicationDestinationIsSilentException;
import prr.exceptions.TerminalAlreadySilentException;

import prr.clients.Client;
import prr.notifications.NotificationO2S;

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

    public boolean canReceiveTextCommunication(Terminal originTerminal) {
        return true;
    }

    public boolean canEndCurrentCommunication() {
        return false;
    }

    public boolean canReceiveInteractiveCommunication(Terminal originTerminal)
            throws CommunicationDestinationIsSilentException {
        if (originTerminal.getClient().wantNotifications())
            terminal.addInteractiveNotification(originTerminal.getClient());
        throw new CommunicationDestinationIsSilentException(terminal.getKey());
    }

    public void turnOff() {
        terminal.setState(new StateOff(terminal));
    }

    public void turnOn() {
        terminal.setState(new StateIdle(terminal));
        terminal.getState().sendNotificationsFromSilence();
    }

    public void silence() throws TerminalAlreadySilentException {
        throw new TerminalAlreadySilentException();
    }

    public void startInterativeCommunication() {
        terminal.setState(new StateBusy(terminal, true));
    }

    // public void sendNotificationsFromBusy() {
    // /* não existem notificações de busy to silence */
    // }

    public void sendNotificationsFromOff() {
        for (Client client : terminal.getTextNotifications().values()) {
            client.addNotification(new NotificationO2S(terminal));
        }
        terminal.getTextNotifications().clear();
    }

}
