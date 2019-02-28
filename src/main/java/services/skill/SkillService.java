package services.skill;

import dtos.SkillDto;
import entitites.Skill;

import java.util.List;

public interface SkillService {
    List<SkillDto> getSkillsDto();
    List<Skill> getSkills();
    void initialize();
}
