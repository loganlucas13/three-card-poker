
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

public class StartController implements Initializable {
    @FXML
    private Button startServerButton;

    @FXML
    private TextField portField;

    // begins server at specified port
    void startServer(int port) throws Exception {
        Server server = new Server();
        ServerInfo.setServer(server);
    }

    // switches scene to main server display
    void goToServerScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/server.fxml"));
        Parent root = loader.load();
        ServerInfo.setStartController(this);
        ServerInfo.setServerController(loader.getController());
        Scene scene = new Scene(root, 1280, 1024);
        Stage primaryStage = (Stage)this.startServerButton.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // initializes all event handlers
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.startServerButton.setDisable(true);

        // enables start server button once user enters into the text field
        this.portField.setOnAction(event -> {
            this.startServerButton.setDisable(false);
        });

        // starts server at port and switches scene
        this.startServerButton.setOnAction(event -> {
            try {
                int port = Integer.parseInt(this.portField.getText());
                ServerInfo.setPort(port);

                this.startServer(port);
            }
            catch (Exception e) {
                System.err.println("startServer() error!");
            }

            try {
                this.goToServerScene();
            }
            catch (Exception e) {
                System.err.println("goToServerScene() error!");
            }
        });
    }
}
