package com.daniel_liao.ticketsellingsystem.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daniel_liao.ticketsellingsystem.ISectionStatus;
import com.daniel_liao.ticketsellingsystem.entity.Event;
import com.daniel_liao.ticketsellingsystem.entity.Seat;
import com.daniel_liao.ticketsellingsystem.entity.Section;
import com.daniel_liao.ticketsellingsystem.form.EventForm;
import com.daniel_liao.ticketsellingsystem.repository.EventRepository;
import com.daniel_liao.ticketsellingsystem.repository.SeatRepository;
import com.daniel_liao.ticketsellingsystem.repository.SectionRepository;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private SectionRepository sectionRepo;

    @Autowired
    private SeatRepository seatRepository;
    
    @GetMapping("/all")
    public String eventsListPage(Model model) {
        List<Event> events = eventRepo.findAll();
        model.addAttribute("events", events);
        model.addAttribute("activeLink", "Events");
        model.addAttribute("eventForm", new EventForm());
        return "events";
    }

    @PostMapping("/do-add-events")
    public String handleAddEvents(@Valid EventForm eventForm, BindingResult result, Model model) throws ParseException {
        if (result.hasErrors()) {
            List<String> errorStrings = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                errorStrings.add(error.getDefaultMessage());
            }
            model.addAttribute("form_error", errorStrings);
            model.addAttribute("events", eventRepo.findAll()); // use Redis!!!
            return "events";
        }

        System.out.println(eventForm.toString());
        
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

        return "redirect:/events/all";
    }

    @GetMapping("/{event_id}/sections/all")
    public String eventBindSectionPage(@PathVariable(value = "event_id") Integer id, Model model) {
        Event event = eventRepo.findById(id).get();
        model.addAttribute("event", event);

        List<Section> sectionsNotSelect = sectionRepo.findSectionsEventNotSelect(id);
        Section selected_section = new Section();
        model.addAttribute("sectionsNotSelect", sectionsNotSelect);
        model.addAttribute("section", selected_section);

        List<ISectionStatus> sectionStatuses = seatRepository.findAvailableSeatsForAnEvent(id);
        for (ISectionStatus sectionStatus: sectionStatuses) {
            System.out.println(sectionStatus.getAvSeats());
        }
        model.addAttribute("sectionStatuses", sectionStatuses);

        return "event-bind-sections";
    }

    @PostMapping("/{event_id}/sections/do-bind-section")
    public String doBindSection(@PathVariable(value = "event_id") Integer id, Section selectedSection) {

        Section section = sectionRepo.findById(selectedSection.getId()).get();

        Event event = new Event();
        event.setId(id);

        List<Seat> seats = new ArrayList<>(); 
        
        // add all of the seats to the event
        for (int i=1; i<=section.getTotalRow(); i++) {
            for (int j=1; j<=section.getTotalNumberPerRow(); j++) {
                Seat seat = new Seat();
                seat.setRow(i);
                seat.setNumber(j);
                seat.setAvailability(true);
                seat.setEvent(event);
                seat.setSection(section);
                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
        
        return "redirect:" + "/events/" + id + "/sections/all";
    }
}
