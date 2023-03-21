package com.daniel_liao.ticketsellingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daniel_liao.ticketsellingsystem.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
    List<Section> findByName(String name);

    @Query(
        // 找出那些沒有被這個event_id所選擇的sections
        value = "SELECT * FROM section WHERE id NOT IN (SELECT section_id FROM seat WHERE event_id = ?1 GROUP BY section_id)", 
        nativeQuery = true)

    List<Section> findSectionsEventNotSelect(Integer eventID);
}
