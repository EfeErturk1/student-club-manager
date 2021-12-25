

package com.example.cs319project.service;

import com.example.cs319project.dto.EventDto;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
import com.example.cs319project.model.request.AddEventRequest;

import java.util.List;

public interface EventService {
    Event findByEventId(Integer id);
    Event addEvent(Event event);
    void deleteEvent(Event event);
    List<Event> findAll();
    List<Event> findAllEventParticipatedBy(Student s);
    void saveEvent(Event event);
    void updateEvent(EventDto event);
    long findNumberOfEventsOfClub(Club club);
    Event saveorUpdate(Event event);
    //void joinEvent(Event event, Student student);
}
