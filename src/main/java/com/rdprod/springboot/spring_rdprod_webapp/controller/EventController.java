package com.rdprod.springboot.spring_rdprod_webapp.controller;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Event;
import com.rdprod.springboot.spring_rdprod_webapp.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public String showEventsPage(Model model) {
        List<Event> events = eventService.findAllEvents();
        model.addAttribute("events", events);
        model.addAttribute("newEvent", new Event());

        return "events";
    }

    @PostMapping("/addNewEventProcess")
    public String addNewEvent(@ModelAttribute("newEvent") Event newEvent) {
        newEvent.setDate(new Date());
        eventService.addNewEvent(newEvent);

        return "redirect:/events";
    }
}
