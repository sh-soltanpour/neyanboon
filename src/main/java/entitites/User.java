package entitites;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private List<Skill> skills = new ArrayList<>();

    public User(String username, List<Skill> skills) {
        this.username = username;
        this.skills = skills;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public int getSkillPoints(String skillName) {
        Skill foundSkill = skills.stream()
                .filter(skill -> skill.getName().equals(skillName))
                .findFirst()
                .orElse(null);
        if (foundSkill == null)
            return 0;
        return foundSkill.getPoints();
    }
}
