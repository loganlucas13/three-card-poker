import java.util.ArrayList;

public class Dealer {

    // data members
    // required
    private Deck theDeck;
    private ArrayList<Card> dealersHand;

    // optional
    private boolean hasFlipped;


    // constructor
    public Dealer() {
        this.theDeck = new Deck();
    }

    // returns an ArrayList of 3 cards removed from this.theDeck
    // checks if there are more than 34 cards left in this.theDeck
    // if not, deck is reshuffled
    public ArrayList<Card> dealHand() {
    	if(getDeck().size() <= 34) {
    		this.getDeck().newDeck(); // reshuffle with new deck if amount of cards <= 34
    	}

        // create new hand to return
        ArrayList<Card> newHand = new ArrayList<Card>(3);

        // adds three cards to the hand
    	// removes top card 3 times
    	newHand.add(this.theDeck.remove(0));
    	newHand.add(this.theDeck.remove(0));
    	newHand.add(this.theDeck.remove(0));

        return newHand;
    }


    // getters and setters

    // theDeck
    public Deck getDeck() {
        return this.theDeck;
    }
    public void setDeck(Deck deck) {
        this.theDeck = deck;
    }

    // dealersHand
    public ArrayList<Card> getDealersHand() {
        return this.dealersHand;
    }
    public void setDealersHand(ArrayList<Card> dealersHand) {
        this.dealersHand = dealersHand;
    }

    // hasFlipped
    public boolean getHasFlipped() {
        return this.hasFlipped;
    }
    public void setHasFlipped(boolean hasFlipped) {
        this.hasFlipped = hasFlipped;
    }
}
