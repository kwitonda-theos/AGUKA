package com.example.AGUKA.service;

import com.example.AGUKA.model.Notification;
import com.example.AGUKA.model.User;
import com.example.AGUKA.model.enums.NotificationType;

import java.util.List;

/**
 * Service interface for managing user notifications.
 */
public interface NotificationService {

    /**
     * Creates and persists a new notification for the given user.
     *
     * @param user    the recipient
     * @param title   notification title
     * @param message notification body
     * @param type    notification type
     * @return the persisted notification
     */
    Notification createNotification(User user, String title, String message, NotificationType type);

    /**
     * Retrieves all notifications for a given user.
     *
     * @param userId the user's ID
     * @return list of notifications
     */
    List<Notification> getNotificationsByUser(Long userId);
}
