package com.solmaz.notificationservice.model.factory;

import com.solmaz.notificationservice.model.concrete.EmailNotification;
import com.solmaz.notificationservice.model.Notification;
import com.solmaz.notificationservice.model.concrete.PushNotification;
import com.solmaz.notificationservice.model.concrete.SmsNotification;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

    public Notification getNotification(String type, String contact) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("MAIL")) {
            EmailNotification emailNotification = new EmailNotification();
            emailNotification.setEmail(contact);
            return emailNotification;
        } else if (type.equalsIgnoreCase("PUSH")) {
            PushNotification pushNotification = new PushNotification();
            pushNotification.setReceiverUsername(contact);
            return pushNotification;
        } else if (type.equalsIgnoreCase("SMS")) {
            SmsNotification smsNotification = new SmsNotification();
            smsNotification.setTelephoneNumber(contact);
            return smsNotification;
        }
        return null;
    }
}
