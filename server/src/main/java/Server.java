import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;


public class Server {

	int count = 0;
	ArrayList<ClientThread> clients = new ArrayList<>();
	TheServer server;

	synchronized int increase() {
	    int current = count;
	    count++;
	    return current;
	}

	Server() {
		this.server = new TheServer();
		this.server.start();
	}


	public class TheServer extends Thread {
		public void run() {
			try (ServerSocket socket = new ServerSocket(ServerInfo.getPort());) {
				System.out.println("Server is waiting for a client!");


				while (true) {
					ClientThread c = new ClientThread(socket.accept(), increase());
					Platform.runLater(() -> {
						ServerInfo.getServerController().getClientCountLabel().setText(Integer.toString(count));
					});
					System.out.println("client has connected to server: " + "client #" + count);
					synchronized(clients) {
						clients.add(c);
					}
					c.start();
				}
			}//end of try
			catch(Exception e) {
				System.err.println("server did not launch");
				System.err.println(e);
			}
		}//end of while
	}


	class ClientThread extends Thread {
		Socket connection;
		int count;
		ObjectInputStream in;
		ObjectOutputStream out;

		ClientThread(Socket s, int count) {
			this.connection = s;
			this.count = count;
		}

		public synchronized void updateClients(String message) {
			for(int i = 0; i < clients.size(); i++) {
				ClientThread t = clients.get(i);
				if (message.equals("Join")) {
					try {
						t.out.writeObject(message);
					}
					catch(Exception e) {}
				}
				else if (message.equals("Quit")) {
					try {

						t.out.writeObject(message);
					}
					catch(Exception e) {}
				}
				else if (message.equals("Fold")) {
					try {

						t.out.writeObject(message);
					}
					catch (Exception e) {}
				}
				else if (message.equals("Play")) {
					try {

						t.out.writeObject(message);
					}
					catch (Exception e) {}
				}
				else if (message.equals("PairPlus")) {
					try {

						t.out.writeObject(message);
					}
					catch (Exception e) {}
				}
				else if (message.equals("Ante")) {
					try {

						t.out.writeObject(message);
					}
					catch (Exception e) {}
				}
				else if(message.equals("FreshStart")) {
					try {

						t.out.writeObject(message);
					}
					catch (Exception e) {}
				}
			}
		}

		@Override
		public void run() {
			try {
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);
			}
			catch(Exception e) {
				System.out.println("stream not open");
			}

			updateClients("new client on server: client #" + count);

			Dealer dealer = new Dealer();
			Player player = new Player();

			while(true) {
				try {
					PokerInfo gameInstance = (PokerInfo) in.readObject();
					String request = gameInstance.getRequest();

					updateClients("client #" + count + " said: " + request);

					if (request.equals("DEAL_CARDS")) {
						System.out.println("dealing cards to client #" + count);
						player.setHand(dealer.dealHand());
						player.setAnteBet(gameInstance.getAnte());
						player.setPlayBet(gameInstance.getPlay());
						player.setPairPlusBet(gameInstance.getPairPlus());
						player.setTotalWinnings(gameInstance.getTotalWinnings());
						player.setHasConfirmed(false);
						player.setHasFolded(false);
						player.setKeepAnte(false);

						dealer.setDealersHand(dealer.dealHand());
						dealer.setHasFlipped(false);

						gameInstance.setPlayerHand(player.getHand());
						gameInstance.setPlayerHandString(player.handToString());
						gameInstance.setDealerHand(dealer.getDealersHand());
						gameInstance.setDealerHandString(dealer.handToString());
						gameInstance.setWillPushAnte(false);
						gameInstance.setShowPlayer(true); // we want to show the player's cards after dealing

						try {
							out.writeObject(gameInstance);
						}
						catch (Exception e) {
							System.err.println("DEAL_PLAYER_CARDS response error");
						}
					}
					else if (request.equals("CHECK_DEALER_QUALIFICATION")) {
						System.out.println("checking dealer qualification for client #" + count);

						gameInstance.setShowDealer(true);

						// prepares response for client
						String result;
						if (!ThreeCardLogic.doesDealerQualify(dealer)) {
							result = "DEALER DOES NOT QUALIFY";
							dealer.setQualify(false);
							gameInstance.setWillPushAnte(true);
						}
						else {
							result = "DEALER DOES QUALIFY";
							dealer.setQualify(true);
							gameInstance.setWillPushAnte(false);
						}
						gameInstance.setRequest(result);

						try {
							out.writeObject(gameInstance);
						}
						catch (Exception e) {
							System.err.println("CHECK_DEALER_QUALIFICATION response error");
						}
					}
					else if (request.equals("EVALUATE_WINNINGS")) {
						System.out.println("evaluating winnings for client #" + count);

						player.setHasFolded(gameInstance.getHasFolded());

						if (gameInstance.getHasFolded()) {
							player.setTotalWinnings(player.getTotalWinnings() - player.getPairPlusBet() - player.getAnteBet());
							gameInstance.setWillPushAnte(false); // prevent user from pushing ante after folding
						}
						else {
							int gameResult = ThreeCardLogic.CompareHands(dealer.getDealersHand(), player.getHand());
							int pairPlusResult = ThreeCardLogic.evalPPWinnings(player.getHand(), player.getPairPlusBet());

							// if player lost the pair plus bet, subtract wager from total winnings
							if (pairPlusResult == 0) {
								pairPlusResult = player.getPairPlusBet() * -1;
							}

							if (!dealer.getQualify()) {
								player.setKeepAnte(true);
								player.setTotalWinnings(player.getTotalWinnings() + pairPlusResult);
							}
							else if (gameResult == 2) {
								// player wins
								player.setTotalWinnings(player.getTotalWinnings() + pairPlusResult + (2 * player.getAnteBet()));
							}
							else if (gameResult == 1) {
								// player loses
								player.setTotalWinnings(player.getTotalWinnings() + pairPlusResult - (2 * player.getAnteBet()));
							}
						}
						gameInstance.setTotalWinnings(player.getTotalWinnings());

						try {
							out.writeObject(gameInstance);
						}
						catch (Exception e) {
							System.err.println("EVALUATE_WINNINGS response error");
						}
					}
					else if (request.equals("GET_RESULT")) {
						System.out.println("gathering game-end results for client #" + count);

						gameInstance.setResult(player.resultToString(dealer));

						try {
							out.writeObject(gameInstance);
						}
						catch (Exception e) {
							System.err.println("GET_RESULT response error");
						}
					}
				}
				catch (Exception e) {
					System.out.println("OOOOPs...Something wrong with the socket from client: " + count + "....closing down!");
					updateClients("Client #" + count + " has left the server!");
					synchronized(clients) {
						clients.remove(this);
					}
					break;
				}
			}
		}//end of run
	}//end of client thread
}