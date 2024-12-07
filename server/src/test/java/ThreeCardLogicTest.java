import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ThreeCardLogicTest {
    static ArrayList<Card> hand = new ArrayList<>();
    static ArrayList<Card> dHand = new ArrayList<>();
    static ArrayList<Card> pHand = new ArrayList<>();
    static Dealer dealer;
	    
    @Test
    void evalHandTest1() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('S', 3));
    	hand.add(new Card('S', 4));
    	assertEquals(1, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest2() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('D', 3));
    	hand.add(new Card('D', 4));
    	assertEquals(3, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest3() {
    	hand = new ArrayList<>();
    	hand.add(new Card('C', 12));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('H', 13));
    	assertEquals(3, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest4() {
    	hand = new ArrayList<>();
    	hand.add(new Card('H', 2));
    	hand.add(new Card('S', 14));
    	hand.add(new Card('D', 3));
    	assertEquals(3, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest5() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 5));
    	hand.add(new Card('S', 14));
    	hand.add(new Card('S', 7));
    	assertEquals(4, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest6() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 5));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('S', 7));
    	assertEquals(0, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest7() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 3));
    	hand.add(new Card('S', 14));
    	hand.add(new Card('S', 2));
    	assertEquals(1, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest8() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 3));
    	hand.add(new Card('D', 3));
    	hand.add(new Card('H', 3));
    	assertEquals(2, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest9() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('D', 2));
    	hand.add(new Card('H', 2));
    	assertEquals(2, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest10() {
    	hand = new ArrayList<>();
    	hand.add(new Card('C', 14));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('H', 14));
    	assertEquals(2, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest11() {
    	hand = new ArrayList<>();
    	hand.add(new Card('C', 2));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('H', 14));
    	assertEquals(5, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest12() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('D', 3));
    	hand.add(new Card('H', 2));
    	assertEquals(5, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest13() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 5));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('C', 2));
    	assertEquals(0, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest14() {
    	hand = new ArrayList<>();
    	hand.add(new Card('D', 13));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('C', 2));
    	assertEquals(0, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest15() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('D', 5));
    	hand.add(new Card('C', 9));
    	assertEquals(0, ThreeCardLogic.evalHand(hand));
    }
    
    @Test
    void evalHandTest16() {
    	hand = new ArrayList<>();
    	hand.add(new Card('D', 3));
    	hand.add(new Card('S', 3));
    	hand.add(new Card('H', 3));
    	assertEquals(2, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void evalHandTest17() {
    	hand = new ArrayList<>();
    	hand.add(new Card('H', 2));
    	hand.add(new Card('H', 9));
    	hand.add(new Card('C', 9));
    	assertEquals(5, ThreeCardLogic.evalHand(hand));
    }
    
    @Test
    void evalPPTest1() {
    	hand = new ArrayList<>();
    	hand.add(new Card('D', 2));
    	hand.add(new Card('D', 3));
    	hand.add(new Card('D', 4));
    	int bet = 5;
    	assertEquals(200, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest2() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('D', 3));
    	hand.add(new Card('S', 4));
    	int bet = 10;
    	assertEquals(60, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest3() {
    	hand = new ArrayList<>();
    	hand.add(new Card('H', 12));
    	hand.add(new Card('S', 14));
    	hand.add(new Card('D', 13));
    	int bet = 8;
    	assertEquals(48, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest4() {
    	hand = new ArrayList<>();
    	hand.add(new Card('H', 2));
    	hand.add(new Card('S', 14));
    	hand.add(new Card('D', 3));
    	int bet = 25;
    	assertEquals(150, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest5() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 5));
    	hand.add(new Card('S', 14));
    	hand.add(new Card('S', 7));
    	int bet = 6;
    	assertEquals(18, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest6() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 5));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('S', 7));
    	int bet = 5;
    	assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest7() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 3));
    	hand.add(new Card('S', 14));
    	hand.add(new Card('S', 2));
    	int bet = 6;
    	assertEquals(240, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest8() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 3));
    	hand.add(new Card('D', 3));
    	hand.add(new Card('H', 3));
    	int bet = 10;
    	assertEquals(300, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest9() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('D', 2));
    	hand.add(new Card('H', 2));
    	int bet = 15;
    	assertEquals(450, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest10() {
    	hand = new ArrayList<>();
    	hand.add(new Card('C', 14));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('H', 14));
    	int bet = 5;
    	assertEquals(150, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest11() {
    	hand = new ArrayList<>();
    	hand.add(new Card('C', 2));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('H', 14));
    	int bet = 25;
    	assertEquals(25, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest12() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('D', 3));
    	hand.add(new Card('H', 2));
    	int bet = 15;
    	assertEquals(15, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest13() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 5));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('C', 2));
    	int bet = 25;
    	assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest14() {
    	hand = new ArrayList<>();
    	hand.add(new Card('D', 13));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('C', 2));
    	int bet = 13;
    	assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest15() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('D', 5));
    	hand.add(new Card('C', 9));
    	int bet = 20;
    	assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, bet));
    }
    
    @Test
    void evalPPTest16() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('D', 3));
    	hand.add(new Card('H', 2));
    	int bet = 5;
    	assertEquals(5, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest17() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 5));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('S', 9));
    	int bet = 0;
    	assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest18() {
    	hand = new ArrayList<>();
    	hand.add(new Card('D', 13));
    	hand.add(new Card('D', 14));
    	hand.add(new Card('D', 2));
    	int bet = 6;
    	assertEquals(18, ThreeCardLogic.evalPPWinnings(hand, bet));
    }

    @Test
    void evalPPTest19() {
    	hand = new ArrayList<>();
    	hand.add(new Card('S', 2));
    	hand.add(new Card('S', 3));
    	hand.add(new Card('S', 4));
    	int bet = 5;
    	assertEquals(200, ThreeCardLogic.evalPPWinnings(hand, bet));
    }
    
    @Test
    void compareTest1() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('H', 2));
    	dHand.add(new Card('H', 3));
    	dHand.add(new Card('H', 4));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('H', 2));
    	pHand.add(new Card('H', 3));
    	pHand.add(new Card('H', 4));
    	assertEquals(0, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest2() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('H', 2));
    	dHand.add(new Card('C', 3));
    	dHand.add(new Card('D', 4));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('H', 12));
    	pHand.add(new Card('S', 14));
    	pHand.add(new Card('D', 13));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest3() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('H', 2));
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('D', 3));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 5));
    	pHand.add(new Card('S', 14));
    	pHand.add(new Card('S', 7));
    	assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest4() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 5));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('S', 7));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 3));
    	pHand.add(new Card('S', 14));
    	pHand.add(new Card('S', 2));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest5() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 3));
    	dHand.add(new Card('D', 3));
    	dHand.add(new Card('H', 3));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 2));
    	pHand.add(new Card('D', 2));
    	pHand.add(new Card('H', 2));
    	assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest6() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('C', 14));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('H', 14));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('C', 2));
    	pHand.add(new Card('D', 14));
    	pHand.add(new Card('H', 14));
    	assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest7() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('D', 3));
    	dHand.add(new Card('H', 2));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 5));
    	pHand.add(new Card('D', 14));
    	pHand.add(new Card('C', 2));
    	assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest8() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('C', 2));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 2));
    	pHand.add(new Card('D', 5));
    	pHand.add(new Card('C', 9));
    	assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest9() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('H', 10));
    	dHand.add(new Card('D', 5));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 2));
    	pHand.add(new Card('D', 5));
    	pHand.add(new Card('S', 10));
    	assertEquals(0, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest10() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('H', 3));
    	dHand.add(new Card('D', 5));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 2));
    	pHand.add(new Card('D', 3));
    	pHand.add(new Card('S', 6));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest11() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 3));
    	dHand.add(new Card('H', 3));
    	dHand.add(new Card('D', 4));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 4));
    	pHand.add(new Card('D', 4));
    	pHand.add(new Card('S', 5));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest12() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 3));
    	dHand.add(new Card('H', 4));
    	dHand.add(new Card('D', 5));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 4));
    	pHand.add(new Card('D', 6));
    	pHand.add(new Card('S', 5));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest13() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 3));
    	dHand.add(new Card('H', 3));
    	dHand.add(new Card('D', 3));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 14));
    	pHand.add(new Card('D', 14));
    	pHand.add(new Card('C', 14));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest14() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('S', 3));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('H', 12));
    	pHand.add(new Card('H', 13));
    	pHand.add(new Card('H', 14));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest15() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 12));
    	dHand.add(new Card('C', 13));
    	dHand.add(new Card('D', 14));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('H', 12));
    	pHand.add(new Card('H', 13));
    	pHand.add(new Card('H', 14));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest16() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('C', 13));
    	dHand.add(new Card('D', 14));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('D', 12));
    	pHand.add(new Card('C', 12));
    	pHand.add(new Card('H', 14));
    	assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest17() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('S', 10));
    	dHand.add(new Card('S', 6));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 13));
    	pHand.add(new Card('S', 12));
    	pHand.add(new Card('S', 3));
    	assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
    }

	@Test
	void compareTest18() {
		dHand = new ArrayList<>();
		dHand.add(new Card('S', 12));
		dHand.add(new Card('C', 7));
		dHand.add(new Card('H', 3));

		pHand = new ArrayList<>();
		pHand.add(new Card('H', 12));
		pHand.add(new Card('D', 5));
		pHand.add(new Card('C', 4));

		assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
	}
	
    @Test
    void compareTest19() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('D', 3));
    	dHand.add(new Card('H', 2));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 5));
    	pHand.add(new Card('D', 14));
    	pHand.add(new Card('S', 9));
    	assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
    }

    @Test
    void compareTest20() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('D', 2));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 2));
    	pHand.add(new Card('S', 3));
    	pHand.add(new Card('S', 4));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }
    
    @Test
    void compareTest21() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('D', 2));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 13));
    	pHand.add(new Card('S', 14));
    	pHand.add(new Card('S', 3));
    	assertEquals(2, ThreeCardLogic.CompareHands(dHand, pHand));
    }
    
    @Test
    void compareTest22() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('D', 3));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 14));
    	pHand.add(new Card('D', 13));
    	pHand.add(new Card('S', 3));
    	assertEquals(0, ThreeCardLogic.CompareHands(dHand, pHand));
    }
    
    @Test
    void compareTest23() {
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('D', 12));

    	pHand = new ArrayList<>();
    	pHand.add(new Card('S', 14));
    	pHand.add(new Card('S', 3));
    	pHand.add(new Card('S', 2));
    	assertEquals(1, ThreeCardLogic.CompareHands(dHand, pHand));
    }
    
    @Test
    void qualifyTest1() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('D', 12));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }
    
    @Test
    void qualifyTest2() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('C', 2));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }
    
    @Test
    void qualifyTest3() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('D', 5));
    	dHand.add(new Card('C', 9));
    	dealer.setDealersHand(dHand);
    	assertEquals(false, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest4() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('H', 10));
    	dHand.add(new Card('D', 5));
    	dealer.setDealersHand(dHand);
    	assertEquals(false, ThreeCardLogic.doesDealerQualify(dealer));
    }
    
    @Test
    void qualifyTest5() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('S', 5));
    	dHand.add(new Card('S', 10));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest6() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('H', 3));
    	dHand.add(new Card('D', 5));
    	dealer.setDealersHand(dHand);
    	assertEquals(false, ThreeCardLogic.doesDealerQualify(dealer));
    }
    
    @Test
    void qualifyTest7() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('D', 3));
    	dHand.add(new Card('S', 6));
    	dealer.setDealersHand(dHand);
    	assertEquals(false, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest8() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 3));
    	dHand.add(new Card('H', 3));
    	dHand.add(new Card('D', 3));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest9() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 4));
    	dHand.add(new Card('S', 5));
    	dHand.add(new Card('S', 6));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest10() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 3));
    	dHand.add(new Card('H', 4));
    	dHand.add(new Card('D', 5));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest11() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 4));
    	dHand.add(new Card('D', 6));
    	dHand.add(new Card('S', 5));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest12() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 3));
    	dHand.add(new Card('H', 3));
    	dHand.add(new Card('D', 3));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }
    
    @Test
    void qualifyTest13() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('C', 14));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest14() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('S', 3));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest15() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('H', 12));
    	dHand.add(new Card('H', 13));
    	dHand.add(new Card('H', 14));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest16() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 12));
    	dHand.add(new Card('C', 13));
    	dHand.add(new Card('D', 14));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest17() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('H', 12));
    	dHand.add(new Card('H', 13));
    	dHand.add(new Card('H', 14));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest18() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('C', 13));
    	dHand.add(new Card('D', 14));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest19() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 12));
    	dHand.add(new Card('C', 12));
    	dHand.add(new Card('H', 14));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest20() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('S', 10));
    	dHand.add(new Card('S', 6));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest21() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 13));
    	dHand.add(new Card('S', 12));
    	dHand.add(new Card('S', 3));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest22() {
    	dealer = new Dealer();
		dHand = new ArrayList<>();
		dHand.add(new Card('S', 12));
		dHand.add(new Card('C', 7));
		dHand.add(new Card('H', 3));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest23() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
		dHand.add(new Card('H', 12));
		dHand.add(new Card('D', 5));
		dHand.add(new Card('C', 4));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
	}
	
    @Test
    void qualifyTest24() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('D', 3));
    	dHand.add(new Card('H', 2));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest25() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 5));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('S', 9));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest26() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('D', 2));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest27() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 2));
    	dHand.add(new Card('S', 3));
    	dHand.add(new Card('S', 4));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }
    
    @Test
    void qualifyTest28() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('D', 14));
    	dHand.add(new Card('D', 2));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest29() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 13));
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('S', 3));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }
    
    @Test
    void qualifyTest30() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('D', 13));
    	dHand.add(new Card('S', 14));
    	dHand.add(new Card('D', 3));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }

    @Test
    void qualifyTest31() {
    	dealer = new Dealer();
    	dHand = new ArrayList<>();
    	dHand.add(new Card('S', 4));
    	dHand.add(new Card('D', 3));
    	dHand.add(new Card('S', 3));
    	dealer.setDealersHand(dHand);
    	assertEquals(true, ThreeCardLogic.doesDealerQualify(dealer));
    }
}
