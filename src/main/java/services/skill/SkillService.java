package services.skill;

import dtos.ProjectSkillDto;
import entitites.ProjectSkill;

import java.util.List;

public interface SkillService {
    List<ProjectSkillDto> getSkillsDto();
    List<ProjectSkill> getSkills();
    void initialize();
}
