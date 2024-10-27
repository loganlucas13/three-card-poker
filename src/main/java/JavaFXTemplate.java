import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

// Logan Lucas and Jonathan Kang
// netid: lluca5
// netid: jkang87

public class JavaFXTemplate extends Application {

	// data members
	Player playerOne;
	Player playerTwo;
	Dealer theDealer;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("FXML/menu.fxml"));

		Scene scene = new Scene(root, 1250, 1000);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
