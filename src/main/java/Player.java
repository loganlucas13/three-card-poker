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
    private boolean keepAnte;

    // constructor
    public Player() {
        this.setHand(null);
        this.setAnteBet(5); // default start is $5
        this.setPlayBet(0);
        this.setPairPlusBet(0);
        this.setTotalWinnings(0);
        this.setHasFolded(false);
        this.setKeepAnte(false);
    }


    // returns the hand type as a string
    // (for printing out popup text)
    public String handToString() {
        switch (ThreeCardLogic.evalHand(this.getHand())) {
            case 0:
                return "HIGH CARD";
            case 1:
                return "STRAIGHT FLUSH";
            case 2:
                return "THREE OF A KIND";
            case 3:
                return "STRAIGHT";
            case 4:
                return "FLUSH";
            case 5:
                return "PAIR";
            default:
                return "INVALID HAND TYPE";
        }
    }


    // returns the result of the player's wagers as a string
    // checks against the dealer's hand using ThreeCardLogic.CompareHands
    public String resultToString(Dealer dealer) {
        String result = "";
        // if the player has folded, results are not printed
        if (this.hasFolded) {
            result += "PLAYER HAS FOLDED\n\nALL BETS LOST";
            return result;
        }

        result += "ANTE BET ";
        switch (ThreeCardLogic.CompareHands(dealer.getDealersHand(), this.getHand())) {
            case 0:
                result += "TIE";
                break;
            case 1:
                result += "LOSS";
                break;
            case 2:
                result += "WIN";
                break;
        }

        if (this.getPairPlusBet() == 0) {
            result += "\n\nDID NOT PLACE PAIR PLUS BET";
            return result;
        }

        result += "\n\nPAIR PLUS RESULT: ";


        int pairPlusWinnings = ThreeCardLogic.evalPPWinnings(this.getHand(), this.getPairPlusBet());
        if (this.getPairPlusBet() == pairPlusWinnings) {
            result += "1 TO 1";
        }
        else if (this.getPairPlusBet() * 3 == pairPlusWinnings) {
            result += "3 TO 1";
        }
        else if (this.getPairPlusBet() * 6 == pairPlusWinnings) {
            result += "6 TO 1";
        }
        else if (this.getPairPlusBet() * 30 == pairPlusWinnings) {
            result += "30 TO 1";
        }
        else if (this.getPairPlusBet() * 40 == pairPlusWinnings) {
            result += "40 TO 1";
        }
        else {
            result += "LOSS";
        }
        return result;
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

    // keepAnte
    public boolean getKeepAnte() {
    	return this.keepAnte;
    }
    public void setKeepAnte(boolean keepAnte) {
    	this.keepAnte = keepAnte;
    }
}
