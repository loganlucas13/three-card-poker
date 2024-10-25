import java.util.ArrayList;

public class Player {

    // data members
    private ArrayList<Card> hand;
    private int anteBet;
    private int playBet;
    private int pairPlusBet;
    private int totalWinnings;


    // constructor
    public Player() {
        // TODO: implement
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
}
