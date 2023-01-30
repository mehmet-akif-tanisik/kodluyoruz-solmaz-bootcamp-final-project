package com.solmaz.notificationservice.model.concrete;

import com.solmaz.notificationservice.model.Notification;
import com.solmaz.notificationservice.model.enums.NotificationType;

public class SmsNotification extends Notification {

    private String telephoneNumber;

    public SmsNotification() {
        super();
        super.setNotificationType(NotificationType.SMS);
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String toString() {
        return super.toString() +
                "Mobile Number: " + telephoneNumber + "\n";
    }
}
