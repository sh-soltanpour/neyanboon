package services.skill;

import entitites.Skill;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import repositories.skill.SkillRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

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
    public List<Skill> getSkills() throws SQLException {
        return skillsRepository.findAll();

    }

    @Override
    public Skill getSkill(String id) throws NotFoundException, SQLException {
        return skillsRepository.findById(id);

    }
}
