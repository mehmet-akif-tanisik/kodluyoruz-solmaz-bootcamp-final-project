package com.solmaz.notificationservice.listener;

import com.solmaz.notificationservice.model.Notification;
import com.solmaz.notificationservice.model.NotificationRequest;
import com.solmaz.notificationservice.model.factory.NotificationFactory;
import com.solmaz.notificationservice.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final NotificationFactory notificationFactory;
    private final NotificationRepository repository;


    public NotificationListener(NotificationFactory notificationFactory, NotificationRepository repository) {
        this.notificationFactory = notificationFactory;
        this.repository = repository;
    }

    @RabbitListener(queues = "notification")
    public void notificationListener(NotificationRequest notificationRequest) {

        Notification notification = notificationFactory
                .getNotification(notificationRequest.getType(), notificationRequest.getContact());

        notification.setNotificationMessage(notificationRequest.getMessage());

        repository.save(notification);
    }
}