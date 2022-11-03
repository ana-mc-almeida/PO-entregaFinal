package prr.terminals;

import java.util.Map;
import java.util.TreeMap;

import prr.clients.Client;
import prr.exceptions.TerminalAlreadyOnException;
import prr.notifications.NotificationB2I;
import prr.notifications.NotificationS2I;
import prr.notifications.NotificationO2I;

public class StateIdle extends TerminalState {

    private String name = "IDLE";

    public StateIdle(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return name;
    }

    public boolean canStartCommunication() {
        return true;
    }

    public boolean canEndCurrentCommunication() {
        return false;
    }

    public boolean canReceiveTextCommunication(Terminal originTerminal) {
        return true;
    }

    public boolean canReceiveInteractiveCommunication(Terminal originTerminal) {
        return true;
    }

    public void turnOff() {
        terminal.setState(new StateOff(terminal));
    }

    public void turnOn() throws TerminalAlreadyOnException {
        throw new TerminalAlreadyOnException();
    }

    public void silence() {
        terminal.setState(new StateSilent(terminal));
    }

    public void startInterativeCommunication() {
        terminal.setState(new StateBusy(terminal, true));
    }

    public void receiveInterativeCommunication() {
        terminal.setState(new StateBusy(terminal, false));
    }

    public void sendNotificationsFromBusy() {
        for (Client client : terminal.getInteractiveNotifications().values()) {
            // if (client.wantNotifications())
            client.addNotification(new NotificationB2I(terminal));
        }
        terminal.getInteractiveNotifications().clear();
    }

    public void sendNotificationsFromSilence() {
        for (Client client : terminal.getInteractiveNotifications().values()) {
            // if (client.wantNotifications())
            client.addNotification(new NotificationS2I(terminal));
        }
        terminal.getInteractiveNotifications().clear();
    }

    public void sendNotificationsFromOff() {
        // System.out.println("estive no sendNotificationsFromOff aaaaaaaaaaas");

        Map<String, Client> allNotifications = new TreeMap<String, Client>();
        allNotifications.putAll(terminal.getTextNotifications());
        allNotifications.putAll(terminal.getInteractiveNotifications());
        for (Client client : allNotifications.values()) {
            // if (client.wantNotifications())
            client.addNotification(new NotificationO2I(terminal));
        }

        terminal.getTextNotifications().clear();
        terminal.getInteractiveNotifications().clear();
    }
}
