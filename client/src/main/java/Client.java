import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;


public class Client extends Thread {
	Socket clientSocket;

	ObjectOutputStream out;
	ObjectInputStream in;

	@Override
	public void run() {
		// opens client socket
		try {
			String ip = PokerInfoSingleton.getInstance().getIp();
			int port = PokerInfoSingleton.getInstance().getPort();

			clientSocket = new Socket(ip, port);

			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());

			clientSocket.setTcpNoDelay(true);
		}
		catch (Exception e) {
			// creates popup if the client cannot open a socket
			System.err.println("ERROR: Unable to create client socket. Check the port number input and make sure that the server is running, then try again.");
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("UNABLE TO CREATE CLIENT SOCKET");
				alert.setHeaderText("CLIENT SOCKET CREATION FAILED.");
				alert.setContentText("CHECK THE PORT NUMBER YOU INPUT AND MAKE SURE THAT THE SERVER IS RUNNING, THEN TRY AGAIN.\n\nCLOSING THIS POPUP WILL TERMINATE THE PROGRAM");

				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add(getClass().getResource("styles/alert.css").toExternalForm());

				alert.showAndWait();
				Platform.exit();
			});
		}
    }

	public synchronized void send(Object data) {
		try {
			out.writeObject(data);
		} catch (IOException e) {
			System.err.println("error in Client.send()");
		}
	}

	public synchronized Object read() {
		try {
			return in.readObject();
		}
		catch (IOException e) {
			System.err.println("IOException in Client.read()");
		}
		catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException in Client.read()");
		}
		return null;
	}
}