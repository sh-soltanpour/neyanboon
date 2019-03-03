package dtos;

import entitites.ProjectSkill;

public class ProjectSkillDto {


    private String name;
    private int point;

    public static ProjectSkillDto of(ProjectSkill projectSkill) {
        return new ProjectSkillDto(
                projectSkill.getName(),
                projectSkill.getPoint()
        );
    }

    public ProjectSkill toProjectSkill() {
        return new ProjectSkill(name, point);
    }

    public ProjectSkillDto() {
    }

    private ProjectSkillDto(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
