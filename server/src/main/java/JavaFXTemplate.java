import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Logan Lucas and Jonathan Kang
// netid: lluca5
// netid: jkang87

public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("FXML/start.fxml"));

		Scene scene = new Scene(root, 1280, 1024);
		scene.setFill(null); // so scene background doesn't show when switching scenes

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}