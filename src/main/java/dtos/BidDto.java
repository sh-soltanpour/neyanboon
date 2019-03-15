package dtos;

public class BidDto {
    private UserDto biddingUser;
    private ProjectDto project;
    private int bidAmount;

    public BidDto() {
    }

    public UserDto getBiddingUser() {
        return biddingUser;
    }

    public void setBiddingUser(UserDto biddingUser) {
        this.biddingUser = biddingUser;
    }

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }
}
