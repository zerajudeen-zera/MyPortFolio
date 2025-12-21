package com.portfolio.app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.app.model.Project;
import com.portfolio.app.model.Skill;
import com.portfolio.app.repository.ProjectRepository;
import com.portfolio.app.repository.SkillRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadSkills(SkillRepository skillRepository) {
        return args -> {
            skillRepository.save(new Skill("Jenkins", "Intermediate", "Jenkins.png"));
            skillRepository.save(new Skill("ArgoCD", "Intermediate", "ArgoCD.png"));
            skillRepository.save(new Skill("AWS", "Intermediate", "AWS.png"));
            skillRepository.save(new Skill("Docker", "Intermediate", "Docker.png"));
            skillRepository.save(new Skill("Kubernetes", "Intermediate", "Kubernetes.png"));
            skillRepository.save(new Skill("Terraform", "Intermediate", "Terraform.png"));
            skillRepository.save(new Skill("Ansible", "Intermediate", "Ansible.png"));
            skillRepository.save(new Skill("Prometheus", "Intermediate", "Prometheus.png"));
            skillRepository.save(new Skill("Grafana", "Intermediate", "Grafana.png"));
            skillRepository.save(new Skill("Bash Scripting", "Intermediate", "Bash Scripting.png"));
            skillRepository.save(new Skill("GitHub Actions", "Intermediate", "GitHub Actions.png"));
            skillRepository.save(new Skill("SonarQube", "Intermediate", "SonarQube.png"));
        };
    }

    @Bean
    CommandLineRunner loadProjects(ProjectRepository projectRepository) {
        return args -> {

            projectRepository.save(
                new Project(
                    "Brain Task App",
                    "Automated build & deployment using AWS services and deployed to EKS cluster",
                    "CodeBuild, CodePipeline, AWS, EKS",
                    "https://github.com/zerajudeen-zera/Brain-Task-App",
                    "eks-portfolio.png"
                )
            );

            projectRepository.save(
                new Project(
                    "Jenkins CI/CD",
                    "Automated build and deployment to EC2 using Jenkins pipeline and Shell script",
                    "Jenkins, Docker, Shell",
                    "https://github.com/zerajudeen-zera/devops-react-app",
                    "Ecom-app.png"
                )
            );

            projectRepository.save(
                new Project(
                    "K8s (EKS) Deployment",
                    "Deployed a static application to EKS and managed infrastructure using Terraform",
                    "AWS, EKS, Kubernetes, Terraform, Jenkins",
                    "https://github.com/zerajudeen-zera/Trendsapp",
                    "Trends-app.png"
                )
            );
        };
    }
}
