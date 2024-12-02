import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card> {

    // constructor
    // creates a new deck of 52 cards sorted in random order
    public Deck() {
        char[] suits = {'C', 'D', 'S', 'H'}; //clubs, diamonds, spades, hearts
        for(char suit : suits) {
        	for(int i = 2; i <= 14; i++) { //14=ace, 13=king, 12=queen, 11=jack, rest are same
        		this.add(new Card(suit, i)); //new unique card has been added
        	}
        }
        Collections.shuffle(this); //randomize order of cards
    }


    // clears all cards
    // creates a brand new deck of 52 cards sorted in random order
    // essentially Deck() but clears before
    public void newDeck() {
    	clear();
        char[] suits = {'C', 'D', 'S', 'H'}; //clubs, diamonds, spades, hearts
        for(char suit : suits) {
        	for(int i = 2; i <= 14; i++) { //14=ace, 13=king, 12=queen, 11=jack, rest are same
        		this.add(new Card(suit, i)); //new unique card has been added
        	}
        }
        Collections.shuffle(this); //randomize order of cards
    }
}
