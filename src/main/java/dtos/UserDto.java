package dtos;

import entitites.Skill;
import entitites.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String profilePictureUrl;
    private String bio;
    private List<SkillDto> skills = new ArrayList<>();

    public UserDto() {
    }

    private UserDto(String id, String firstName, String lastName, String jobTitle, String profilePictureUrl, String bio, List<SkillDto> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.profilePictureUrl = profilePictureUrl;
        this.bio = bio;
        this.skills = skills;
    }

    public static UserDto of(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getJobTitle(),
                user.getProfilePictureUrl(),
                user.getBio(),
                user.getSkills().stream().map(SkillDto::of).collect(Collectors.toList())
        );
    }

    public User toUser() {
        return new User(
                id,
                firstName,
                lastName,
                jobTitle,
                profilePictureUrl,
                skills.stream().map(SkillDto::toSkill).collect(Collectors.toList()),
                bio);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }
}
