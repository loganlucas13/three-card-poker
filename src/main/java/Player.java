import java.util.ArrayList;

public class Player {

    // data members
    private ArrayList<Card> hand;
    private int anteBet;
    private int playBet;
    private int pairPlusBet;
    private int totalWinnings;

    private int balance;

    private boolean hasConfirmed;
    private boolean hasFolded;


    // constructor
    public Player() {
        // TODO: implement
        this.setHand(null);

        // set bets to 0
        this.setAnteBet(0);
        this.setPlayBet(0);
        this.setPairPlusBet(0);

        // set winnings and balance to default values
        // winnings: 0
        // balance: 25
        this.setTotalWinnings(0);
        this.setBalance(25);

        // set game control booleans
        this.setHasConfirmed(false);
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

    // balance
    public int getBalance() {
        return this.balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
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
