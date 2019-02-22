package dtos;

import entitites.Project;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDto {
    private String id;
    private String title;
    private List<SkillDto> skills;
    private long budget;
    private Date deadline;
    private String imageUrl;
    private String description;


    public ProjectDto() {
    }

    public Project toProject() {
        return new Project(
                title,
                skills.stream().map(SkillDto::toSkill).collect(Collectors.toList()),
                (int) budget
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

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
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
