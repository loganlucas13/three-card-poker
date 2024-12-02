import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
//import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController implements Initializable {

    // FXML components
    @FXML
    private MenuItem freshStartButton;
    @FXML
    private MenuItem newLookButton;
    @FXML
    private MenuItem exitButton;

    @FXML
    private Label player1Winnings;
    @FXML
    private Label player2Winnings;

    @FXML
    private Label player1HandType;
    @FXML
    private Label player2HandType;
    @FXML
    private Label dealerHandType;

    @FXML
    private VBox player1ButtonBox;
    @FXML
    private VBox player2ButtonBox;

    @FXML
    private Label player1AnteBet;
    @FXML
    private Label player1PlayBet;
    @FXML
    private Label player1PairPlusBet;

    @FXML
    private Button player1IncreaseAnteBet;
    @FXML
    private Button player1DecreaseAnteBet;
    @FXML
    private Button player1IncreasePairPlusBet;
    @FXML
    private Button player1DecreasePairPlusBet;
    @FXML
    private Button player1IncreasePlayBet;
    @FXML
    private Button player1ConfirmBets;
    @FXML
    private Button player1Fold;

    @FXML
    private Label player2AnteBet;
    @FXML
    private Label player2PlayBet;
    @FXML
    private Label player2PairPlusBet;

    @FXML
    private Button player2IncreaseAnteBet;
    @FXML
    private Button player2DecreaseAnteBet;
    @FXML
    private Button player2IncreasePairPlusBet;
    @FXML
    private Button player2DecreasePairPlusBet;
    @FXML
    private Button player2IncreasePlayBet;
    @FXML
    private Button player2ConfirmBets;
    @FXML
    private Button player2Fold;

    @FXML
    private ImageView dealerCard1;
    @FXML
    private ImageView dealerCard2;
    @FXML
    private ImageView dealerCard3;

    @FXML
    private ImageView player1Card1;
    @FXML
    private ImageView player1Card2;
    @FXML
    private ImageView player1Card3;

    @FXML
    private ImageView player2Card1;
    @FXML
    private ImageView player2Card2;
    @FXML
    private ImageView player2Card3;

    // data members
    // (for game functionality)
    private Dealer dealer;

    private Player player1;
    private Player player2;

    private ArrayList<Button> player1BetButtons;
    private ArrayList<Button> player2BetButtons;

    private ArrayList<ImageView> dealerCards;
    private ArrayList<ImageView> player1Cards;
    private ArrayList<ImageView> player2Cards;

    private String cardBackFileName;


    // constructor
    public GameController() {
        this.dealer = new Dealer();

        this.player1 = new Player();
        this.player2 = new Player();

        this.cardBackFileName = "/images/deck-of-cards/backs/Back2.png";
    }


    // event handler for exit button
    private void quitGame() throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION, "ARE YOU SURE YOU WANT TO EXIT?");
        alert.setTitle("Exit Confirmation");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("styles/alert.css").toExternalForm());
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.getDialogPane().setPadding(new Insets(20));

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
            }
        });
    }


    // event handler for new look button
    // updates card backing randomly
    private void updateAppearance() throws Exception {
        ArrayList<String> filenames = new ArrayList<String>();
        String defaultPath = "/images/deck-of-cards/backs/Back";

        for (int i = 1; i <= 8; i++) {
            filenames.add(defaultPath+i+".png");
        }

        int randomIndex = (int) (Math.random() * 8);
        // the while loop prevents the same backing from being picked twice in a row
        while (filenames.get(randomIndex).equals(this.cardBackFileName)) {
            randomIndex = (int) (Math.random() * 8);
        }

        this.cardBackFileName = filenames.get(randomIndex);

        if (!(this.player1.getHasConfirmed() && this.player2.getHasConfirmed())) {
            // if both players have not confirmed, update card image to the backing (cards haven't been flipped yet)
            this.setAllCardImages();
        }
        else if (!this.dealer.getHasFlipped()) {
            // if the dealer has not flipped their cards, update the backing of the cards
            for (ImageView card : this.dealerCards) {
                this.setCardImage(card, this.cardBackFileName);
            }
        }
    }


    // resets game
    // can be used to either reset the game completely or move onto the next round
    // 'willResetWinnings' should be false when continuing the game, true otherwise
    // keepAnte1 and keepAnte2 determine if they'll be kept because of dealer's qualification
    // main variable kind of just exists for this method to be used after game ends
    private void resetGame(boolean willResetWinnings, int main) throws Exception {
        this.dealer = new Dealer();

        this.initializeDealer();

        //ante1,ante2,keep1,keep2 are meant to see if the bet should be kept to the next round,
        //depending on dealer's qualification
        int ante1 = this.player1.getAnteBet();
        int ante2 = this.player2.getAnteBet();
        boolean keep1 = this.player1.getKeepAnte();
        boolean keep2 = this.player1.getKeepAnte();
        //remember the winnings if not reset
        int remember1 = this.player1.getTotalWinnings();
        int remember2 = this.player2.getTotalWinnings();

        this.player1 = new Player();
        this.player2 = new Player();

        this.initializePlayer1();
        this.initializePlayer2();

        this.dealerHandType.setText("HAND TYPE: ???");
        this.player1HandType.setText("HAND TYPE: ???");
        this.player2HandType.setText("HAND TYPE: ???");

        //keep ante bet for next round if played and dealer's cards are not qualified
        if (keep1) {
        	this.player1.setAnteBet(ante1);
        	this.updateBetAmount(player1, player1AnteBet, "ante");
            this.player1IncreaseAnteBet.setDisable(true);
            this.player1DecreaseAnteBet.setDisable(true);
        }
        if (keep2) {
        	this.player2.setAnteBet(ante2);
        	this.updateBetAmount(player2, player2AnteBet, "ante");
            this.player2IncreaseAnteBet.setDisable(true);
            this.player2DecreaseAnteBet.setDisable(true);
        }

        // winnings boxes in the top right
        if (willResetWinnings) {
            this.initializeWinnings(player1, player1Winnings);
            this.initializeWinnings(player2, player2Winnings);
        }
        else {
        	this.player1.setTotalWinnings(remember1);
        	this.player2.setTotalWinnings(remember2);
        }
    }


    // overloaded
    // resets game
    // can be used to either reset the game completely or move onto the next round
    // 'willResetWinnings' should be false when continuing the game, true otherwise
    private void resetGame(boolean willResetWinnings) throws Exception {
        this.dealer = new Dealer();

        this.initializeDealer();

        //remember winnings for the entire program until reset
        int remember1 = this.player1.getTotalWinnings();
        int remember2 = this.player2.getTotalWinnings();

        this.player1 = new Player();
        this.player2 = new Player();

        this.initializePlayer1();
        this.initializePlayer2();

        this.dealerHandType.setText("HAND TYPE: ???");
        this.player1HandType.setText("HAND TYPE: ???");
        this.player2HandType.setText("HAND TYPE: ???");

        // winnings boxes in the top right
        if (willResetWinnings) {
            this.initializeWinnings(player1, player1Winnings);
            this.initializeWinnings(player2, player2Winnings);
        }
        else {
        	this.player1.setTotalWinnings(remember1);
        	this.player2.setTotalWinnings(remember2);
        }
    }


    // event handler for all increase bet buttons
    // increases ante and pair plus bets by 1 OR sets play bet equal to ante bet
    // if ante or pair plus bet == 0, increases by 5 (min $5 wager)
    // if ante or pair plus bet is >= 25, they are not updated (max $25 wager)
    // valid betType parameters: "ante", "pair plus", "play"
    private void increaseBet(Player player, Label betDisplay, String betType, Button increaseButton, Button decreaseButton, Button confirmButton) throws Exception {
        if(betType.equals("ante")) {
            if (player.getAnteBet() >= 25) {
                increaseButton.setDisable(true);
                return;
            }
            else if (player.getAnteBet() == 24) {
                player.setAnteBet(player.getAnteBet()+1);
                increaseButton.setDisable(true);
            }
            else {
                player.setAnteBet(player.getAnteBet()+1);
                decreaseButton.setDisable(false);
            }
        }
        else if (betType.equals("pair plus")) {
            if (player.getPairPlusBet() >= 25) {
                increaseButton.setDisable(true);
                return;
            }
            else if (player.getPairPlusBet() == 24) {
                player.setPairPlusBet(player.getPairPlusBet()+1);
                increaseButton.setDisable(true);
            }
            else if (player.getPairPlusBet() == 0) {
                player.setPairPlusBet(player.getPairPlusBet()+5);
                decreaseButton.setDisable(false);
            }
            else {
                player.setPairPlusBet(player.getPairPlusBet()+1);
                decreaseButton.setDisable(false);
            }
        }
        else if (betType.equals("play")) {
            player.setPlayBet(player.getAnteBet()); // play bet must be equal to ante bet
            increaseButton.setDisable(true);
        }
        else {
            System.err.println("error increasing bet amount'");
            return;
        }
        this.updateBetAmount(player, betDisplay, betType);
    }


    // event handler for all decrease bet buttons
    // decreases ante and pair plus bets by 1
    // if ante <= 5, it is not updated (min $5 wager)
    // if pair plus <= 0, it is not updated (no negative bets)
    // valid betType parameters: "ante", "pair plus"
    private void decreaseBet(Player player, Label betDisplay, String betType, Button increaseButton, Button decreaseButton) throws Exception {
        if(betType.equals("ante")) {
            if (player.getAnteBet() == 25) {
                increaseButton.setDisable(false);
            }
            else if (player.getAnteBet() <= 6) {
                decreaseButton.setDisable(true);
            }
            player.setAnteBet(player.getAnteBet()-1);
        }
        else if (betType.equals("pair plus")) {
            if (player.getPairPlusBet() == 25) {
                player.setPairPlusBet(player.getPairPlusBet()-1);
                increaseButton.setDisable(false);
            }
            else if (player.getPairPlusBet() == 5) {
                player.setPairPlusBet(player.getPairPlusBet()-5);
                decreaseButton.setDisable(true);
            }
            else {
                player.setPairPlusBet(player.getPairPlusBet()-1);
            }
        }
        else {
            System.err.println("error decreasing bet amount'");
            return;
        }
        this.updateBetAmount(player, betDisplay, betType);
    }


    // changes the text color of the 'playerWinnings' label based on the player's totalWinnings data member
    // > 0 - green
    // < 0 - red
    // 0 - black (default)
    private void changeWinningsTextColor(Player player, Label playerWinnings) {
        if (player.getTotalWinnings() > 0) {
            playerWinnings.getStyleClass().removeAll("negative", "zero");
            playerWinnings.getStyleClass().add("positive");
        }
        else if (player.getTotalWinnings() < 0) {
            playerWinnings.getStyleClass().removeAll("positive", "zero");
            playerWinnings.getStyleClass().add("negative");
        }
        else {
            playerWinnings.getStyleClass().removeAll("positive", "negative");
            playerWinnings.getStyleClass().add("zero");
        }
    }


    // adds a drop shadow to a card image
    // cosmetic effect only
    private void addDropShadow(ImageView card) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#000000")); // black shadow color
        shadow.setOffsetY(2);
        shadow.setRadius(15); // set the blur radius

        card.setEffect(shadow);
    }


    // sets card image from filename
    // turns off antialiasing of image
    // example filename: '/images/deck-of-cards/backs/Back1.png'
    private void setCardImage(ImageView card, String filename) {
        Image cardImage = new Image(getClass().getResourceAsStream(filename));
        card.setImage(cardImage);
        card.setSmooth(false);

        this.addDropShadow(card);
    }


    // updates all card images to the cardBackFileName data member
    private void setAllCardImages() {
        ArrayList<ImageView> cards = new ArrayList<ImageView>();

        cards.add(dealerCard1);
        cards.add(dealerCard2);
        cards.add(dealerCard3);
        cards.add(player1Card1);
        cards.add(player1Card2);
        cards.add(player1Card3);
        cards.add(player2Card1);
        cards.add(player2Card2);
        cards.add(player2Card3);

        for (int i = 0; i < 9; i++) {
            this.setCardImage(cards.get(i), cardBackFileName);
        }
    }


    // updates bet display on user interface
    // should be called after every increase/decrease of bet amount
    // valid betType parameters: "ante", "pair plus", "play"
    private void updateBetAmount(Player player, Label betDisplay, String betType) {
        if(betType.equals("ante")) {
            betDisplay.setText("$" + player.getAnteBet());
        }
        else if (betType.equals("pair plus")) {
            betDisplay.setText("$" + player.getPairPlusBet());
        }
        else if (betType.equals("play")) {
            betDisplay.setText("$" + player.getPlayBet());
        }
        else {
            System.err.println("invalid 'betType' parameter in 'updateBetAmount()'");
        }
    }


    // confirms all bets and disables betting buttons for a single player
    // also checks if both players have confirmed; if they have, then the game begins
    private void confirmBets(Player player, ArrayList<Button> betButtons) {
        // under minimum ante bet
        if (player.getAnteBet() < 5) {
            return;
        }

        player.setHasConfirmed(true);

        for (int i = 0; i < betButtons.size(); i++) {
            betButtons.get(i).setDisable(true);
        }

        if (player1.getHasConfirmed() && player2.getHasConfirmed()) {
            this.startGame();
        }
    }


    // starts the game, holds all function calls needed for the game to function
    private void startGame() {
        this.player1Fold.setDisable(false);
        this.player2Fold.setDisable(false);

        this.player1IncreasePlayBet.setDisable(false);
        this.player2IncreasePlayBet.setDisable(false);

        // distribute cards
        this.dealCards();

        // flips player cards (updates image)
        try {
            // flip player cards
            this.flipCards(null, this.player1, this.player1Cards);
            this.flipCards(null, this.player2, this.player2Cards);

            // update hand labels found above the card display
            this.player1HandType.setText("HAND TYPE: " + this.player1.handToString());
            this.player2HandType.setText("HAND TYPE: " + this.player2.handToString());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    // handles all events after both players have made their choice to add a play bet or fold
    // flips dealer cards, evaluates hands, and resets to begin game again
    private void completeGame() {

    	//checks to see if the dealer is qualified by checking if their hand is at least a Queen high
    	int dealerValue = ThreeCardLogic.evalHand(this.dealer.getDealersHand());
    	if(dealerValue == 0) {
    		//gets all 3 cards' values
    		ArrayList<Integer> dealerV = new ArrayList<>();
            for(Card card : this.dealer.getDealersHand()) {
            	int value = card.getValue();
            	dealerV.add(value);
            }
            //sorts it
            Collections.sort(dealerV);
            //if hand is worse than a Queen high, no longer qualified
            if(dealerV.get(2) <= 11) {
            	this.dealer.setQualify(false);
            }
    	}

    	try {
    		//flip dealer cards
    		this.flipCards(this.dealer, null, this.dealerCards);
    		//checks if dealer qualifies and writes it in the program
    		if(this.dealer.getQualify() == false) {
    			this.dealerHandType.setText("HAND TYPE: DID NOT QUALIFY");
    		}
    		else {
    			this.dealerHandType.setText("HAND TYPE: " + this.dealer.handToString());
    		}
    	}
    	catch (Exception e) {
    		System.err.println(e.getMessage());
    	}

    	//figures out the evaluation of the cards and the winnings
    	this.evaluateTheWinnings();

    	//update scoreboard
    	this.initializeWinnings(player1, player1Winnings);
    	this.initializeWinnings(player2, player2Winnings);

        this.displayWinningPopup();
    }


    // used for completeGame()
    // compares and evaluates the cards and the winnings/losses for the players
    private void evaluateTheWinnings() {
    	//finds details on the cards so that they'll get compared and evaluated
    	int player1Win = ThreeCardLogic.CompareHands(this.dealer.getDealersHand(), this.player1.getHand());
    	int player2Win = ThreeCardLogic.CompareHands(this.dealer.getDealersHand(), this.player2.getHand());
    	int player1PP = ThreeCardLogic.evalPPWinnings(this.player1.getHand(), this.player1.getPairPlusBet());
		int player2PP = ThreeCardLogic.evalPPWinnings(this.player2.getHand(), this.player2.getPairPlusBet());

		//if player lost pair plus bet, lose that money
		if(player1PP == 0) {
			player1PP = this.player1.getPairPlusBet() * -1;
		}
		if(player2PP == 0) {
			player2PP = this.player2.getPairPlusBet() * -1;
		}

    	//2 = player wins money, 1 = dealer takes the money, 0 = nothing, you get money returned
		//for 2, it adds the antebet winnings, and gives the evaluated pair plus winnings (or loss)
		//for player 1
		if(this.player1.getHasFolded() == false) {
			//does the dealer qualify
			if(this.dealer.getQualify() == false) {
				this.player1.setKeepAnte(true); //keep ante bet
				this.player1.setTotalWinnings(this.player1.getTotalWinnings() + player1PP); //calculate pair plus evaluation
			}
			//if player wins
			else if(player1Win == 2) {
				this.player1.setTotalWinnings(this.player1.getTotalWinnings() + player1PP + (2 * this.player1.getAnteBet()));
			}
			//if player loses
			else if(player1Win == 1) {
				this.player1.setTotalWinnings(this.player1.getTotalWinnings() + player1PP - (2 * this.player1.getAnteBet()));
			}
		}
		//fold
		else {
			this.player1.setTotalWinnings(this.player1.getTotalWinnings() - this.player1.getPairPlusBet() - this.player1.getAnteBet());
		}
		//for player 2
		if(this.player2.getHasFolded() == false) {
			//does the dealer qualify
			if(this.dealer.getQualify() == false) {
				this.player2.setKeepAnte(true); //keep ante bet
				this.player2.setTotalWinnings(this.player2.getTotalWinnings() + player2PP); //calculate pair plus evaluation
			}
			//if player wins
			else if(player2Win == 2) {
				this.player2.setTotalWinnings(this.player2.getTotalWinnings() + player2PP + (2 * this.player2.getAnteBet()));
			}
			//if player loses
			else if(player2Win == 1) {
				this.player2.setTotalWinnings(this.player2.getTotalWinnings() + player2PP - (2 * this.player2.getAnteBet()));
			}
		}
		//fold
		else {
			this.player2.setTotalWinnings(this.player2.getTotalWinnings() - this.player2.getPairPlusBet() - this.player2.getAnteBet());
		}
    }


    // restarts the game using the non-overloaded resetGame function (adding in main)
    private void startAgain() {
    	try {
            this.resetGame(false, 1);
        }
        catch (Exception e) {
            System.err.println("resetGame() error!");
        }
    }


    // displays popups that show up after the game has ended
    private void displayWinningPopup() {
        // based off of the controls for each player
        Stage stage1 = (Stage)this.player1ButtonBox.getScene().getWindow();
        Stage stage2 = (Stage)this.player2ButtonBox.getScene().getWindow();

        Popup player1Popup = this.createPopup(this.player1, stage1);
        Popup player2Popup = this.createPopup(this.player2, stage2);

        // to align the popup with the betting buttons
        Bounds player1Bounds = this.player1ButtonBox.localToScreen(this.player1ButtonBox.getBoundsInLocal());
        Bounds player2Bounds = this.player2ButtonBox.localToScreen(this.player2ButtonBox.getBoundsInLocal());

        player1Popup.show(stage1, player1Bounds.getMinX()+2, player1Bounds.getMinY()+2);
        player2Popup.show(stage2, player2Bounds.getMinX()+2, player1Bounds.getMinY()+2);

        // pauses the game to allow the player to read popup text
        PauseTransition pause = new PauseTransition(Duration.seconds(8));

    	pause.setOnFinished(event -> {
            player1Popup.hide();
            player2Popup.hide();
            startAgain();
        });
    	pause.play();
    }


    // constructs a popup with the desired text and returns it
    // mainly used as a helper function for this.displayWinningPopup()
    private Popup createPopup(Player player, Stage stage) {
        Popup popup = new Popup();

        Label popupLabel = new Label(player.resultToString(this.dealer));
        popupLabel.getStylesheets().add(getClass().getResource("styles/popup.css").toExternalForm());

        popup.getContent().add(popupLabel);

        return popup;
    }


    // deals cards out to the dealer and both players
    // sets 'dealersHand' data member and 'hand' data members
    private void dealCards() {
        this.dealer.setDealersHand(this.dealer.dealHand());
        this.player1.setHand(this.dealer.dealHand());
        this.player2.setHand(this.dealer.dealHand());
    }


    // flips cards for either a player or dealer, depending on input
    // if flipping for the dealer, set player == null
    // if flipping for a player, set dealer == null
    // options for 'cards' parameter: this.dealerCards, this.player1Cards, this.player2Cards
    private void flipCards(Dealer dealer, Player player, ArrayList<ImageView> cards) throws Exception {
        ArrayList<Card> hand;
        if (dealer != null) {
            hand = dealer.getDealersHand();
        }
        else if (player != null) {
            hand = player.getHand();
        }
        else {
            throw new Exception("attempting to flip cards when both parameters are null");
        }

        for (int i = 0; i < hand.size(); i++) {
            String filename = this.getCardFilename(hand.get(i));
            this.setCardImage(cards.get(i), filename);
        }
    }


    // returns card filename from 'card' parameter's suit and value
    private String getCardFilename(Card card) throws Exception {
        String filename = "/images/deck-of-cards/";

        switch (card.getSuit()) {
            case 'C':
                filename += "clubs/C";
                break;
            case 'D':
                filename += "diamonds/D";
                break;
            case 'H':
                filename += "hearts/H";
                break;
            case 'S':
                filename += "spades/S";
                break;
            default:
                throw new Exception("card has incorrect suit attribute");
        }

        switch (card.getValue()) {
            case 14:
                filename += "A";
                break;
            case 13:
                filename += "K";
                break;
            case 12:
                filename += "Q";
                break;
            case 11:
                filename += "J";
                break;
            default:
                filename += card.getValue();
        }
        return filename + ".png";
    }


    // folds hand and disables betting buttons for a single player
    // also performs visual updates (darkens cards)
    private void fold(Player player, ArrayList<Button> betButtons, ArrayList<ImageView> cards) {
        player.setHasFolded(true);

        for (int i = 0; i < betButtons.size(); i++) {
            betButtons.get(i).setDisable(true);
        }

        // darkens cards on fold
        ColorAdjust darkenFilter = new ColorAdjust();
        darkenFilter.setBrightness(-0.5);

        // preserves existing drop shadow
        DropShadow dropShadow = (DropShadow)cards.get(0).getEffect();
        darkenFilter.setInput(dropShadow);

        for (ImageView cardImage : cards) {
            cardImage.setEffect(darkenFilter);
        }
    }


    // checks if the dealer should flip their cards
    // essentially checks if both players have folded OR placed a play bet
    // returns true if they have, false otherwise
    private boolean shouldDealerFlip() {
        boolean player1Done = false;
        boolean player2Done = false;

        if (player1.getPlayBet() != 0 || player1.getHasFolded()) {
            player1Done = true;
        }
        if (player2.getPlayBet() != 0 || player2.getHasFolded()) {
            player2Done = true;
        }
        this.dealer.setHasFlipped(player1Done && player2Done);

        return this.dealer.getHasFlipped();
    }


    // initializes the winnings display in the top right for a single player
    private void initializeWinnings(Player player, Label playerWinnings) {
        playerWinnings.setText("$" + player.getTotalWinnings());
        changeWinningsTextColor(player, playerWinnings);
    }


    // initializes the menu in the top left of the screen
    private void initializeMenu() {
        // event handler for the reset button found in the menu bar (top left)
        this.freshStartButton.setOnAction(event -> {
            try {
                this.resetGame(true);
            }
            catch (Exception e) {
                System.err.println("resetGame() error!");
            }
        });

        // event handler for the new look button found in the menu bar (top left)
        this.newLookButton.setOnAction(event -> {
            try {
                this.updateAppearance();
            }
            catch (Exception e) {
                System.err.println("updateAppearance() error1");
            }
        });

        // event handler for the exit button found in the menu bar (top left)
        this.exitButton.setOnAction(event -> {
            try {
                this.quitGame();
            }
            catch (Exception e) {
                System.err.println("quitGame() error!");
            }
        });
    }


    // calls all functions needed to initialize the dealer
    private void initializeDealer() {
        // create dealerCards so that images can be updated after flipping
        this.dealerCards = new ArrayList<ImageView>(3);

        this.dealerCards.add(this.dealerCard1);
        this.dealerCards.add(this.dealerCard2);
        this.dealerCards.add(this.dealerCard3);

        // sets all card's images to the back side
        this.setCardImage(this.dealerCard1, this.cardBackFileName);
        this.setCardImage(this.dealerCard2, this.cardBackFileName);
        this.setCardImage(this.dealerCard3, this.cardBackFileName);
    }


    // calls all functions needed to initialize player1
    // lots of parameters would be needed to make a generic function
    // for both players, so i made an initialize function for each player
    private void initializePlayer1() {
        // create player1Cards so that images can be updated after flipping
        this.player1Cards = new ArrayList<ImageView>(3);

        this.player1Cards.add(this.player1Card1);
        this.player1Cards.add(this.player1Card2);
        this.player1Cards.add(this.player1Card3);

        // puts all bet buttons in a list for easy disabling (confirm/fold)
        this.player1BetButtons = new ArrayList<Button>();

        this.player1BetButtons.add(this.player1IncreaseAnteBet);
        this.player1BetButtons.add(this.player1DecreaseAnteBet);
        this.player1BetButtons.add(this.player1IncreasePairPlusBet);
        this.player1BetButtons.add(this.player1DecreasePairPlusBet);
        this.player1BetButtons.add(this.player1IncreasePlayBet);
        this.player1BetButtons.add(this.player1ConfirmBets);
        this.player1BetButtons.add(this.player1Fold);

        // sets all buttons to 'active'
        // mainly for resetGame()
        for (int i = 0; i < player1BetButtons.size(); i++) {
            player1BetButtons.get(i).setDisable(false);
        }

        // can only place a play bet after you have seen your cards
        this.player1IncreasePlayBet.setDisable(true);

        // can't decrease at starting bet value
        this.player1DecreaseAnteBet.setDisable(true);
        this.player1DecreasePairPlusBet.setDisable(true);

        // can't fold at the very start
        this.player1Fold.setDisable(true);

        // updates bet display
        this.updateBetAmount(player1, player1AnteBet, "ante");
        this.updateBetAmount(player1, player1PairPlusBet, "pair plus");
        this.updateBetAmount(player1, player1PlayBet, "play");

        // sets all card's images to the back side
        this.setCardImage(this.player1Card1, this.cardBackFileName);
        this.setCardImage(this.player1Card2, this.cardBackFileName);
        this.setCardImage(this.player1Card3, this.cardBackFileName);

        // event handlers for bet buttons

        // ante bet
        this.player1IncreaseAnteBet.setOnAction(event -> {
            try {
                this.increaseBet(this.player1, this.player1AnteBet, "ante", this.player1IncreaseAnteBet, this.player1DecreaseAnteBet, this.player1ConfirmBets);
            }
            catch (Exception e) {
                System.err.println("player 1 ante bet update error!");
            }
        });
        this.player1DecreaseAnteBet.setOnAction(event -> {
            try {
                this.decreaseBet(this.player1, this.player1AnteBet, "ante", this.player1IncreaseAnteBet, this.player1DecreaseAnteBet);
            }
            catch (Exception e) {
                System.err.println("player 1 ante bet update error!");
            }
        });

        // pair plus bet
        this.player1IncreasePairPlusBet.setOnAction(event -> {
            try {
                this.increaseBet(this.player1, this.player1PairPlusBet, "pair plus", this.player1IncreasePairPlusBet, this.player1DecreasePairPlusBet, this.player1ConfirmBets);
            }
            catch (Exception e) {
                System.err.println("player 1 pair plus bet update error!");
            }
        });
        this.player1DecreasePairPlusBet.setOnAction(event -> {
            try {
                this.decreaseBet(this.player1, this.player1PairPlusBet, "pair plus", this.player1IncreasePairPlusBet, this.player1DecreasePairPlusBet);
            }
            catch (Exception e) {
                System.err.println("player 1 pair plus bet update error!");
            }
        });

        // play bet
        this.player1IncreasePlayBet.setOnAction(event -> {
            try {
                this.increaseBet(this.player1, this.player1PlayBet, "play", this.player1IncreasePlayBet, null, this.player1ConfirmBets);
                this.player1Fold.setDisable(true); // no longer able to fold after increasing play bet

                // checks if game should complete
                // (both players have folded or added a play bet)
                if (this.shouldDealerFlip()) {
                    this.completeGame();
                }
            }
            catch (Exception e) {
                System.err.println("player 1 play bet update error!");
            }
        });

        // confirm bets
        this.player1ConfirmBets.setOnAction(event -> {
            try {
                this.confirmBets(this.player1, this.player1BetButtons);
            }
            catch (Exception e) {
                System.err.println("player 1 confirm bets error!");
            }
        });

        // fold
        this.player1Fold.setOnAction(event -> {
            try {
                this.fold(this.player1, this.player1BetButtons, this.player1Cards);

                // checks if game should complete
                // (both players have folded or added a play bet)
                if (this.shouldDealerFlip()) {
                    this.completeGame();
                }
            }
            catch (Exception e) {
                System.err.println("player 1 fold error!");
            }
        });
    }


    // calls all functions needed to initialize player2
    // lots of parameters would be needed to make a generic function
    // for both players, so i made an initialize function for each player
    private void initializePlayer2() {
        // create player1Cards so that images can be updated after flipping
        this.player2Cards = new ArrayList<ImageView>(3);

        this.player2Cards.add(this.player2Card1);
        this.player2Cards.add(this.player2Card2);
        this.player2Cards.add(this.player2Card3);

        // puts all bet buttons in a list for easy disabling (confirm/fold)
        this.player2BetButtons = new ArrayList<Button>();

        this.player2BetButtons.add(this.player2IncreaseAnteBet);
        this.player2BetButtons.add(this.player2DecreaseAnteBet);
        this.player2BetButtons.add(this.player2IncreasePairPlusBet);
        this.player2BetButtons.add(this.player2DecreasePairPlusBet);
        this.player2BetButtons.add(this.player2IncreasePlayBet);
        this.player2BetButtons.add(this.player2ConfirmBets);
        this.player2BetButtons.add(this.player2Fold);

        // sets all buttons to 'active'
        // mainly for resetGame()
        for (int i = 0; i < player2BetButtons.size(); i++) {
            player2BetButtons.get(i).setDisable(false);
        }

        // can only place a play bet after you have seen your cards
        this.player2IncreasePlayBet.setDisable(true);

        // can't decrease at starting bet value
        this.player2DecreaseAnteBet.setDisable(true);
        this.player2DecreasePairPlusBet.setDisable(true);

        // can't fold at the very start
        this.player2Fold.setDisable(true);

        // updates bet display
        this.updateBetAmount(player2, player2AnteBet, "ante");
        this.updateBetAmount(player2, player2PairPlusBet, "pair plus");
        this.updateBetAmount(player2, player2PlayBet, "play");

        // sets all card's images to the back side
        this.setCardImage(this.player2Card1, this.cardBackFileName);
        this.setCardImage(this.player2Card2, this.cardBackFileName);
        this.setCardImage(this.player2Card3, this.cardBackFileName);

        // event handlers for bet buttons

        // ante bet
        this.player2IncreaseAnteBet.setOnAction(event -> {
            try {
                this.increaseBet(this.player2, this.player2AnteBet, "ante", this.player2IncreaseAnteBet, this.player2DecreaseAnteBet, this.player2ConfirmBets);
            }
            catch (Exception e) {
                System.err.println("player 2 ante bet update error!");
            }
        });
        this.player2DecreaseAnteBet.setOnAction(event -> {
            try {
                this.decreaseBet(this.player2, this.player2AnteBet, "ante", this.player2IncreaseAnteBet, this.player2DecreaseAnteBet);
            }
            catch (Exception e) {
                System.err.println("player 2 ante bet update error!");
            }
        });

        // pair plus bet
        this.player2IncreasePairPlusBet.setOnAction(event -> {
            try {
                this.increaseBet(this.player2, this.player2PairPlusBet, "pair plus", this.player2IncreasePairPlusBet, this.player2DecreasePairPlusBet, this.player2ConfirmBets);
            }
            catch (Exception e) {
                System.err.println("player 2 pair plus bet update error!");
            }
        });
        this.player2DecreasePairPlusBet.setOnAction(event -> {
            try {
                this.decreaseBet(this.player2, this.player2PairPlusBet, "pair plus", this.player2IncreasePairPlusBet, this.player2DecreasePairPlusBet);
            }
            catch (Exception e) {
                System.err.println("player 2 pair plus bet update error!");
            }
        });

        // play bet
        this.player2IncreasePlayBet.setOnAction(event -> {
            try {
                this.increaseBet(this.player2, this.player2PlayBet, "play", this.player2IncreasePlayBet, null, this.player2ConfirmBets);
                this.player2Fold.setDisable(true); // no longer able to fold after increasing play bet

                // checks if game should complete
                // (both players have folded or added a play bet)
                if (this.shouldDealerFlip()) {
                    this.completeGame();
                }
            }
            catch (Exception e) {
                System.err.println("player 2 play bet update error!");
            }
        });

        // confirm bets
        this.player2ConfirmBets.setOnAction(event -> {
            try {
                this.confirmBets(this.player2, this.player2BetButtons);
            }
            catch (Exception e) {
                System.err.println("player 2 confirm bets error!");
            }
        });

        // fold
        this.player2Fold.setOnAction(event -> {
            try {
                this.fold(this.player2, this.player2BetButtons, this.player2Cards);

                // checks if game should complete
                // (both players have folded or added a play bet)
                if (this.shouldDealerFlip()) {
                    this.completeGame();
                }
            }
            catch (Exception e) {
                System.err.println("player 1 fold error!");
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.initializeMenu();
            this.resetGame(true);
        }
        catch (Exception e) {
            System.err.println("error beginning game!");
        }
    }
}
