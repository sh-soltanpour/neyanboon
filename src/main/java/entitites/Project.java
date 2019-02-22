package entitites;

import java.util.List;

public class Project {
    private String title;
    private List<Skill> skills;
    private int budget;

    public Project(String title, List<Skill> skills, int budget) {
        this.title = title;
        this.skills = skills;
        this.budget = budget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
