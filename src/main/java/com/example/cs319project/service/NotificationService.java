package com.example.cs319project.service;

import com.example.cs319project.model.Assignment;
import com.example.cs319project.model.Document;
import com.example.cs319project.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Notification findByNotificationId(Integer id);
    Notification createNewNotification(Notification notification);
    void deleteNotification(Notification notification);
    List<Notification> findAll();
    //List<Notification> findNotificationsOfClub(Integer clubId);

}
