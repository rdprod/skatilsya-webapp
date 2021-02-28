package com.rdprod.springboot.spring_rdprod_webapp.service.event;

import com.rdprod.springboot.spring_rdprod_webapp.dao.EventRepository;
import com.rdprod.springboot.spring_rdprod_webapp.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public void addNewEvent(Event event) {
        eventRepository.save(event);
    }
}
