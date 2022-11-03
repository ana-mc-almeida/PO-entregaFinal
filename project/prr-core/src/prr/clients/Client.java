package prr.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.Network;
import prr.communications.Communication;
import prr.terminals.Terminal;
import prr.notifications.Notification;
import prr.exceptions.ClientNotificationsAlreadyEnabledException;
import prr.exceptions.ClientNotificationsAlreadyDisabledException;
import prr.exceptions.DuplicateClientKeyException;

public class Client implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private String key;
    private String name;
    private int taxId;
    private boolean allowNotifications;

    private Map<String, Terminal> terminals;

    private ClientLevel level;
    // private String level = "NORMAL";
    /* atributos que ainda não estão implementados */
    private List<Notification> notifications;

    private double debts = 0;
    private double payments = 0;

    public Client(String key, String name, int taxId) throws DuplicateClientKeyException {
        this.key = key;
        this.name = name;
        this.taxId = taxId;
        level = new ClientNormal(this);
        allowNotifications = true;
        terminals = new TreeMap<String, Terminal>();
        notifications = new ArrayList<Notification>();
    }

    public String getKey() {
        return this.key;
    }

    public boolean wantNotifications() {
        return allowNotifications;
    }

    public void addTerminal(Terminal terminal) {
        terminals.put(terminal.getKey(), terminal);
    }

    public String showLevel() {
        return level.show();
    }

    public double getDebts() {
        return debts;
    }

    public void addDebt(double newDebt) {
        debts += newDebt;
    }

    public void performPayment(double price) {
        payments += price;
        debts -= price;
    }

    /*
     * Return client in the correct format.
     * The correct format
     * {@code CLIENT|key|name|taxId|type|notifications|terminals|payments|debts}
     */
    @Override
    public String toString() {
        return "CLIENT|" + key + "|" + name + "|" + taxId + "|" + showLevel() + "|" + (allowNotifications ? "YES"
                : "NO") + "|" + terminals.size() + "|" + showPayments()
                + "|"
                + showDebts();
        // return "CLIENT|" + key + "|" + name + "|" + taxId + "|" + level.status() +
        // "|" + (allowNotifications ? "YES"
        // : "NO") + "|" + terminals.size() + "|" + calculatePayments() + "|" +
        // calculateDebts();
    }

    public void enableNotifications() throws ClientNotificationsAlreadyEnabledException {
        if (allowNotifications == true)
            throw new ClientNotificationsAlreadyEnabledException();
        allowNotifications = true;
    }

    public void disableNotifications() throws ClientNotificationsAlreadyDisabledException {
        if (allowNotifications == false)
            throw new ClientNotificationsAlreadyDisabledException();
        allowNotifications = false;
    }

    public boolean hasDebts() {
        return debts > 0;
    }

    public double getPriceTextComm(int numChars) {
        return level.getPriceTextComm(numChars);
    }

    public double getPriceVideoComm(int duration, boolean hasDiscount) {
        return level.getPriceVideoComm(duration, hasDiscount);
    }

    public double getPriceVoiceComm(int duration, boolean hasDiscount) {
        return level.getPriceVoiceComm(duration, hasDiscount);
    }

    public String showAllMadeCommunications() {
        Map<Integer, String> sortedMadeCommunications = new TreeMap<Integer, String>();
        for (Terminal terminal : terminals.values()) {
            for (Communication communication : terminal.getAllMadeCommunications()) {
                sortedMadeCommunications.put(communication.getKey(), communication.toString());
            }
        }
        return String.join("\n", sortedMadeCommunications.values());
    }

    public String showAllReceivedCommunications() {
        Map<Integer, String> sortedReceivedCommunications = new TreeMap<Integer, String>();
        for (Terminal terminal : terminals.values()) {
            for (Communication communication : terminal.getAllReceivedCommunications()) {
                sortedReceivedCommunications.put(communication.getKey(), communication.toString());
            }
        }
        return String.join("\n", sortedReceivedCommunications.values());
    }

    public long showDebts() {
        return Math.round(debts);
    }

    public long showPayments() {
        return Math.round(payments);
    }

    public void addNotification(Notification notification) {
        // System.out.println("addNotifications -> " + notification.getName() + "no
        // cliente: " + this.getKey());
        notifications.add(notification);
        // System.out.println(notifications);
    }

    public String showNotifications() {
        List<String> notificationsString = new ArrayList<String>();
        for (Notification notification : notifications) {
            notificationsString.add(notification.toString());
        }
        clearNotifications();
        return String.join("\n", notificationsString);
    }

    private void clearNotifications() {
        notifications.clear();
    }

    public boolean hasNotifications() {
        // System.out.println("bbbb" + notifications);
        // System.out.println("ahahahah + " + notifications.size());
        return notifications.size() > 0;
    }
}
