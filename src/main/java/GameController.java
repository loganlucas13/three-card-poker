import java.net.URL;
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
    private Label player2AnteBet;
    @FXML
    private Label player2PlayBet;
    @FXML
    private Label player2PairPlusBet;

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
            playerWinnings.getStyleClass().removeAll();
        }
    }


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dealer = new Dealer();

        this.player1 = new Player();
        this.player2 = new Player();

        this.player1Winnings.setText("$" + this.player1.getTotalWinnings());
        this.changeWinningsTextColor(player1, player1Winnings);

        this.player1AnteBet.setText("$" + this.player1.getAnteBet());
        this.player1PlayBet.setText("$" + this.player1.getPlayBet());
        this.player1PairPlusBet.setText("$" + this.player1.getPairPlusBet());

        this.player2Winnings.setText("$" + this.player2.getTotalWinnings());
        this.changeWinningsTextColor(player2, player2Winnings);

        this.player2AnteBet.setText("$" + this.player2.getAnteBet());
        this.player2PlayBet.setText("$" + this.player2.getPlayBet());
        this.player2PairPlusBet.setText("$" + this.player2.getPairPlusBet());

        this.setCardImage(this.dealerCard1, "/images/deck-of-cards/Back2.png");
        this.setCardImage(this.dealerCard2, "/images/deck-of-cards/Back2.png");
        this.setCardImage(this.dealerCard3, "/images/deck-of-cards/Back2.png");

        this.setCardImage(this.player1Card1, "/images/deck-of-cards/Back2.png");
        this.setCardImage(this.player1Card2, "/images/deck-of-cards/Back2.png");
        this.setCardImage(this.player1Card3, "/images/deck-of-cards/Back2.png");

        this.setCardImage(this.player2Card1, "/images/deck-of-cards/Back2.png");
        this.setCardImage(this.player2Card2, "/images/deck-of-cards/Back2.png");
        this.setCardImage(this.player2Card3, "/images/deck-of-cards/Back2.png");


        this.exitButton.setOnAction(event -> {
            try {
                this.quitGame();
            }
            catch (Exception e) {
                System.err.println("quitGame() error!");
            }
        });
    }
}
