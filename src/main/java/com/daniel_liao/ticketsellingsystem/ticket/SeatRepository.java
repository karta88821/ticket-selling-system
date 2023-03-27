package com.daniel_liao.ticketsellingsystem.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.daniel_liao.ticketsellingsystem.ISectionStatus;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Query(
        value = "SELECT * FROM seat WHERE event_id = ?1 GROUP BY section_id", 
        nativeQuery = true)
    List<Seat> findSeatsInDiffSectionsForAnEvent(Integer eventID);

    @Query(
        value = "SELECT section.id AS sectionID, section.name AS sectionName, section.total_row AS totalRow, section.total_number_per_row AS totalNumberPerRow, section.price, COUNT(*) AS avSeats " + 
                "FROM section, " + 
                "(SELECT section_id AS sectionID FROM seat WHERE seat.event_id = ?1 AND availability = 1) AS av_seats " +
                "WHERE section.id = av_seats.sectionID " + 
                "GROUP BY section.id;", 
        nativeQuery = true)
    List<ISectionStatus> findAvailableSeatsForAnEvent(Integer eventID);

    @Query(
        value = "SELECT * FROM seat WHERE event_id = ?1 AND section_id = ?2 AND availability = 1 LIMIT ?3", 
        nativeQuery = true)
    List<Seat> findAvailablityWithNumber(Integer eventID, Integer sectionID, Integer number);

    @Transactional
    @Modifying
    @Query(
        value = "UPDATE seat SET availability = 0 WHERE event_id = ?1 AND section_id = ?2 AND seat_row = ?3 AND seat_number = ?4", 
        nativeQuery = true)
    void updateAvailablityWithNumber(Integer eventID, Integer sectionID, Integer row, Integer number);
}
