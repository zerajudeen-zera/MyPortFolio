package com.portfolio.app.repository;

import com.portfolio.app.model.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SkillRepositoryTest {

    @Autowired
    private SkillRepository skillRepository;

    @Test
    void shouldSaveAndFetchSkill() {
        Skill skill = new Skill("Docker", "Intermediate", "docker.png");

        Skill saved = skillRepository.save(skill);
        
        @SuppressWarnings("null")
        Optional<Skill> found = skillRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Docker", found.get().getName());
        assertEquals("Intermediate", found.get().getLevel());
    }
}
