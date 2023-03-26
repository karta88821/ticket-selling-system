package com.daniel_liao.ticketsellingsystem.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    // EVENTS: [Event]
    private final String EVENTS = "EVENTS";
    
    @Autowired
    private EventRepository eventRepo;
    
    @Cacheable(EVENTS)
    public List<Event> getAllEvents() {
        System.out.println("Query all events from the db");
        List<Event> events = eventRepo.findAll();
        return events;
    }

    @Cacheable(value = EVENTS, key = "#id")
    public Event getEventById(Integer id) {
        System.out.println("Query event with id " + id + " from the db");
        return eventRepo.findById(id).get();
    }
    
    @CacheEvict(value = EVENTS, allEntries = true) 
    public void addNewEvents(EventForm eventForm) throws ParseException {
        List<Event> events = new ArrayList<>(); 
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        for (String dateString: eventForm.getDates()) {
            Event newEvent = new Event();
            newEvent.setName(eventForm.getName());
            java.util.Date date = format.parse(dateString);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            newEvent.setDate(sqlDate);
            events.add(newEvent);
        }

        eventRepo.saveAll(events);
    }
}
