package prr.notifications;

import prr.terminals.Terminal;

public class NotificationO2S extends Notification {
    public NotificationO2S(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return "O2S";
    }
}
