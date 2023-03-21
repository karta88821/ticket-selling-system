package com.daniel_liao.ticketsellingsystem.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel_liao.ticketsellingsystem.entity.Event;
import com.daniel_liao.ticketsellingsystem.entity.Section;
import com.daniel_liao.ticketsellingsystem.form.EventForm;
import com.daniel_liao.ticketsellingsystem.repository.EventRepository;
import com.daniel_liao.ticketsellingsystem.repository.SectionRepository;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private SectionRepository sectionRepo;

    @PostMapping("/add_events")
    public List<Event> handleAddEvents(@RequestBody EventForm eventForm) {

        System.out.println(eventForm.toString());

        List<Event> events = new ArrayList<>();

        for (Date date: eventForm.getDates()) {
            Event newEvent = new Event();
            newEvent.setName(eventForm.getName());
            newEvent.setDate(date);
            events.add(newEvent);
        }

        List<Event> savedEvents = eventRepo.saveAll(events);

        return savedEvents;
    }

    @PostMapping("/add_section")
    public Section handleAddEvents(@RequestBody Section section) {

        Section savedSection = sectionRepo.save(section);

        return savedSection;
    }
}
