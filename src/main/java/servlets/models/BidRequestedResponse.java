package servlets.models;

public class BidRequestedResponse {
    public BidRequestedResponse(boolean bidRequested) {
        this.bidRequested = bidRequested;
    }

    private boolean bidRequested;

    public boolean isBidRequested() {
        return bidRequested;
    }

    public void setBidRequested(boolean bidRequested) {
        this.bidRequested = bidRequested;
    }
}
