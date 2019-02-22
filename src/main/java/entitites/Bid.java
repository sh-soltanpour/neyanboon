package entitites;

public class Bid {
    private User biddingUser;
    private Project project;
    private int bidAmount;

    public Bid(User biddingUser, Project project, int bidAmount) {
        this.biddingUser = biddingUser;
        this.project = project;
        this.bidAmount = bidAmount;
    }

    public User getBiddingUser() {
        return biddingUser;
    }

    public void setBiddingUser(User biddingUser) {
        this.biddingUser = biddingUser;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }
}
