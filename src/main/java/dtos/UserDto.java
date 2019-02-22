package dtos;

import entitites.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private String username;
    private List<SkillDto> skills = new ArrayList<>();

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    public User toUser() {
        return new User(
                username,
                skills.stream().map(SkillDto::toSkill).collect(Collectors.toList())
        );
    }
}
