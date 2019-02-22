package dtos;

import entitites.Project;
import entitites.Skill;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDto {
    private String id;
    private String title;
    private List<SkillDto> skills;
    private int budget;
    private Date deadline;
    private String imageUrl;
    private String description;


    public ProjectDto() {
    }

    public static ProjectDto of(Project project) {
        if (project == null)
            return null;
        return new ProjectDto(
                project.getId(),
                project.getTitle(),
                project.getSkills().stream().map(SkillDto::of).collect(Collectors.toList()),
                project.getBudget(),
                project.getDeadline(),
                project.getImageUrl(),
                project.getDescription()
        );
    }

    public ProjectDto(String id, String title, List<SkillDto> skills, int budget, Date deadline, String imageUrl, String description) {
        this.id = id;
        this.title = title;
        this.skills = skills;
        this.budget = budget;
        this.deadline = deadline;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Project toProject() {
        return new Project(
                id,
                title,
                description,
                imageUrl,
                skills.stream().map(skillDto -> skillDto.toSkill()).collect(Collectors.toList()),
                Collections.emptyList(),
                budget,
                deadline,
                null
        );
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
