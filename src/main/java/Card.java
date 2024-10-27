public class Card {

    // data members
    private char suit;
    private int value;


    // constructor
    public Card(char suit, int value) {
        this.suit = suit;
        this.value = value;
    }


    // getters and setters

    // suit
    public char getSuit() {
        return this.suit;
    }
    public void setSuit(char suit) {
        this.suit = suit;
    }

    // value
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    }

}
