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
    	Set<Character> sameS = new HashSet<>();
    	int res = 0;
    	int valueCount = 1;
    	int suitCount = 1;
        for(Card card : hand) {
        	int value = card.getValue();
        	char suit = card.getSuit();
        	if(sameV.contains(value)) valueCount += 1;
        	else sameV.add(value);
        	if(sameS.contains(suit)) suitCount += 1;
        	else sameS.add(suit);
        }
        int straightFlush = 0;
        if(valueCount == 2) res = 5;
        int length = 1;
        for(int value : sameV) {
            if(!sameV.contains(value - 1)) {
                while(sameV.contains(value + length)) length++;
            }
        }
        if(length == 3) {
        	res = 4;
        	straightFlush += 1;
        }
        if(suitCount == 3) {
        	res = 3;
        	straightFlush += 1;
        }
        if(valueCount == 3) res = 2;
        if(straightFlush == 2) res = 1;
        return res;
    }


    // returns the amount won for the PairPlus bet
    // if the player loses, return 0
    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
        // TODO: implement
        return -1;
    }


    // compare the hands passed in an returns an integer based on result
    // 0 - no winner
    // 1 - dealer wins
    // 2 - player wins
    public static int CompareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
        // TODO: implement
        return -1;
    }
}
