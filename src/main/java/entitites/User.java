package entitites;

import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import exceptions.PreConditionFailedException;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String jobTitle;
    private String profilePictureUrl;
    private String bio;
    private List<UserSkill> skills = new ArrayList<>();


    public User(String id, String firstName, String lastName, String password, String jobTitle, String profilePictureUrl, List<UserSkill> skills, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.jobTitle = jobTitle;
        this.profilePictureUrl = profilePictureUrl;
        this.bio = bio;
        if (skills != null)
            this.skills = skills;
    }

    public User(String id) {
        this.id = id;
    }

    public List<UserSkill> getSkills() {
        return skills;
    }


    private boolean hasSkill(String skillName) {
        return skills
                .stream()
                .anyMatch(skill -> skill.getName().equals(skillName));
    }

    public void addSkill(String skillName) throws AlreadyExistsException {
        if (hasSkill(skillName))
            throw new AlreadyExistsException("You already have this skill");

        skills.add(new UserSkill(skillName));
    }

    public void setSkills(List<UserSkill> skills) {
        this.skills = skills;
    }

    public int getSkillPoints(String skillName) {
        UserSkill foundSkill = skills.stream()
                .filter(skill -> skill.getName().equals(skillName))
                .findFirst()
                .orElse(null);
        if (foundSkill == null)
            return 0;
        return foundSkill.getPoints();
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

    public void deleteSkill(String skillName) throws NotFoundException {
        if (!hasSkill(skillName))
            throw new NotFoundException("Skill Not Found");
        skills.removeIf(skill -> skill.getName().equals(skillName));
    }

    public void endorse(User endorser, String skillName)
            throws NotFoundException, PreConditionFailedException, AlreadyExistsException {
        if (id.equals(endorser.getId()))
            throw new PreConditionFailedException("You Cannot Endorse Yourself");
        skills
                .stream()
                .filter(skill -> skill.getName().equals(skillName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Skill Not Found"))
                .endorse(endorser);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
