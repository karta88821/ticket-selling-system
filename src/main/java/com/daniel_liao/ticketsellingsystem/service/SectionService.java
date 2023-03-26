package com.daniel_liao.ticketsellingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.daniel_liao.ticketsellingsystem.entity.Section;
import com.daniel_liao.ticketsellingsystem.repository.EventRepository;
import com.daniel_liao.ticketsellingsystem.repository.SectionRepository;

@Service
public class SectionService {

    // SECTIONS: [Section]
    private final String SECTIONS = "SECTIONS";
    
    @Autowired
    private SectionRepository sectionRepo;

    @Cacheable(SECTIONS)
    public List<Section> getAllSections() {
        System.out.println("Query all sections from the db");
        List<Section> sections = sectionRepo.findAll();
        return sections;
    }

    @CacheEvict(value = SECTIONS, allEntries = true) // allEntries = true means clear the cache data with the given key
    public void addNewSection(Section section) {
        sectionRepo.save(section);
    }
}
