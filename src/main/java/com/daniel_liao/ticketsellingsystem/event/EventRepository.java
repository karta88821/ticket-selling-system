package com.daniel_liao.ticketsellingsystem.event;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByNameAndDate(String name, Date date);
}
