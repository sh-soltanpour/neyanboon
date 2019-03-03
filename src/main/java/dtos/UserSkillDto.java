package dtos;


import entitites.UserSkill;

public class UserSkillDto {
    private String name;
    private int point = 0;


    public UserSkillDto() {
    }

    public UserSkillDto(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public static UserSkillDto of(UserSkill skill) {
        return new UserSkillDto(skill.getName(), skill.getPoints());
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

    public UserSkill toUserSkill() {
        return new UserSkill(name);
    }


}
