package com.daniel_liao.ticketsellingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daniel_liao.ticketsellingsystem.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(
        value = "SELECT * FROM ticket WHERE user_id = ?1", 
        nativeQuery = true)
    List<Ticket> findTicketsForSingleUser(Integer userID);
}
