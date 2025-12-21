package com.portfolio.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is required")
    @Size(max = 50, message = "Skill name must be at most 50 characters")
    private String name;

    @NotBlank(message = "Skill level is required")
    private String level;

    @Column(nullable = false)
    private String icon;  

    public Skill() {}

    public Skill(String name, String level, String icon) {
        this.name = name;
        this.level = level;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
    public void setIcon(String icon) {
        this.level = icon;
    }
    
}
