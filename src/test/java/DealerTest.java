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
		dealer = new Dealer();
		assertEquals(52, dealer.getDeck().size());
	}
	
	@Test
	void dealerTest2() {
		dealer = new Dealer();
		assertEquals(true, dealer.getQualify());
	}
	
	@Test
	void dealerTest3() {
		dealer.getDeck().remove(0);
		dealer.getDeck().remove(0);
		dealer.getDeck().remove(0);
		assertEquals(49, dealer.getDeck().size());
	}
	
	@Test
	void dealerTest4() {
		dealer = new Dealer();
		for(int i = 0; i < 52; i++) {
			dealer.getDeck().remove(0);
		}
		assertEquals(0, dealer.getDeck().size());
	}
	
	@Test
	void dealHandTest1() {
		dealer = new Dealer();
		ArrayList<Card> three = new ArrayList<Card>();
		three = dealer.dealHand();
		assertEquals(3, three.size());
	}
	
	@Test
	void dealHandTest2() {
		dealer = new Dealer();
		ArrayList<Card> three = new ArrayList<Card>();
		for(int i = 0; i < 10; i++) {
			three = dealer.dealHand();
		}
		assertEquals(3, three.size());
	}
	
	@Test
	void dealHandTest3() {
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
		dealer.setQualify(false);
		assertEquals(false, dealer.getQualify());
	}
	
	@Test
	void flippedTest1() {
		dealer.setHasFlipped(false);
		assertEquals(false, dealer.getHasFlipped());
	}
	
	@Test
	void handTest1() {
		ArrayList<Card> three = new ArrayList<Card>();
		three = dealer.dealHand();
		dealer.setDealersHand(three);
		assertEquals(3, dealer.getDealersHand().size());
	}
	
	@Test
	void handTest2() {
		dealer = new Dealer();
		assertEquals(null, dealer.getDealersHand());
	}
	
	@Test
	void detailTest1() {
		ArrayList<Card> three = new ArrayList<Card>();
    	three.add(new Card('D', 13));
    	three.add(new Card('D', 14));
    	three.add(new Card('C', 2));
    	dealer.setDealersHand(three);
    	assertEquals("HIGH CARD", dealer.handToString());
	}
	
	@Test
	void detailTest2() {
		ArrayList<Card> three = new ArrayList<Card>();
    	three.add(new Card('S', 14));
    	three.add(new Card('D', 14));
    	three.add(new Card('C', 2));
    	dealer.setDealersHand(three);
    	assertEquals("PAIR", dealer.handToString());
	}
	
	@Test
	void detailTest3() {
		ArrayList<Card> three = new ArrayList<Card>();
    	three.add(new Card('S', 14));
    	three.add(new Card('D', 14));
    	three.add(new Card('C', 14));
    	dealer.setDealersHand(three);
    	assertEquals("THREE OF A KIND", dealer.handToString());
	}
}
