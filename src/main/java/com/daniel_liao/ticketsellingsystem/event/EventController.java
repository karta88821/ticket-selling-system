package com.daniel_liao.ticketsellingsystem.event;

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
import com.daniel_liao.ticketsellingsystem.section.Section;
import com.daniel_liao.ticketsellingsystem.section.SectionRepository;
import com.daniel_liao.ticketsellingsystem.ticket.Seat;
import com.daniel_liao.ticketsellingsystem.ticket.SeatRepository;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private SectionRepository sectionRepo;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private EventService eventService;
    
    @GetMapping("/all")
    public String eventsListPage(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        model.addAttribute("activeLink", "Events");
        model.addAttribute("eventForm", new EventForm());
        return "events";
    }

    @PostMapping("/all")
    public String handleAddEvents(@Valid EventForm eventForm, BindingResult result, Model model) throws ParseException {
        if (result.hasErrors()) {
            List<String> errorStrings = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                errorStrings.add(error.getDefaultMessage());
            }
            model.addAttribute("form_error", errorStrings);
            model.addAttribute("events", eventService.getAllEvents()); 
            return "events";
        }

        eventService.addNewEvents(eventForm);

        return "redirect:/events/all";
    }

    @GetMapping("/{event_id}/sections/all")
    public String eventBindSectionPage(@PathVariable(value = "event_id") Integer id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);

        List<Section> sectionsNotSelect = sectionRepo.findSectionsEventNotSelect(id);
        Section selected_section = new Section();
        model.addAttribute("sectionsNotSelect", sectionsNotSelect);
        model.addAttribute("section", selected_section);

        List<ISectionStatus> sectionStatuses = seatRepository.findAvailableSeatsForAnEvent(id);
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
