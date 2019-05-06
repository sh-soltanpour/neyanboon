package services.skill;

import entitites.Skill;
import exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface SkillService {
    List<Skill> getSkills() throws SQLException;
    Skill getSkill(String id) throws NotFoundException, SQLException;

    void initialize();
}
