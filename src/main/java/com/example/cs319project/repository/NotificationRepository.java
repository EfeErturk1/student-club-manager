package com.example.cs319project.repository;

import com.example.cs319project.model.Assignment;
import com.example.cs319project.model.Document;
import com.example.cs319project.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Notification findByNotificationId(Integer id);
    List<Notification> findAll();
    //List<Notification> findNotificationsOfClub(Integer clubId);
}
