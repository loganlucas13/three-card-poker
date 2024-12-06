import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client extends Thread{
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
			System.err.println("error in Client.run() socket creation");
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