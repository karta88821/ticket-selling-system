package com.daniel_liao.ticketsellingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import com.daniel_liao.ticketsellingsystem.entity.Section;
import com.daniel_liao.ticketsellingsystem.repository.SectionRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SectionRepositoryTest {

    @Autowired
    private SectionRepository repo;

    @Test
    public void testInsertSection() {
        Section section = new Section();
        section.setName("B區");
        section.setTotalRow(2);
        section.setTotalNumberPerRow(2);
        section.setPrice(400);

        Section savedSection = repo.save(section);
        
        assertEquals(section.getTotalRow(), savedSection.getTotalRow());
    }
}
