
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ServerController implements Initializable {
    @FXML
    private ListView eventList;

    @FXML
    private Label portDisplay;
    @FXML
    private Label clientCount;

    @FXML
    private Button startServerButton;
    @FXML
    private Button closeServerButton;

    private final int port = ServerInfo.getPort();


    // starts the server at specified port
    void startServer(int port) throws Exception {
        // TODO: add starting server code
        ServerInfo.setIsRunning(true);
        this.startServerButton.setDisable(true);
        this.closeServerButton.setDisable(false);
    }


    // closes the server at specified port
    void closeServer(int port) throws Exception {
        // TODO: add closing server code
        ServerInfo.setIsRunning(false);
        this.startServerButton.setDisable(false);
        this.closeServerButton.setDisable(true);
    }


    // disables buttons that shouldn't be clicked and updates port label at top
    void initializeLabelsAndButtons() {
        this.portDisplay.setText(Integer.toString(this.port));
        this.startServerButton.setDisable(true);
        this.closeServerButton.setDisable(false);
    }


    // initializes all event handlers
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.eventList.getItems().add("PLAYER JOINED"); // TEMPORARY

        this.initializeLabelsAndButtons();

        // event handler to start server
        this.startServerButton.setOnAction(event -> {
            try {
                this.startServer(this.port);
            }
            catch (Exception e) {
                System.out.println("startServer() error!");
            }
        });

        // event handler to close server
        this.closeServerButton.setOnAction(event -> {
            try {
                this.closeServer(this.port);
            }
            catch (Exception e) {
                System.out.println("closeServer() error!");
            }
        });
    }
}
