import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController implements Initializable{

    // data members

    @FXML
    private Button playButton;

    @FXML
    private Button rulesButton;

    @FXML
    private Button quitButton;


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


    // initializes all event handlers
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    }
}