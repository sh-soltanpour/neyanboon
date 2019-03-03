package services.skill;

import dtos.ProjectSkillDto;
import entitites.ProjectSkill;
import factory.ObjectFactory;

import java.util.List;
import java.util.stream.Collectors;

public class SkillServiceImpl implements SkillService {
    private List<ProjectSkill> skills;

    public SkillServiceImpl() {
        initialize();
    }

    @Override
    public void initialize() {
        skills = ObjectFactory.getMetaDataClient().getSkills()
                .stream().map(ProjectSkillDto::toProjectSkill).collect(Collectors.toList());
    }

    @Override
    public List<ProjectSkillDto> getSkillsDto() {
        return skills.stream().map(ProjectSkillDto::of).collect(Collectors.toList());
    }

    @Override
    public List<ProjectSkill> getSkills() {
        return skills;
    }
}
