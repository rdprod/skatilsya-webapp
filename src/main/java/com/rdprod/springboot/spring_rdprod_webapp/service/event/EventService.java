package com.rdprod.springboot.spring_rdprod_webapp.service.event;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Event;

import java.util.List;

public interface EventService {
    public List<Event> findAllEvents();

    public void addNewEvent(Event event);
}
