package dtos;

import entitites.User;
import entitites.UserSkill;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String jobTitle;
    private String profilePictureUrl;
    private String bio;
    private List<UserSkillDto> skills = new ArrayList<>();

    public UserDto() {
    }

    private UserDto(String id, String firstName, String lastName, String password, String jobTitle, String profilePictureUrl, String bio, List<UserSkillDto> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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
                user.getPassword(),
                user.getJobTitle(),
                user.getProfilePictureUrl(),
                user.getBio(),
                user.getSkills().stream().map(UserSkillDto::of).collect(Collectors.toList())
        );
    }

    public User toUser() {
        return new User(
                id,
                firstName,
                lastName,
                password,
                jobTitle,
                profilePictureUrl,
                skills.stream().map(UserSkillDto::toUserSkill).collect(Collectors.toList()),
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

    public List<UserSkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<UserSkillDto> skills) {
        this.skills = skills;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
