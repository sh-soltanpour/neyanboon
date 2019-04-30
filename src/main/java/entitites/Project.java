package entitites;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Project {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private List<ProjectSkill> skills = Collections.emptyList();
    private List<Bid> bids;
    private int budget;
    private Date deadline;
    private User winner;

    public Project() {
    }

    public Project(String id) {
        this.id = id;
    }

    public Project(String id, String title, String description, String imageUrl, List<ProjectSkill> skills, List<Bid> bids, int budget, Date deadline, User winner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.skills = skills;
        this.bids = bids;
        this.budget = budget;
        this.deadline = deadline;
        this.winner = winner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ProjectSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<ProjectSkill> skills) {
        this.skills = skills;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }
}
