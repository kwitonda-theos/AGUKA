package com.example.UBAKA.service;

import com.example.UBAKA.model.Notification;
import com.example.UBAKA.model.User;
import com.example.UBAKA.model.enums.NotificationType;

import java.util.List;

/**
 * Service interface for managing user notifications.
 */
public interface NotificationService {
    Notification createNotification(Long userId, String title, String message, NotificationType type);
    List<Notification> getUserNotifications(Long userId);
    Notification markAsRead(Long notificationId);
    Notification getNotificationById(Long notificationId);
}
