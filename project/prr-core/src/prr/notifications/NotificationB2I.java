package prr.notifications;

import prr.terminals.Terminal;

public class NotificationB2I extends Notification {

    public NotificationB2I(Terminal terminal) {
        super(terminal);
    }

    public String getName() {
        return "B2I";
    }
}
