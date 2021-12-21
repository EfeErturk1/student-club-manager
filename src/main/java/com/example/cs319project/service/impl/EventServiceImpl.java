package com.example.cs319project.service.impl;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
import com.example.cs319project.repository.ClubRepository;
import com.example.cs319project.repository.EventRepository;
import com.example.cs319project.service.ClubService;
import com.example.cs319project.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    @Override
    public Event findByEventId(Integer id){
        return repository.findByEventId(id);
    }


    @Override
    public Event addEvent(Event event){
        Objects.requireNonNull(event, "event cannot be null");
        return repository.save(event);
    }

    @Override
    public void deleteEvent(Event event){
        repository.delete(event);
    }


    @Override
    public List<Event> findAll(){
        return repository.findAll();
    }


    /*
    @Override
    public void joinEvent(Event event, Student student){
        repository.joinEvent(event, student);
    }
    */
}
