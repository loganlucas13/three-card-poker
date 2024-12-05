
import java.io.Serializable;

public class PokerInfoSingleton implements Serializable {
    // so that only one gameInstance exists
    private static PokerInfo gameInstance;

    // returns instance of the game
    // if game instance doesn't exist, then creates a new instance and returns it
    public static PokerInfo getInstance() {
        if (gameInstance == null) {
            PokerInfoSingleton.gameInstance = new PokerInfo();
        }
        return PokerInfoSingleton.gameInstance;
    }

    // resets instance of game (use case ex - when starting new round)
    public static void resetGameInstance() {
        PokerInfoSingleton.gameInstance = new PokerInfo();
    }
}
