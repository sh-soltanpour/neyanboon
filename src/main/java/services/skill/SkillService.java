package services.skill;

import entitites.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getSkills();

    void initialize();
}
