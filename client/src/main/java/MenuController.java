import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    // data members

    @FXML
    private Button playButton;
    @FXML
    private Button rulesButton;
    @FXML
    private Button quitButton;

    @FXML
    private Button confirmIp;
    @FXML
    private Button confirmPort;

    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;

    // stores the instance of the game for modifications
    private final PokerInfo gameInstance = PokerInfoSingleton.getInstance();


    // event for playButton
    // takes user to the gameplay screen
    private void startGame() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/game.fxml"));
        Scene scene = new Scene(root, 1250, 1000);
        Stage primaryStage = (Stage)this.playButton.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // event for rulesButton
    // takes user to new scene with rules
    private void displayRules() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/rules.fxml"));
        Scene scene = new Scene(root, 1250, 1000);
        Stage primaryStage = (Stage)this.rulesButton.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // event for quitButton
    // closes game
    private void quitGame() throws Exception {
        Stage primaryStage = (Stage)this.quitButton.getScene().getWindow();

        primaryStage.close();
    }


    // event for ipField
    // updates the ip address of the game
    private void updateIp(String ip) throws Exception {
        this.gameInstance.setIp(ip);

        if (!(this.gameInstance.getIp().equals("undefined") || this.gameInstance.getPort() == -1)) {
            this.playButton.setDisable(false);
        }
    }


    // event for portField
    // updates the port of the game
    private void updatePort(int port) throws Exception {
        this.gameInstance.setPort(port);

        if (!(this.gameInstance.getIp().equals("undefined") || this.gameInstance.getPort() == -1)) {
            this.playButton.setDisable(false);
        }
    }


    // fills text fields for ip and port and allows user to start game
    private void enablePlay() {
        this.ipField.setText(this.gameInstance.getIp());
        this.portField.setText(Integer.toString(this.gameInstance.getPort()));
        this.playButton.setDisable(false);
    }

    // initializes all event handlers and does checks
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.playButton.setDisable(true);

        // saves information if the user already input their ip and port and then switched to the rules tab
        if (this.gameInstance.getIp().equals("127.0.0.1") && this.gameInstance.getPort() != -1) {
            this.enablePlay();
        }

        // event handler to start the game
        this.playButton.setOnAction(event -> {
            try {
                this.startGame();
            }
            catch (Exception e) {
                System.err.println("startGame() error!\n");
            }
        });

        // event handler to display rules
        this.rulesButton.setOnAction(event -> {
            try {
                this.displayRules();
            }
            catch (Exception e) {
                System.err.println("displayRules() error!\n");
            }
        });

        // event handler to exit game
        this.quitButton.setOnAction(event -> {
            try {
                this.quitGame();
            }
            catch (Exception e) {
                System.err.println("quitGame() error!\n");
            }
        });

        // event handler to get ip from text field
        this.confirmIp.setOnAction(event -> {
            try {
                this.updateIp(this.ipField.getText());
            }
            catch (Exception e) {
                System.err.println("updateIp() error!\n");
            }
        });

        // event handler to get port from text field
        this.confirmPort.setOnAction(event -> {
            try {
                int port = Integer.parseInt(this.portField.getText()); // casts user text as integer
                this.updatePort(port);
            }
            catch (Exception e) {
                System.err.println("updatePort() error!\n");
            }
        });
    }
}