package com.daniel_liao.ticketsellingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel_liao.ticketsellingsystem.entity.Section;
import com.daniel_liao.ticketsellingsystem.repository.EventRepository;
import com.daniel_liao.ticketsellingsystem.repository.SectionRepository;
import com.daniel_liao.ticketsellingsystem.service.SectionService;

@RestController
@RequestMapping("/api")
public class SectionRestController {

    @Autowired
    private SectionService sectionService;
    
    @GetMapping("/sections")

    private List<Section> getEvents() {
        return sectionService.getAllSections();
    }
}
