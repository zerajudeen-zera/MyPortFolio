package com.portfolio.app.controller;

import com.portfolio.app.controller.api.SkillApiController;
import com.portfolio.app.model.Skill;
import com.portfolio.app.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SkillApiController.class)
class SkillApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillRepository skillRepository;

    @Test
    void shouldReturnSkillById() throws Exception {
        Skill skill = new Skill("AWS", "Intermediate", "aws.png");
        skill.setId(1L);

        when(skillRepository.findById(1L))
                .thenReturn(Optional.of(skill));

        mockMvc.perform(get("/api/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("AWS"))
                .andExpect(jsonPath("$.level").value("Intermediate"));
    }

    @Test
    void shouldReturn404WhenSkillNotFound() throws Exception {
        when(skillRepository.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/skills/99"))
                .andExpect(status().isNotFound());
    }
}
