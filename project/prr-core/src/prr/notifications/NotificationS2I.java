package prr.notifications;

import prr.terminals.Terminal;

public class NotificationS2I extends Notification {
    public NotificationS2I(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return "S2I";
    }
}
