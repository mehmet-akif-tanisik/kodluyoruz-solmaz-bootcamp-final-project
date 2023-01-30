package com.solmaz.notificationservice.model.concrete;

import com.solmaz.notificationservice.model.Notification;
import com.solmaz.notificationservice.model.enums.NotificationType;

public class PushNotification extends Notification {

    private String receiverUsername;

    public PushNotification() {
        super();
        super.setNotificationType(NotificationType.PUSH);
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String toString() {
        return super.toString() +
                "Username: " + receiverUsername + "\n";
    }
}
