package com.daniel_liao.ticketsellingsystem.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.daniel_liao.ticketsellingsystem.ISectionStatus;
import com.daniel_liao.ticketsellingsystem.TicketCheckout;
import com.daniel_liao.ticketsellingsystem.entity.Event;
import com.daniel_liao.ticketsellingsystem.entity.Seat;
import com.daniel_liao.ticketsellingsystem.entity.Ticket;
import com.daniel_liao.ticketsellingsystem.entity.User;
import com.daniel_liao.ticketsellingsystem.form.SelectSectionForm;
import com.daniel_liao.ticketsellingsystem.repository.EventRepository;
import com.daniel_liao.ticketsellingsystem.repository.SeatRepository;
import com.daniel_liao.ticketsellingsystem.repository.TicketRepository;
import com.daniel_liao.ticketsellingsystem.repository.UserRepository;
import com.daniel_liao.ticketsellingsystem.section.Section;


@Controller
@RequestMapping("/tickets")
public class TicketController {

    private static final String TICKET_CHECKOUT = "ticketCheckout";

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private SeatRepository seatRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TicketRepository ticketRepo;

    @GetMapping("/all")
    public String ticketListPage(Model model, Principal principle) {
        User user = userRepo.findByAccount(principle.getName());
        List<Ticket> tickets = ticketRepo.findTicketsForSingleUser(user.getId());
        model.addAttribute("tickets", tickets);
        return "tickets";
    }

    @GetMapping("/events")
    public String selectEventPage(Model model) {
        model.addAttribute("activeLink", "Get a ticket");
        List<Event> events = eventRepo.findAll();
        model.addAttribute("events", events);
        Event event = new Event();
        model.addAttribute("event", event);
        return "select-event";
    }

    @PostMapping("/events")
    public String selectEvent(Event event) {
        System.out.println(event.getId());
        return "redirect:" + "/tickets/events/" + event.getId();
    }

    @GetMapping("/events/{event_id}")
    public String selectSectionAndSeatPage(@PathVariable(value = "event_id") Integer eventID, Model model) {
        List<ISectionStatus> sectionStatuses = seatRepo.findAvailableSeatsForAnEvent(eventID);
        model.addAttribute("sectionStatuses", sectionStatuses);
        SelectSectionForm selectSectionForm = new SelectSectionForm();
        model.addAttribute("selectSectionForm", selectSectionForm);
        return "select-section-and-seat";
    }

    @PostMapping("/events/{event_id}")
    public String selectSectionAndSeat(@PathVariable(value = "event_id") Integer eventID, SelectSectionForm selectSectionForm, HttpSession session) {
        // check whether the number is valid or not
        List<Seat> availableSeats = seatRepo.findAvailablityWithNumber(eventID, selectSectionForm.getSectionID(), selectSectionForm.getNumberOfseats());
        TicketCheckout ticketCheckout = new TicketCheckout();
        ticketCheckout.setEventID(eventID);
        ticketCheckout.setSectionID(selectSectionForm.getSectionID());
        ticketCheckout.setSelectedSeats(availableSeats);
        session.setAttribute(TICKET_CHECKOUT, ticketCheckout);

        System.out.println("post events/event_id session id: " + session.getId());

        return "redirect:/tickets/checkout";
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model, HttpSession session) {

        System.out.println("checkout session id: " + session.getId());

        if (session.getAttribute(TICKET_CHECKOUT) != null) {
            TicketCheckout ticketCheckout = (TicketCheckout) session.getAttribute(TICKET_CHECKOUT);

            Event event = eventRepo.findById(ticketCheckout.getEventID()).get();
            model.addAttribute("event", event);
            model.addAttribute("seats", ticketCheckout.getSelectedSeats());
    
            Integer totalPrice = 0;
            for (Seat seat: ticketCheckout.getSelectedSeats()) {
                totalPrice += seat.getSection().getPrice();
            }
            model.addAttribute("totalPrice", totalPrice);
        }
        
        return "checkout";
    }

    @PostMapping("/checkout")
    public String doCheckout(Model model, Principal principle, HttpSession session) {
        
        TicketCheckout ticketCheckout = (TicketCheckout) session.getAttribute(TICKET_CHECKOUT);
        
        Integer eventID = ticketCheckout.getEventID();
        Integer sectionID = ticketCheckout.getSectionID();

        // get user entity by username
        String account = principle.getName();
        User user = userRepo.findByAccount(account);

        Event event = new Event();
        event.setId(eventID);

        Section section = new Section();
        section.setId(sectionID);

        // set availability to 0 for each selected seat
        // create tickets for each seat (assign to the user)
        for (Seat seat: ticketCheckout.getSelectedSeats()) {
            seatRepo.updateAvailablityWithNumber(eventID, sectionID, seat.getRow(), seat.getNumber());
            
            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setSection(section);
            ticket.setSeat(seat);
            ticket.setUser(user);
            ticketRepo.save(ticket);
        }

        // clear session for ticketCheckout
        session.removeAttribute(TICKET_CHECKOUT);

        return "redirect:/tickets/finish";
    }

    @GetMapping("/finish")
    public String finishPage(Model model) {
        return "create-tickets-success";
    }
}
