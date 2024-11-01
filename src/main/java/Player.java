import java.util.ArrayList;

public class Player {

    // data members
    // required
    private ArrayList<Card> hand;
    private int anteBet;
    private int playBet;
    private int pairPlusBet;
    private int totalWinnings;

    // optional
    private boolean hasConfirmed;
    private boolean hasFolded;


    // constructor
    public Player() {
        // TODO: implement
        this.setHand(null);
        this.setAnteBet(0);
        this.setPlayBet(0);
        this.setPairPlusBet(0);
        this.setTotalWinnings(0);
        this.setHasFolded(false);
    }


    // getters and setters

    // hand
    public ArrayList<Card> getHand() {
        return this.hand;
    }
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    // anteBet
    public int getAnteBet() {
        return this.anteBet;
    }
    public void setAnteBet(int anteBet) {
        this.anteBet = anteBet;
    }

    // playBet
    public int getPlayBet() {
        return this.playBet;
    }
    public void setPlayBet(int playBet) {
        this.playBet = playBet;
    }

    // pairPlusBet
    public int getPairPlusBet() {
        return this.pairPlusBet;
    }
    public void setPairPlusBet(int pairPlusBet) {
        this.pairPlusBet = pairPlusBet;
    }

    // totalWinnings
    public int getTotalWinnings() {
        return this.totalWinnings;
    }
    public void setTotalWinnings(int totalWinnings) {
        this.totalWinnings = totalWinnings;
    }

    // hasConfirmed
    public boolean getHasConfirmed() {
        return this.hasConfirmed;
    }
    public void setHasConfirmed(boolean hasConfirmed) {
        this.hasConfirmed = hasConfirmed;
    }

    // hasFolded
    public boolean getHasFolded() {
        return this.hasFolded;
    }
    public void setHasFolded(boolean hasFolded) {
        this.hasFolded = hasFolded;
    }
}
