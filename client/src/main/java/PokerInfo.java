import java.io.Serializable;
import java.util.ArrayList;

public class PokerInfo implements Serializable {
    // data members
    private String request;
    private String ip;
    private int port;
    private int playerCount;
    private int ante;
    private int pairPlus;
    private int play;
    private int totalWinnings;
    private int roundWinnings;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;
    private String dealerHandString;
    private String playerHandString;
    private boolean showDealer;
    private boolean showPlayer;
    private boolean hasFolded;
    private boolean willPushAnte;
    private String result; // payout result

    // constructor
    public PokerInfo() {
        this.request = "NONE";
        this.ip = "127.0.0.1";
        this.port = -1;
        this.ante = 5;
        this.pairPlus = 0;
        this.totalWinnings = 0;
        this.dealerHand = new ArrayList<>();
        this.playerHand = new ArrayList<>();
        this.showDealer = false;
        this.showPlayer = false;
    }


    // getters and setters

    // request
    public String getRequest() {
        return this.request;
    }
    public void setRequest(String request) {
        this.request = request;
    }

    // ip
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    // port
    public int getPort() {
        return this.port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    // playerCount
    public int getPlayerCount() {
    	return this.playerCount;
    }
    public void setPlayerCount(int count) {
    	this.playerCount = count;
    }

    // ante
    public int getAnte() {
    	return this.ante;
    }
    public void setAnte(int ante) {
    	this.ante = ante;
    }

    // pair plus
    public int getPairPlus() {
    	return this.pairPlus;
    }
    public void setPairPlus(int pairPlus) {
    	this.pairPlus = pairPlus;
    }

    // play
    public int getPlay() {
        return this.play;
    }
    public void setPlay(int play) {
        this.play = play;
    }

    // total winnings
    public int getTotalWinnings() {
    	return this.totalWinnings;
    }
    public void setTotalWinnings(int totalWinnings) {
    	this.totalWinnings = totalWinnings;
    }

    // round winnings
    public int getRoundWinnings() {
        return this.roundWinnings;
    }
    public void setRoundWinnings(int roundWinnings) {
        this.roundWinnings = roundWinnings;
    }

    // dealer's hand
    public ArrayList<Card> getDealerHand() {
    	return this.dealerHand;
    }
    public void setDealerHand(ArrayList<Card> dealerHand) {
    	this.dealerHand = dealerHand;
    }

    // player's hand
    public ArrayList<Card> getPlayerHand() {
    	return this.playerHand;
    }
    public void setPlayerHand(ArrayList<Card> playerHand) {
    	this.playerHand = playerHand;
    }

    // dealer hand as a string (ex. straight flush)
    public String getDealerHandString() {
        return this.dealerHandString;
    }
    public void setDealerHandString(String dealerHandString) {
        this.dealerHandString = dealerHandString;
    }

    // player hand as a string (ex. straight flush)
    public String getPlayerHandString() {
        return this.playerHandString;
    }
    public void setPlayerHandString(String playerHandString) {
        this.playerHandString = playerHandString;
    }

    // show dealer hand
    public boolean getShowDealer() {
    	return this.showDealer;
    }
    public void setShowDealer(boolean showDealer) {
    	this.showDealer = showDealer;
    }

    // show player hand
    public boolean getShowPlayer() {
    	return this.showPlayer;
    }
    public void setShowPlayer(boolean showPlayer) {
    	this.showPlayer = showPlayer;
    }

    // has player folded
    public boolean getHasFolded() {
        return this.hasFolded;
    }
    public void setHasFolded(boolean hasFolded) {
        this.hasFolded = hasFolded;
    }

    // will push ante (for when the dealer doesn't qualify)
    public boolean getWillPushAnte() {
        return this.willPushAnte;
    }
    public void setWillPushAnte(boolean willPushAnte) {
        this.willPushAnte = willPushAnte;
    }

    // result
    public String getResult() {
        return this.result;
    }
    public void setResult(String result) {
        this.result = result;
    }
}
