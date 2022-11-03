package prr.notifications;

import prr.clients.Client;
import prr.terminals.Terminal;

public abstract class Notification {
    private Terminal terminal;

    public Notification(Terminal terminal) {
        this.terminal = terminal;
    }

    public abstract String getName();

    @Override
    public String toString() {
        return getName() + "|" + terminal.getKey();
    }

}
