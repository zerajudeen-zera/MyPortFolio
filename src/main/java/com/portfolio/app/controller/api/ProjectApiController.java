package com.portfolio.app.controller.api;

import com.portfolio.app.exception.ResourceNotFoundException;
import com.portfolio.app.model.Project;
import com.portfolio.app.repository.ProjectRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectApiController {

    private final ProjectRepository projectRepository;

    public ProjectApiController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable long id) {
        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + id));
    }
    @SuppressWarnings("null")
    @PostMapping
    public Project createProject(@Valid @RequestBody Project project) {
        return projectRepository.save(project);
    }
}
