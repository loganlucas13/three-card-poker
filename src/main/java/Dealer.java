import java.util.ArrayList;

public class Dealer {

    // data member(s)
    private Deck theDeck;
    private ArrayList<Card> dealersHand;


    // constructor
    public Dealer() {
        this.theDeck = new Deck();
    }


    // returns an ArrayList of 3 cards removed from this.theDeck
    // checks if there are more than 34 cards left in this.theDeck
    // if not, deck is reshuffled
    public ArrayList<Card> dealHand() {
        // TODO: implement
        return null;
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
}