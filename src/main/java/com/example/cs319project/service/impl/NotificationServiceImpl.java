package com.example.cs319project.service.impl;

import com.example.cs319project.model.Assignment;
import com.example.cs319project.model.Document;
import com.example.cs319project.model.Notification;
import com.example.cs319project.repository.AssignmentRepository;
import com.example.cs319project.repository.NotificationRepository;
import com.example.cs319project.service.AssignmentService;
import com.example.cs319project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public Notification findByNotificationId(Integer id) {
        return repository.findByNotificationId(id);
    }

    @Override
    public Notification createNewNotification(Notification notification) {
        Objects.requireNonNull(notification, "notification cannot be null");
        return repository.save(notification);
    }

    @Override
    public void deleteNotification(Notification notification) {
        repository.delete(notification);
    }

    @Override
    public List<Notification> findAll() {
        return repository.findAll();
    }
}
