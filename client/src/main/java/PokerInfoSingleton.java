public class PokerInfoSingleton {
    // so that only one gameInstance exists
    private static PokerInfo gameInstance;

    // returns instance of the game
    // if game instance doesn't exist, then creates a new instance and returns it
    public static PokerInfo getInstance() {
        if (gameInstance == null) {
            gameInstance = new PokerInfo();
        }
        return gameInstance;
    }
}
