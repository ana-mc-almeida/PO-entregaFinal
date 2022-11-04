package prr.notifications;

import java.io.Serializable;

public interface DeliveryMethod extends Serializable {
    public void sendNotifications();

    public void addNotification(Notification notification);

}
