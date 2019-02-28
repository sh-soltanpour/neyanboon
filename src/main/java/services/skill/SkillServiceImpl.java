package services.skill;

import dtos.SkillDto;
import entitites.Skill;
import factory.ObjectFactory;

import java.util.List;
import java.util.stream.Collectors;

public class SkillServiceImpl implements SkillService {
    private List<Skill> skills;

    public SkillServiceImpl() {
        initialize();
    }

    @Override
    public void initialize() {
        skills = ObjectFactory.getMetaDataClient().getSkills().stream().map(SkillDto::toSkill).collect(Collectors.toList());
    }

    @Override
    public List<SkillDto> getSkillsDto() {
        return skills.stream().map(SkillDto::of).collect(Collectors.toList());
    }

    @Override
    public List<Skill> getSkills() {
        return skills;
    }
}
