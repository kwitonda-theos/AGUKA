package com.example.UBAKA.controller.api;

import com.example.UBAKA.model.Notification;
import com.example.UBAKA.model.User;
import com.example.UBAKA.model.enums.NotificationType;
import com.example.UBAKA.repository.UserRepository;
import com.example.UBAKA.exception.ResourceNotFoundException;
import com.example.UBAKA.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing Notification endpoints for Postman testing.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public NotificationRestController(NotificationService notificationService,
                                       UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    /**
     * GET /api/notifications/user/{userId}
     * Retrieve all notifications for a specific user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsByUser(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * POST /api/notifications?userId={id}&title={title}&message={message}&type={type}
     * Create a notification for testing purposes.
     * Valid types: INFO, ALERT, MESSAGE, JOB_UPDATE, JOB_MATCHED, VERIFICATION
     */
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestParam Long userId,
                                                            @RequestParam String title,
                                                            @RequestParam String message,
                                                            @RequestParam NotificationType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Notification notification = notificationService.createNotification(user, title, message, type);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }
}
