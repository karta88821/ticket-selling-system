package com.daniel_liao.ticketsellingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.daniel_liao.ticketsellingsystem.entity.Event;
import com.daniel_liao.ticketsellingsystem.entity.Seat;
import com.daniel_liao.ticketsellingsystem.entity.Section;
import com.daniel_liao.ticketsellingsystem.entity.User;
import com.daniel_liao.ticketsellingsystem.repository.EventRepository;
import com.daniel_liao.ticketsellingsystem.repository.SeatRepository;
import com.daniel_liao.ticketsellingsystem.repository.SectionRepository;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SeatRepositoryTest {
    
    @Autowired
    private SeatRepository seatRepo;

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private SectionRepository sectionRepo;

    @Test
    public void testInsertSingleSeat() {
        // 演場會test 2023-03-19 A區 1-1 true
        Seat seat = new Seat();
        seat.setRow(1);
        seat.setNumber(1);
        seat.setAvailability(true);

        // set event
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 19);
        List<Event> events = eventRepo.findByNameAndDate("演唱會test", cal.getTime());

        if (!events.isEmpty()) {
            seat.setEvent(events.get(0));
        }

        // set section
        List<Section> sections = sectionRepo.findByName("A區");

        if (!sections.isEmpty()) {
            seat.setSection(sections.get(0));
        }

        // save to DB
        Seat savedSeat = seatRepo.save(seat);
        
        assertTrue(savedSeat.getEvent().getName().equals(events.get(0).getName()));
        assertTrue(savedSeat.getSection().getName().equals(sections.get(0).getName()));
        assertEquals(savedSeat, seat);
    }

    @Test
    public void testInsertAllSeatsOfAnEvent() {
        // 新增event時，要讓admin選擇要加入那些section，
        // 然後將這些section內的所有seat加入到DB(availability都設為false)

        // insert all seat of 演唱會test2 2023-03-19
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 19);
        Event event = eventRepo.findByNameAndDate("演唱會test", cal.getTime()).get(0);

        List<Seat> seats = new ArrayList<>();

        // B區
        Section Bsection = sectionRepo.findByName("B區").get(0);
        for (int i=1; i<=Bsection.getTotalRow(); i++) {
            for (int j=1; j<=Bsection.getTotalNumberPerRow(); j++) {
                Seat seat = new Seat();
                seat.setRow(i);
                seat.setNumber(j);
                seat.setAvailability(false);
                seat.setEvent(event);
                seat.setSection(Bsection);
                seats.add(seat);
            }
        }

        List<Seat> savedSeats = seatRepo.saveAll(seats);
        assertEquals(savedSeats.size(), seats.size());
    }

    @Test
    public void updateSeatAvailablity() {
        Event event = new Event();
        event.setId(3);

        Section section = new Section();
        section.setId(1);

        User user = new User();
        user.setId(7);

        seatRepo.updateAvailablityWithNumber(3, 1, 1, 1);
    }
}
