package com.solmaz.notificationservice.service.impl;

import com.solmaz.notificationservice.model.Notification;
import com.solmaz.notificationservice.repository.NotificationRepository;
import com.solmaz.notificationservice.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}
