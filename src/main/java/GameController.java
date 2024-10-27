import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

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

    private String cardBackFileName;


    // constructor
    public GameController() {
        this.dealer = new Dealer();

        this.player1 = new Player();
        this.player2 = new Player();

        this.cardBackFileName = "/images/deck-of-cards/Back2.png";
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
        String defaultPath = "/images/deck-of-cards/Back";

        for (int i = 1; i <= 8; i++) {
            filenames.add(defaultPath+i+".png");
        }

        int randomIndex = (int) (Math.random() * 8);
        // the while loop prevents the same backing from being picked twice in a row
        while (filenames.get(randomIndex).equals(this.cardBackFileName)) {
            randomIndex = (int) (Math.random() * 8);
        }

        this.cardBackFileName = filenames.get(randomIndex);

        this.setAllCardImages();
    }


    // event handler for fresh start button
    private void resetGame() throws Exception {
        this.dealer = new Dealer();

        this.initializeDealer();

        this.player1 = new Player();
        this.player2 = new Player();

        this.initializePlayer1();
        this.initializePlayer2();

        // winnings boxes in the top right
        this.initializeWinnings(player1, player1Winnings);
        this.initializeWinnings(player2, player2Winnings);
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
            else if (player.getAnteBet() == 0) {
                player.setAnteBet(player.getAnteBet()+5);
                confirmButton.setDisable(false);
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
            playerWinnings.getStyleClass().add("positive");
        }
        else if (player.getTotalWinnings() < 0) {
            playerWinnings.getStyleClass().add("negative");
        }
        else {
            playerWinnings.getStyleClass().removeAll(); // sets text to black
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
    // example filename: '/images/deck-of-cards/Back1.png'
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
    private void confirmBets(Player player, ArrayList<Button> betButtons) {
        // under minimum ante bet
        if (player.getAnteBet() < 5) {
            return;
        }

        player.setHasConfirmed(true);

        for (int i = 0; i < betButtons.size(); i++) {
            betButtons.get(i).setDisable(true);
        }
    }


    // folds hand and disables betting buttons for a single player
    private void fold(Player player, ArrayList<Button> betButtons) {
        player.setHasFolded(true);

        for (int i = 0; i < betButtons.size(); i++) {
            betButtons.get(i).setDisable(true);
        }
    }


    // initializes the winnings display in the top right for a single player
    private void initializeWinnings(Player player, Label playerWinnings) {
        playerWinnings.setText("$" + player.getTotalWinnings());
        changeWinningsTextColor(player, playerWinnings);
    }


    private void initializeMenu() {
        // event handler for the reset button found in the menu bar (top left)
        this.freshStartButton.setOnAction(event -> {
            try {
                this.resetGame();
            }
            catch (Exception e) {
                System.err.println("resetGame() error!");
            }
        });

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
        // sets all card's images to the back side
        this.setCardImage(this.dealerCard1, this.cardBackFileName);
        this.setCardImage(this.dealerCard2, this.cardBackFileName);
        this.setCardImage(this.dealerCard3, this.cardBackFileName);
    }


    // calls all functions needed to initialize player1
    // lots of parameters would be needed to make a generic function
    // for both players, so i made an initialize function for each player
    private void initializePlayer1() {
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

        // can't confirm bet or fold at the very start
        this.player1ConfirmBets.setDisable(true);
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
                this.fold(this.player1, this.player1BetButtons);
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

        // can't confirm bet or fold at the very start
        this.player2ConfirmBets.setDisable(true);
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
                this.fold(this.player2, this.player2BetButtons);
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
            this.resetGame();
        }
        catch (Exception e) {
            System.err.println("error beginning game!");
        }
    }
}
