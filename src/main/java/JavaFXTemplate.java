import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Logan Lucas and Jonathan Kang
// netid: lluca5
// netid: jkang87

public class JavaFXTemplate extends Application {

	// data members

	// these are redefined in GameController.java and actually used there
	// these three are only here to fit the project pdf description
	// this should be allowed based on Piazza post @389, which says
	// "You can define the Dealer and Player's in your controller if that is what you are using to really drive your game,
	// instead of having them be in the JavaFXTemplate but not using them."
	// i put these here to be extra safe, i'm sorry for the confusion!
	Player playerOne = new Player();
	Player playerTwo = new Player();
	Dealer theDealer = new Dealer();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("FXML/menu.fxml"));

		Scene scene = new Scene(root, 1280, 1024);
		scene.setFill(null); // so scene background doesn't show when switching scenes

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
