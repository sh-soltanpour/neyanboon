package dtos;

import entitites.Skill;

public class SkillDto {
    private String name;
    private int point;


    public SkillDto() {
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
