package com.portfolio.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project title is required")
    @Size(max = 100)
    private String title;

    @Column(length = 1000)
    @NotBlank(message = "Project description is required")
    @Size(max = 1000)
    private String description;

    @NotBlank(message = "Tech stack is required")
    private String techStack;

    @NotBlank(message = "GitHub URL is required")
    private String githubUrl;

    @NotBlank(message = "Project image is required")
    @Column(nullable = false)
    private String image;

    public Project() {}

    // ✅ UPDATED CONSTRUCTOR
    public Project(String title,
                   String description,
                   String techStack,
                   String githubUrl,
                   String image) {
        this.title = title;
        this.description = description;
        this.techStack = techStack;
        this.githubUrl = githubUrl;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTechStack() {
        return techStack;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    // ✅ REQUIRED for Thymeleaf
    public String getImage() {
        return image;
    }

    // Optional setter (useful for APIs)
    public void setImage(String image) {
        this.image = image;
    }
}
