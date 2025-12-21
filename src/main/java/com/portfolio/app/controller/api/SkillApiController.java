package com.portfolio.app.controller.api;
import com.portfolio.app.exception.ResourceNotFoundException;
import com.portfolio.app.model.Skill;
import com.portfolio.app.repository.SkillRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
public class SkillApiController {

    private final SkillRepository skillRepository;

    public SkillApiController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public Iterable<Skill> getSkills() {
        return skillRepository.findAll();
    }

    @GetMapping("/{id}")
    public Skill getSkillById(@PathVariable long id) {
        return skillRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Skill not found with id: " + id));
    }
    @SuppressWarnings("null")
    @PostMapping
    public Skill createSkill(@Valid @RequestBody Skill skill) {
        return skillRepository.save(skill);
    }
}
