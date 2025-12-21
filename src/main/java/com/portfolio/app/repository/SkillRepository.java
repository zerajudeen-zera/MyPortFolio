package com.portfolio.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portfolio.app.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
