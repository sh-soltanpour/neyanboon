package services.skill;

import dtos.ProjectSkillDto;
import entitites.ProjectSkill;
import factory.ObjectFactory;
import repositories.skill.SkillRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SkillServiceImpl implements SkillService {
    private SkillRepository skillsRepository = ObjectFactory.getSkillRepository();

    public SkillServiceImpl() {
        initialize();
    }

    @Override
    public void initialize() {
        ObjectFactory.getMetaDataClient().getSkills().forEach(skill -> {
            try {
                skillsRepository.save(skill);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public List<ProjectSkillDto> getSkillsDto() {
//        return skills.stream().map(ProjectSkillDto::of).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<ProjectSkill> getSkills() {
        return null;
    }
}
