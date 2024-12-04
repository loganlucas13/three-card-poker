import java.io.Serializable;
import java.util.ArrayList;

public class PokerInfo implements Serializable{
    // data members
    private String ip;
    private int port;
    private int playerCount;
    private int ante;
    private int pairPlus;
    private int totalWinnings;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;
    private boolean showDealer;
    private boolean showPlayer;
    
    // constructor
    public PokerInfo() {
        this.ip = "undefined";
        this.port = -1;
        this.ante = 0;
        this.pairPlus = 0;
        this.totalWinnings = 0;
        this.dealerHand = new ArrayList<>();
        this.playerHand = new ArrayList<>();
        this.showDealer = false;
        this.showPlayer = false;
    }

    // getters and setters

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
    
    
    // total winnings
    public int getTotalWinnings() {
    	return this.totalWinnings;
    }
    
    public void setTotalWinnings(int totalWinnings) {
    	this.totalWinnings = totalWinnings;
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
}
