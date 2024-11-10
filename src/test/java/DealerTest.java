import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DealerTest {
	static Dealer dealer;

	@BeforeAll
	static void setup(){
		dealer = new Dealer();
	}

	@Test
	void dealerTest1() {
		// checks size of newly constructed deck
		dealer = new Dealer();
		assertEquals(52, dealer.getDeck().size());
	}

	@Test
	void dealerTest2() {
		// dealer should qualify by default
		dealer = new Dealer();
		assertEquals(true, dealer.getQualify());
	}

	@Test
	void dealerTest3() {
		// checks if removing card from the deck works as expected
		dealer.getDeck().remove(0);
		dealer.getDeck().remove(0);
		dealer.getDeck().remove(0);
		assertEquals(49, dealer.getDeck().size());
	}

	@Test
	void dealerTest4() {
		// same as test 4, but in a loop
		dealer = new Dealer();
		for(int i = 0; i < 52; i++) {
			dealer.getDeck().remove(0);
		}
		assertEquals(0, dealer.getDeck().size());
	}

	@Test
	void dealHandTest1() {
		// checks if deal hand works after being called once
		dealer = new Dealer();
		ArrayList<Card> three = new ArrayList<Card>();
		three = dealer.dealHand();
		assertEquals(3, three.size());
	}

	@Test
	void dealHandTest2() {
		// checks if deal hand works after being called multiple times
		dealer = new Dealer();
		ArrayList<Card> three = new ArrayList<Card>();
		for(int i = 0; i < 10; i++) {
			three = dealer.dealHand();
		}
		assertEquals(3, three.size());
	}

	@Test
	void dealHandTest3() {
		// checks if deck is replenished when it hits size == 34
		for(int i = 0; i < 30; i++) {
			dealer.getDeck().remove(0);
		}
		ArrayList<Card> three = new ArrayList<Card>();
		three = dealer.dealHand();
		assertEquals(3, three.size());
		assertEquals(49, dealer.getDeck().size());
	}

	@Test
	void qualifyTest1() {
		// testing set/get qualify
		dealer.setQualify(false);
		assertEquals(false, dealer.getQualify());
	}

	@Test
	void flippedTest1() {
		// testing set/get hasFlipped
		dealer.setHasFlipped(false);
		assertEquals(false, dealer.getHasFlipped());
	}

	@Test
	void handTest1() {
		// checking updating of dealers hand
		ArrayList<Card> three = new ArrayList<Card>();
		three = dealer.dealHand();
		dealer.setDealersHand(three);
		assertEquals(3, dealer.getDealersHand().size());
	}

	@Test
	void handTest2() {
		// by default, the dealer's hand is empty
		dealer = new Dealer();
		assertEquals(null, dealer.getDealersHand());
	}

	@Test
	void detailTest1() {
		// checks for high card
		ArrayList<Card> three = new ArrayList<Card>();
    	three.add(new Card('D', 13));
    	three.add(new Card('D', 14));
    	three.add(new Card('C', 2));
    	dealer.setDealersHand(three);
    	assertEquals("HIGH CARD", dealer.handToString());
	}

	@Test
	void detailTest2() {
		// checks for pair
		ArrayList<Card> three = new ArrayList<Card>();
    	three.add(new Card('S', 14));
    	three.add(new Card('D', 14));
    	three.add(new Card('C', 2));
    	dealer.setDealersHand(three);
    	assertEquals("PAIR", dealer.handToString());
	}

	@Test
	void detailTest3() {
		// checks for three of a kind
		ArrayList<Card> three = new ArrayList<Card>();
    	three.add(new Card('S', 14));
    	three.add(new Card('D', 14));
    	three.add(new Card('C', 14));
    	dealer.setDealersHand(three);
    	assertEquals("THREE OF A KIND", dealer.handToString());
	}
}
