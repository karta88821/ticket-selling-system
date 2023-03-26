package com.daniel_liao.ticketsellingsystem.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(
        value = "SELECT * FROM ticket WHERE user_id = ?1", 
        nativeQuery = true)
    List<Ticket> findTicketsForSingleUser(Integer userID);
}
