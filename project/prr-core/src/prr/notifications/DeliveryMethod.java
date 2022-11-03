package prr.notifications;

public interface DeliveryMethod {
    public void sendNotifications();

    public void addNotification(Notification notification);

    // public void clearNotifications();
}
