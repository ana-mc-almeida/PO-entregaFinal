package prr.notifications;

import prr.terminals.Terminal;

public class NotificationO2I extends Notification {
    public NotificationO2I(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return "O2I";
    }
}
