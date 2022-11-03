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
    private List<Notification> notifications;

    private double debts = 0;
    private double payments = 0;

    private int streaksText = 0;
    private int streaksVideo = 0;
    private int streaksVoice = 0;

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

    public void setLevel(ClientLevel level) {
        this.level = level;
    }

    public double getBalance() {
        return payments - debts;
    }

    public double getPayments() {
        return payments;
    }

    public int getStreaksVideo() {
        return streaksVideo;
    }

    public int getStreaksText() {
        return streaksText;
    }

    public void addDebt(double newDebt) {
        debts += newDebt;
    }

    public void performPayment(double price) {
        payments += price;
        debts -= price;
        level.tryUpgradeAfterPayment();
    }

    public void addStreakText() {
        streaksText++;
        streaksVideo = 0;
        streaksVoice = 0;
    }

    public void addStreakVideo() {
        streaksVideo++;
        streaksText = 0;
        streaksVoice = 0;
    }

    public void addStreakVoice() {
        streaksVoice++;
        streaksVideo = 0;
        streaksText = 0;
    }

    public void tryToUpdateAfterEndingCommunication() {
        level.tryUpgradeAfterEndingCommunication();
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
        notifications.add(notification);
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
        return notifications.size() > 0;
    }
}
