package com.rdprod.springboot.spring_rdprod_webapp.service.event;

import com.rdprod.springboot.spring_rdprod_webapp.dao.EventRepository;
import com.rdprod.springboot.spring_rdprod_webapp.entity.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public void addNewEvent(Event event) {
        eventRepository.save(event);
    }
}
