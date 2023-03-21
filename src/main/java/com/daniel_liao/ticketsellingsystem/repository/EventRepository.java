package com.daniel_liao.ticketsellingsystem.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel_liao.ticketsellingsystem.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByNameAndDate(String name, Date date);
}
