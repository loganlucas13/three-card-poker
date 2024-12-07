import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;

public class DeckTest {

	static Deck deck;

	@BeforeAll
	static void setup(){
		deck = new Deck();
	}

	@Test
	void deckTest1() {
		// checks default deck size
		deck = new Deck();
		assertEquals(52, deck.size());
	}

	@Test
	void deckClearTest1() {
		// checks if deck.clear() completely empties the deck
		deck.clear();
		assertEquals(0, deck.size());
	}

	@Test
	void newDeckTest1() {
		// testing new deck
		deck.newDeck();
		assertEquals(52, deck.size());
	}
}