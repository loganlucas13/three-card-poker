import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
    private Label ipDisplay;
    @FXML
    private Label portDisplay;

    @FXML
    private Label playerWinnings;

    @FXML
    private Label playerHandType;
    @FXML
    private Label dealerHandType;

    @FXML
    private VBox playerButtonBox;

    @FXML
    private Label playerAnteBet;
    @FXML
    private Label playerPlayBet;
    @FXML
    private Label playerPairPlusBet;

    @FXML
    private Button playerIncreaseAnteBet;
    @FXML
    private Button playerDecreaseAnteBet;
    @FXML
    private Button playerIncreasePairPlusBet;
    @FXML
    private Button playerDecreasePairPlusBet;
    @FXML
    private Button playerIncreasePlayBet;
    @FXML
    private Button playerConfirmBets;
    @FXML
    private Button playerFold;

    @FXML
    private ImageView dealerCard1;
    @FXML
    private ImageView dealerCard2;
    @FXML
    private ImageView dealerCard3;

    @FXML
    private ImageView playerCard1;
    @FXML
    private ImageView playerCard2;
    @FXML
    private ImageView playerCard3;

    // data members
    // (for game functionality)

    private ArrayList<Button> playerBetButtons;

    private ArrayList<ImageView> dealerCards;
    private ArrayList<ImageView> playerCards;

    private String cardBackFileName;

    private PokerInfo gameInstance = PokerInfoSingleton.getInstance();

    Client client;

    // constructor
    public GameController() {
        this.cardBackFileName = "/images/deck-of-cards/backs/Back2.png";
        this.client = new Client();
        this.client.start();
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
        ArrayList<String> filenames = new ArrayList<>();
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

        if (!this.gameInstance.getShowPlayer()) {
            // if both players have not confirmed, update card image to the backing (cards haven't been flipped yet)
            this.setAllCardImages();
        }
        else if (!this.gameInstance.getShowDealer()) {
            // if the dealer has not flipped their cards, update the backing of the cards
            for (ImageView card : this.dealerCards) {
                this.setCardImage(card, this.cardBackFileName);
            }
        }
    }


    // resets game
    // can be used to either reset the game completely or move onto the next round
    // 'willResetWinnings' should be false when continuing the game, true otherwise
    private void resetGame(boolean willResetWinnings) throws Exception {
        int savedWinnings = this.gameInstance.getTotalWinnings();

        // saves ante bet if dealer did not qualify
        int savedAnte = this.gameInstance.getAnte();
        boolean shouldSaveAnte = this.gameInstance.getWillPushAnte();

        PokerInfoSingleton.resetGameInstance();
        this.gameInstance = PokerInfoSingleton.getInstance();

        // initialize card displays
        this.initializeDealer();
        this.initializePlayer();

        if (shouldSaveAnte) {
            this.gameInstance.setAnte(savedAnte);
            this.updateBetAmount(playerAnteBet, "ante");
            this.playerIncreaseAnteBet.setDisable(true);
            this.playerDecreaseAnteBet.setDisable(true);
        }

        // saves winnings from previous rounds in new gameInstance
        if (!willResetWinnings) {
            this.gameInstance.setTotalWinnings(savedWinnings);
        }

        // set box in the top right
        this.initializeWinnings();

        // set text to default values
        this.dealerHandType.setText("HAND TYPE: ???");
        this.playerHandType.setText("HAND TYPE: ???");
    }


    // event handler for all increase bet buttons
    // increases ante and pair plus bets by 1 OR sets play bet equal to ante bet
    // if ante or pair plus bet == 0, increases by 5 (min $5 wager)
    // if ante or pair plus bet is >= 25, they are not updated (max $25 wager)
    // valid betType parameters: "ante", "pair plus", "play"
    private void increaseBet(Label betDisplay, String betType) throws Exception {
        if (betType.equals("ante")) {
            if (this.gameInstance.getAnte() >= 25) {
                this.playerIncreaseAnteBet.setDisable(true);
                return;
            }
            else if (this.gameInstance.getAnte() == 24) {
                this.gameInstance.setAnte(this.gameInstance.getAnte()+1);
                this.playerIncreaseAnteBet.setDisable(true);
            }
            else {
                this.gameInstance.setAnte(this.gameInstance.getAnte()+1);
                this.playerDecreaseAnteBet.setDisable(false);
            }
        }
        else if (betType.equals("pair plus")) {
            if (this.gameInstance.getPairPlus() >= 25) {
                this.playerIncreasePairPlusBet.setDisable(true);
                return;
            }
            else if (this.gameInstance.getPairPlus() == 24) {
                this.gameInstance.setPairPlus(this.gameInstance.getPairPlus()+1);
                this.playerIncreasePairPlusBet.setDisable(true);
            }
            else if (this.gameInstance.getPairPlus() == 0) {
                this.gameInstance.setPairPlus(this.gameInstance.getPairPlus()+5);
                this.playerDecreasePairPlusBet.setDisable(false);
            }
            else {
                this.gameInstance.setPairPlus(this.gameInstance.getPairPlus()+1);
                this.playerDecreasePairPlusBet.setDisable(false);
            }
        }
        else if (betType.equals("play")) {
            this.gameInstance.setPlay(this.gameInstance.getAnte()); // play bet must be equal to ante bet
            this.playerIncreasePlayBet.setDisable(true);
        }
        else {
            System.err.println("error increasing bet amount'");
            return;
        }
        this.updateBetAmount(betDisplay, betType);
    }


    // event handler for all decrease bet buttons
    // decreases ante and pair plus bets by 1
    // if ante <= 5, it is not updated (min $5 wager)
    // if pair plus <= 0, it is not updated (no negative bets)
    // valid betType parameters: "ante", "pair plus"
    private void decreaseBet(Label betDisplay, String betType) throws Exception {
        if (betType.equals("ante")) {
            if (this.gameInstance.getAnte() == 25) {
                this.playerIncreaseAnteBet.setDisable(false);
            }
            else if (this.gameInstance.getAnte() <= 6) {
                this.playerDecreaseAnteBet.setDisable(true);
            }
            this.gameInstance.setAnte(this.gameInstance.getAnte()-1);
        }
        else if (betType.equals("pair plus")) {
            if (this.gameInstance.getPairPlus() == 25) {
                this.gameInstance.setPairPlus(this.gameInstance.getPairPlus()-1);
                this.playerIncreasePairPlusBet.setDisable(false);
            }
            else if (this.gameInstance.getPairPlus() == 5) {
                this.gameInstance.setPairPlus(this.gameInstance.getPairPlus()-5);
                this.playerDecreasePairPlusBet.setDisable(true);
            }
            else {
                this.gameInstance.setPairPlus(this.gameInstance.getPairPlus()-1);
            }
        }
        else {
            System.err.println("error decreasing bet amount'");
            return;
        }
        this.updateBetAmount(betDisplay, betType);
    }


    // changes the text color of the 'playerWinnings' label based on the player's totalWinnings data member
    // > 0 - green
    // < 0 - red
    // 0 - black (default)
    private void changeWinningsTextColor() {
        if (this.gameInstance.getTotalWinnings() > 0) {
            this.playerWinnings.getStyleClass().removeAll("negative", "zero");
            this.playerWinnings.getStyleClass().add("positive");
        }
        else if (this.gameInstance.getTotalWinnings() < 0) {
            this.playerWinnings.getStyleClass().removeAll("positive", "zero");
            this.playerWinnings.getStyleClass().add("negative");
        }
        else {
            this.playerWinnings.getStyleClass().removeAll("positive", "negative");
            this.playerWinnings.getStyleClass().add("zero");
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
        ArrayList<ImageView> cards = new ArrayList<>();

        cards.add(dealerCard1);
        cards.add(dealerCard2);
        cards.add(dealerCard3);
        cards.add(playerCard1);
        cards.add(playerCard2);
        cards.add(playerCard3);

        for (int i = 0; i < 6; i++) {
            this.setCardImage(cards.get(i), cardBackFileName);
        }
    }


    // updates bet display on user interface
    // should be called after every increase/decrease of bet amount
    // valid betType parameters: "ante", "pair plus", "play"
    private void updateBetAmount(Label betDisplay, String betType) {
        if (betType.equals("ante")) {
            betDisplay.setText("$" + this.gameInstance.getAnte());
        }
        else if (betType.equals("pair plus")) {
            betDisplay.setText("$" + this.gameInstance.getPairPlus());
        }
        else if (betType.equals("play")) {
            betDisplay.setText("$" + this.gameInstance.getPlay());
        }
        else {
            System.err.println("invalid 'betType' parameter in 'updateBetAmount()'");
        }
    }


    // confirms all bets and disables betting buttons for a single player
    // also checks if both players have confirmed; if they have, then the game begins
    private void confirmBets(ArrayList<Button> betButtons) {
        // under minimum ante bet
        if (this.gameInstance.getAnte() < 5) {
            return;
        }

        for (int i = 0; i < betButtons.size(); i++) {
            betButtons.get(i).setDisable(true);
        }

        this.gameInstance.setShowPlayer(true);
        this.startGame();
    }


    // starts the game, holds all function calls needed for the game to function
    private void startGame() {
        this.playerFold.setDisable(false);

        this.playerIncreasePlayBet.setDisable(false);

        // distribute cards
        this.dealCards();

        try {
            this.flipCards(this.playerCards);
        } catch (Exception e) {
            System.err.println("error while flipping player cards");
        }
    }


    // flips cards for either a player or dealer, depending on input
    // if flipping for the dealer, set player == null
    // if flipping for a player, set dealer == null
    private void flipCards(ArrayList<ImageView> cards) throws Exception {
        ArrayList<Card> hand;

        if (this.gameInstance.getShowDealer()) {
            hand = this.gameInstance.getDealerHand();
            this.dealerHandType.setText("HAND TYPE: " + this.gameInstance.getDealerHandString());
        }
        else if (this.gameInstance.getShowPlayer()) {
            hand = this.gameInstance.getPlayerHand();
            this.playerHandType.setText("HAND TYPE: " + this.gameInstance.getPlayerHandString());
        }
        else {
            throw new Exception("attempting to flip cards when neither player or dealer should be shown");
        }

        for (int i = 0; i < hand.size(); i++) {
            String filename = this.getCardFilename(hand.get(i));
            this.setCardImage(cards.get(i), filename);
        }
    }


    // handles all events after both players have made their choice to add a play bet or fold
    // flips dealer cards, evaluates hands, and resets to begin game again
    private void completeGame() {

        // checks if dealer qualifies
        this.gameInstance.setRequest("CHECK_DEALER_QUALIFICATION");
        this.client.send(this.gameInstance);

        try {
            Object response = this.client.read();
            if (response instanceof PokerInfo) {
                this.gameInstance = (PokerInfo) response;
            }
        }
        catch (Exception e) {
            System.err.println("error updating gameInstance in GameController.completeGame() dealer qualification");
        }

        try {
            this.flipCards(this.dealerCards);
        } catch (Exception e) {
            System.err.println("error while flipping dealer cards");
        }

        if (gameInstance.getRequest().equals("DEALER DOES NOT QUALIFY")) {
            this.dealerHandType.setText("HAND TYPE: DID NOT QUALIFY");
            // keep ante and continue to next game
        }

        // evaluate winnings
        this.gameInstance.setRequest("EVALUATE_WINNINGS");
        this.client.send(this.gameInstance);

        try {
            Object response = this.client.read();
            if (response instanceof PokerInfo) {
                this.gameInstance = (PokerInfo) response;
            }
        }
        catch (Exception e) {
            System.err.println("error updating gameInstance in GameController.completeGame() winnings evaluation");
        }

        this.initializeWinnings();

        // get user winning results for popup
        this.gameInstance.setRequest("GET_RESULT");
        this.client.send(this.gameInstance);

        try {
            Object response = this.client.read();
            if (response instanceof PokerInfo) {
                this.gameInstance = (PokerInfo) response;
            }
        }
        catch (Exception e) {
            System.err.println("error updating gameInstance in GameController.completeGame() result gathering");
        }

        this.displayWinningPopup();
    }


    // restarts the game using the non-overloaded resetGame function (adding in main)
    private void startAgain() {
    	try {
            this.resetGame(false);
        }
        catch (Exception e) {
            System.err.println("resetGame() error!");
        }
    }


    // displays popups that show up after the game has ended
    private void displayWinningPopup() {
        // based off of the controls for each player
        Stage stage1 = (Stage)this.playerButtonBox.getScene().getWindow();

        Popup playerPopup = this.createPopup();

        // to align the popup with the betting buttons
        Bounds playerBounds = this.playerButtonBox.localToScreen(this.playerButtonBox.getBoundsInLocal());

        playerPopup.show(stage1, playerBounds.getMinX()+2, playerBounds.getMinY()+2);

        // pauses the game to allow the player to read popup text
        PauseTransition pause = new PauseTransition(Duration.seconds(8));

    	pause.setOnFinished(event -> {
            playerPopup.hide();
            startAgain();
        });
    	pause.play();
    }


    // constructs a popup with the desired text and returns it
    // mainly used as a helper function for this.displayWinningPopup()
    private Popup createPopup() {
        Popup popup = new Popup();

        Label popupLabel = new Label(this.gameInstance.getResult());
        popupLabel.getStylesheets().add(getClass().getResource("styles/popup.css").toExternalForm());

        popup.getContent().add(popupLabel);

        return popup;
    }


    // deals cards out to the dealer and both players
    // sets 'dealersHand' data member and 'hand' data members
    private void dealCards() {
        this.gameInstance.setRequest("DEAL_CARDS");
        this.client.send(this.gameInstance);

        try {
            Object response = this.client.read();
            if (response instanceof PokerInfo) {
                this.gameInstance = (PokerInfo) response;
            }
        }
        catch (Exception e) {
            System.err.println("error updating gameInstance in GameController.dealCards()\n");
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
    private void fold(ArrayList<Button> betButtons, ArrayList<ImageView> cards) {
        this.gameInstance.setHasFolded(true);

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

    // initializes the winnings display in the top right for a single player
    private void initializeWinnings() {
        this.playerWinnings.setText("$" + this.gameInstance.getTotalWinnings());
        changeWinningsTextColor();
    }


    // initializes the ip address and port labels in the top left
    private void initializeNetworkDisplay() {
        this.ipDisplay.setText(this.gameInstance.getIp());
        this.portDisplay.setText(Integer.toString(this.gameInstance.getPort()));
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
        this.dealerCards = new ArrayList<>(3);

        this.dealerCards.add(this.dealerCard1);
        this.dealerCards.add(this.dealerCard2);
        this.dealerCards.add(this.dealerCard3);

        // sets all card's images to the back side
        this.setCardImage(this.dealerCard1, this.cardBackFileName);
        this.setCardImage(this.dealerCard2, this.cardBackFileName);
        this.setCardImage(this.dealerCard3, this.cardBackFileName);
    }


    // calls all functions needed to initialize player
    // lots of parameters would be needed to make a generic function
    // for both players, so i made an initialize function for each player
    private void initializePlayer() {

        // reset bets to default values
        this.gameInstance.setAnte(5);
        this.gameInstance.setPairPlus(0);
        this.gameInstance.setPlay(0);

        // create playerCards so that images can be updated after flipping
        this.playerCards = new ArrayList<>(3);

        this.playerCards.add(this.playerCard1);
        this.playerCards.add(this.playerCard2);
        this.playerCards.add(this.playerCard3);

        // puts all bet buttons in a list for easy disabling (confirm/fold)
        this.playerBetButtons = new ArrayList<>();

        this.playerBetButtons.add(this.playerIncreaseAnteBet);
        this.playerBetButtons.add(this.playerDecreaseAnteBet);
        this.playerBetButtons.add(this.playerIncreasePairPlusBet);
        this.playerBetButtons.add(this.playerDecreasePairPlusBet);
        this.playerBetButtons.add(this.playerIncreasePlayBet);
        this.playerBetButtons.add(this.playerConfirmBets);
        this.playerBetButtons.add(this.playerFold);

        // sets all buttons to 'active'
        // mainly for resetGame()
        for (int i = 0; i < playerBetButtons.size(); i++) {
            playerBetButtons.get(i).setDisable(false);
        }

        // can only place a play bet after you have seen your cards
        this.playerIncreasePlayBet.setDisable(true);

        // can't decrease at starting bet value
        this.playerDecreaseAnteBet.setDisable(true);
        this.playerDecreasePairPlusBet.setDisable(true);

        // can't fold at the very start
        this.playerFold.setDisable(true);

        // updates bet display
        this.updateBetAmount(playerAnteBet, "ante");
        this.updateBetAmount(playerPairPlusBet, "pair plus");
        this.updateBetAmount(playerPlayBet, "play");

        // sets all card's images to the back side
        this.setCardImage(this.playerCard1, this.cardBackFileName);
        this.setCardImage(this.playerCard2, this.cardBackFileName);
        this.setCardImage(this.playerCard3, this.cardBackFileName);

        // event handlers for bet buttons

        // ante bet
        this.playerIncreaseAnteBet.setOnAction(event -> {
            try {
                this.increaseBet(this.playerAnteBet, "ante");
            }
            catch (Exception e) {
                System.err.println("player 1 ante bet update error!");
            }
        });
        this.playerDecreaseAnteBet.setOnAction(event -> {
            try {
                this.decreaseBet(this.playerAnteBet, "ante");
            }
            catch (Exception e) {
                System.err.println("player 1 ante bet update error!");
            }
        });

        // pair plus bet
        this.playerIncreasePairPlusBet.setOnAction(event -> {
            try {
                this.increaseBet(this.playerPairPlusBet, "pair plus");
            }
            catch (Exception e) {
                System.err.println("player 1 pair plus bet update error!");
            }
        });
        this.playerDecreasePairPlusBet.setOnAction(event -> {
            try {
                this.decreaseBet(this.playerPairPlusBet, "pair plus");
            }
            catch (Exception e) {
                System.err.println("player 1 pair plus bet update error!");
            }
        });

        // play bet
        this.playerIncreasePlayBet.setOnAction(event -> {
            try {
                this.increaseBet(this.playerPlayBet, "play");
                this.playerFold.setDisable(true); // no longer able to fold after increasing play bet

                // completes game
                this.completeGame();
            }
            catch (Exception e) {
                System.err.println("player 1 play bet update error!");
            }
        });

        // confirm bets
        this.playerConfirmBets.setOnAction(event -> {
            try {
                this.confirmBets(this.playerBetButtons);
            }
            catch (Exception e) {
                System.err.println("player 1 confirm bets error!");
            }
        });

        // fold
        this.playerFold.setOnAction(event -> {
            try {
                this.fold(this.playerBetButtons, this.playerCards);

                // completes game
                this.completeGame();
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
            this.initializeNetworkDisplay();
            this.resetGame(true);
        }
        catch (Exception e) {
            System.err.println("error beginning game!");
        }
    }
}
