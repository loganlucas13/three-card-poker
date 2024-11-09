import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

public class ThreeCardLogic {

    // returns an integer value representing the value of the hand passed in
    // 0 - high card
    // 1 - straight flush
    // 2 - three of a kind
    // 3 - straight
    // 4 - flush
    // 5 - pair
    public static int evalHand(ArrayList<Card> hand) {
    	Set<Integer> sameV = new HashSet<>();
        ArrayList<Integer> sortedV = new ArrayList<>(); //will get all cards and sort them later
    	Set<Character> sameS = new HashSet<>();
    	int res = 0; //return this value
    	int valueCount = 1; //counts how many number repeats there are
    	int suitCount = 1; //counts how many suit repeats there are

    	//go through each card in the hand
        for(Card card : hand) {
        	int value = card.getValue();
        	char suit = card.getSuit();
        	//checks if there are value repeats in sameV or not
        	if(sameV.contains(value)) valueCount += 1;
        	else sameV.add(value);
        	//checks if there are suit repeats in sameV or not
        	if(sameS.contains(suit)) suitCount += 1;
        	else sameS.add(suit);
        	//this is for the straight hand
        	sortedV.add(value);
        }

        int straightFlush = 0; //this integer verifies if it's a straight flush. 2 = true. 1, 0 = false.

        //if there's a pair, result is now a pair
        if(valueCount == 2) res = 5;

        //if there is a flush, result is now a flush, and straightFlush is 1/2 complete
        if(suitCount == 3) {
        	res = 4;
        	straightFlush += 1;
        }

        //sort sortedV, then looks at the other 2 cards
        //to make sure if it's +1 and +2 of the beginning card's value
        //if there's a straight, result is now a straight and straightFlush is 1/2 complete
        Collections.sort(sortedV);
        if(sortedV.get(0) == sortedV.get(1) - 1 && sortedV.get(0) == sortedV.get(2) - 2) {
        	res = 3;
        	straightFlush += 1;
        }
        //edge case where ACE is in the straight, so A23 or QKA works
        else if(sortedV.get(2) == 14 && sortedV.get(2) == sortedV.get(1) + 11 && sortedV.get(2) == sortedV.get(0) + 12) {
        	res = 3;
        	straightFlush += 1;
        }

        //if there is a three of a kind, result is now a three of a kind
        if(valueCount == 3) res = 2;

        //checks if straightFlush is 2 from length == 3 and suitCount ==3, result becomes a Straight Flush
        if(straightFlush == 2) res = 1;

        return res;
    }


    // returns the amount won for the PairPlus bet
    // if the player loses, return 0
    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
        switch (evalHand(hand)) {
            case 5: // pair
                return bet;
            case 4: // flush
                return bet * 3;
            case 3: // straight
                return bet * 6;
            case 2: // three of a kind
                return bet * 30;
            case 1: // straight flush
                return bet * 40;
            default: // loss
                return 0; // could return bet * -1, but returns since no amount was won
        }
    }

    // compare the hands passed in an returns an integer based on result
    // 0 - no winner
    // 1 - dealer wins
    // 2 - player wins
    public static int CompareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
        //evaluated the hands
    	int evalD = evalHand(dealer);
        int evalP = evalHand(player);
        //see if dealer's hands are worth more than the player's
        if(evalD > evalP) {
        	if(evalP == 0) return 1;
        	else return 2;
        }
        if(evalP > evalD) {
        	if(evalD == 0) return 2;
        	else return 1;
        }

        //get all values of the two hands
        ArrayList<Integer> dealerV = new ArrayList<>();
        ArrayList<Integer> playerV = new ArrayList<>();
        for(Card card : dealer) {
        	int value = card.getValue();
        	dealerV.add(value);
        }
        for(Card card : player) {
        	int value = card.getValue();
        	playerV.add(value);
        }

        //sort the values in ascending order
	    Collections.sort(dealerV);
	    Collections.sort(playerV);

	    //if the hands are a straight flush, a straight, or a flush, check each card's value to determine the winning hand
        if((evalD == 1 && evalP == 1) || (evalD == 3 && evalP == 3) || (evalD == 4 && evalP == 4)) {
        	for(int i = dealerV.size() - 1; i >= 0; i--) {
        		if(dealerV.get(i) > playerV.get(i)) {
        			return 1;
        		}
        		else if(dealerV.get(i) < playerV.get(i)) {
        			return 2;
        		}
        	}
        	return 0;
        }

        //if the hands are a three of a kind, you only need to check the highest value card for each hand
        else if(evalD == 2 && evalP == 2) {
        	if(dealerV.get(0) > playerV.get(0)) return 1;
        	else if(dealerV.get(0) < playerV.get(0)) return 2;
        	return 0;
        }

        //separate dealer hand into pair values and the kicker value
        int dealerPair = 0;
        int dealerKicker = 0;
        for(int value : dealerV) {
        	if(dealerPair != value && dealerPair != 0) {
        		dealerKicker = value;
        	}
        	if(dealerPair == 0) {
            	dealerPair = value;
        	}
        }
        //separate player hand into pair values and the kicker value
        int playerPair = 0;
        int playerKicker = 0;
        for(int value : playerV) {
        	if(playerPair != value && playerPair != 0) playerKicker = value;
        	if(playerPair == 0) playerPair = value;
        }

        //checks if the pair is greater for one hand
        if(playerPair > dealerPair) return 2;
        else if(playerPair < dealerPair) return 1;
        //checks if the kicker is greater for one hand
        if(playerKicker > dealerKicker) return 2;
        else if(playerKicker < dealerKicker) return 1;
        //if hands are both equal in value, return 0
        return 0;
    }
}
