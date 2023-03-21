package com.daniel_liao.ticketsellingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daniel_liao.ticketsellingsystem.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query(
        value = "SELECT * FROM seat WHERE event_id = ?1 GROUP BY section_id", 
        nativeQuery = true)
    List<Seat> findSeatsInDiffSectionsForAnEvent(Integer eventID);

}
