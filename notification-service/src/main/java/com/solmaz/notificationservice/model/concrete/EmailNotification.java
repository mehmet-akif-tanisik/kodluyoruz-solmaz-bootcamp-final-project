package com.solmaz.notificationservice.model.concrete;

import com.solmaz.notificationservice.model.Notification;
import com.solmaz.notificationservice.model.enums.NotificationType;

public class EmailNotification extends Notification {
    private String email;

    public EmailNotification() {
        super();
        super.setNotificationType(NotificationType.MAIL);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return super.toString() +
                "Email: " + email + "\n";
    }
}
