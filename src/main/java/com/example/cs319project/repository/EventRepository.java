package com.example.cs319project.repository;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAll();
    Event findByEventId(Integer eventId);
    List<Event> findAllByParticipantsContaining(Student s);
    long countAllByClubId(int id);
}
