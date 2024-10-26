import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RulesController implements Initializable {

    // data members

    @FXML
    private Button menuButton;


    // event for menuButton
    // takes user back to the main menu
    private void goToMenu() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/menu.fxml"));
        Scene scene = new Scene(root, 1250, 1000);
        Stage primaryStage = (Stage)this.menuButton.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // initializes all event handlers
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // event handler to return to menu
        this.menuButton.setOnAction(event -> {
            try {
                this.goToMenu();
            }
            catch (Exception e) {
                System.err.println("goToMenu() error!\n");
            }
        });
    }
}
