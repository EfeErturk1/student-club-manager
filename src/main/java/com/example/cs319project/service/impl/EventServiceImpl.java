package com.example.cs319project.service.impl;

import com.example.cs319project.dto.EventDto;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
import com.example.cs319project.repository.EventRepository;
import com.example.cs319project.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    public List<Event> findAllEventParticipatedBy(Student s) {
        return repository.findAllByParticipantsContaining(s);
    }


    @Override
    public void saveEvent(Event event) {
        repository.save(event);
        return;
    }

    @Override
    @Transactional
    public void updateEvent(EventDto dto) {
        Event origEvent = repository.findByEventId(dto.getId());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
        mapper.map(dto, origEvent);
        System.out.println(origEvent.getClubId());
        repository.save(origEvent);
    }

    @Override
    public long findNumberOfEventsOfClub(Club club) {
        int id = club.getId();
        return repository.countAllByClubId(id);
    }

    @Override
    public Event saveorUpdate(Event event) {
        return repository.save(event);
    }

}
