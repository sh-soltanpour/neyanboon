package dtos;

import entitites.Skill;

public class SkillDto {
    private String name;
    private int point = 0;


    public SkillDto() {
    }

    public SkillDto(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public static SkillDto of(Skill skill) {
        return new SkillDto(skill.getName(), skill.getPoints());
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

    public Skill toSkill() {
        return new Skill(name, point);
    }


}
