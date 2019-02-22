package dtos;

public class BidInfoDto {
    private String biddingUser;
    private String projectTitle;
    private int bidAmount;

    public BidInfoDto() {
    }

    public String getBiddingUser() {
        return biddingUser;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public int getBidAmount() {
        return bidAmount;
    }
}
