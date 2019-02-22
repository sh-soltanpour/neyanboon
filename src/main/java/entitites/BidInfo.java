package entitites;

public class BidInfo {
    private User user;
    private int amount;

    public BidInfo(User user, int amount) {
        this.user = user;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public int getAmount() {
        return amount;
    }
}
