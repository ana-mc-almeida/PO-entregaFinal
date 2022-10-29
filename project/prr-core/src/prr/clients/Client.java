package prr.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.DuplicateClientKeyException;

public class Client implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private String key;
    private String name;
    private int taxId;
    private boolean allowNotifications;

    private List<Terminal> terminals;

    private ClientLevel level;
    // private String level = "NORMAL";
    /* atributos que ainda não estão implementados */
    // private Notification[] notifications;

    public Client(String key, String name, int taxId) throws DuplicateClientKeyException {
        this.key = key;
        this.name = name;
        this.taxId = taxId;
        level = new ClientNormal(this);
        allowNotifications = true;
        terminals = new ArrayList<Terminal>();
    }

    public String getKey() {
        return this.key;
    }

    public void addTerminal(Terminal terminal) {
        terminals.add(terminal);
    }

    public String showLevel() {
        return level.show();
    }

    // public String getName() {
    // return this.name;
    // }

    // public String getTaxId() {
    // return this.taxId;
    // }

    public Double calculatePayments() {
        Double payments = 0.0;
        for (Terminal terminal : terminals) {
            payments += terminal.getPayments();
        }
        return payments;
    }

    public Double calculateDebts() {
        Double debts = 0.0;
        for (Terminal terminal : terminals) {
            debts += terminal.getDebts();
        }
        return debts;
    }

    /*
     * Return client in the correct format.
     * The correct format
     * {@code CLIENT|key|name|taxId|type|notifications|terminals|payments|debts}
     */
    @Override
    public String toString() {
        return "CLIENT|" + key + "|" + name + "|" + taxId + "|" + showLevel() + "|" + (allowNotifications ? "YES"
                : "NO") + "|" + terminals.size() + "|" + Math.round(calculatePayments())
                + "|"
                + Math.round(calculateDebts());
        // return "CLIENT|" + key + "|" + name + "|" + taxId + "|" + level.status() +
        // "|" + (allowNotifications ? "YES"
        // : "NO") + "|" + terminals.size() + "|" + calculatePayments() + "|" +
        // calculateDebts();
    }

    /*
     * return True if the new state is differente from previous:
     * true if allowNotifications = false
     * false if allowNotifications = true
     */
    public boolean enableNotifications() {
        boolean different = allowNotifications == false;
        allowNotifications = true;
        return different;
    }

    /*
     * return True if the new state is differente from previous:
     * true if allowNotifications = true
     * false if allowNotifications = false
     */
    public boolean disableNotifications() {
        boolean different = allowNotifications == true;
        allowNotifications = false;
        return different;
    }

    public boolean hasDebts() {
        return this.calculateDebts() > 0;
    }

}
