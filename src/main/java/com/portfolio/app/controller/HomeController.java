package com.portfolio.app.controller;

import com.portfolio.app.repository.ProjectRepository;
import com.portfolio.app.repository.SkillRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;

    public HomeController(
            SkillRepository skillRepository,
            ProjectRepository projectRepository) {
        this.skillRepository = skillRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("projects", projectRepository.findAll());
        return "index";
    }
}
