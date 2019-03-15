package servlets.models;

import dtos.ProjectSkillDto;
import dtos.UserDto;

public class EndorseRequest {
    private UserDto endorsedUser;
    private ProjectSkillDto skill;

    public EndorseRequest() {
    }

    public UserDto getEndorsedUser() {
        return endorsedUser;
    }

    public void setEndorsedUser(UserDto endorsedUser) {
        this.endorsedUser = endorsedUser;
    }

    public ProjectSkillDto getSkill() {
        return skill;
    }

    public void setSkill(ProjectSkillDto skill) {
        this.skill = skill;
    }
}
