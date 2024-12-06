import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;



public class Server {

	ArrayList<ClientThread> clients = new ArrayList<>();
	TheServer server;
	boolean isRunning = true;
	ServerSocket serverSocket;

	synchronized int getNextID() {
	    return clients.size() + 1;
	}

	public synchronized int getServerCount() {
	    return clients.size();
	}

	Server() {
		this.server = new TheServer();
		this.server.start();
	}


	// closes all current connections and prevents server from accepting new connections
    public void stopServer() {
        this.isRunning = false; // Stop the server loop
        if (server != null && !server.isInterrupted()) {
            server.interrupt(); // Interrupt the server thread
        }

		// closes server socket to prevent new connections from being made
		try {
			if (this.serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		}
		catch (Exception e) {
			System.err.println("Error while closing server socket");
		}


		// closes all current client connections
        synchronized (clients) {
        	for(ClientThread client : clients) {
        		try {
        			client.connection.close();
        		}
				catch (IOException e) {
        			System.err.println("Error closing clients" + e.getMessage());
        		}
        	}
        	clients.clear();
			Platform.runLater(() -> {
				ServerInfo.getServerController().getEventList().getItems().add("CLOSED ALL CURRENT CLIENT CONNECTIONS");
			});
        }
    }


	// restarts server completely, allowing new connections if server was previously closed
    public void restartServer() {
    	this.isRunning = true;

		this.server = new TheServer();
		this.server.start();
    }


	public class TheServer extends Thread {
		@Override
		public void run() {
			try (ServerSocket socket = new ServerSocket(ServerInfo.getPort());) {
				serverSocket = socket; // update member in 'Server' class

				// continuously accepts new connections and adds them to client list while server is running
                while (isRunning) {
                    try {
                        int clientID = getNextID();
                        ClientThread c = new ClientThread(socket.accept(), clientID);
                        synchronized (clients) {
                            clients.add(c);
                        }
                        Platform.runLater(() -> {
                            ServerInfo.getServerController().getClientCountLabel().setText(Integer.toString(getServerCount()));
                            ServerInfo.getServerController().getEventList().getItems().add("CLIENT HAS CONNECTED TO SERVER: CLIENT #" + clientID);
                        });
                        c.start();
                    } catch (IOException e) {
                        if (!isRunning) {
                            break;
                        }
						else {
							System.err.println("Error accepting new client connection");
                        }
					}
                }
                System.out.println("Server has shut down.");
            } catch (IOException e) {
                System.err.println("Error with ServerSocket: " + e.getMessage());
            } finally {
                Platform.runLater(() -> {
                    ServerInfo.getServerController().getEventList().getItems().add("SERVER CLOSED");
                });
            }
		}//end of while
	}


	class ClientThread extends Thread {
		Socket connection;
		int clientID;
		ObjectInputStream in;
		ObjectOutputStream out;

		ClientThread(Socket s, int clientID) {
			this.connection = s;
			this.clientID = clientID;
		}


		@Override
		public void run() {
			try {
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);
			}
			catch(Exception e) {
				System.err.println("Client thread streams not opened");
			}

			Dealer dealer = new Dealer();
			Player player = new Player();

			while(true) {
				try {
					PokerInfo gameInstance = (PokerInfo) in.readObject();
					String request = gameInstance.getRequest();

					if (request.equals("DEAL_CARDS")) {
						Platform.runLater(() -> {
							ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " IS STARTING THEIR GAME");
							ServerInfo.getServerController().getEventList().getItems().add("DEALING CARDS TO CLIENT #" + clientID);
						});
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

						Platform.runLater(() -> {
							ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " PLACED ANTE BET OF $" + player.getAnteBet());
							if (player.getPairPlusBet() == 0) {
								ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " DID NOT PLACE PAIR PLUS BET");
							}
							else {
								ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " PLACED PAIR PLUS BET OF $" + player.getPairPlusBet());
							}
						});

						try {
							out.writeObject(gameInstance);
						}
						catch (Exception e) {
							System.err.println("DEAL_PLAYER_CARDS response error");
						}
					}
					else if (request.equals("CHECK_DEALER_QUALIFICATION")) {
						Platform.runLater(() -> {
							ServerInfo.getServerController().getEventList().getItems().add("CHECKING DEALER QUALIFICATION FOR CLIENT #" + clientID);
						});
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
						player.setHasFolded(gameInstance.getHasFolded());
						gameInstance.setRoundWinnings(0);

						if (gameInstance.getHasFolded()) {
							Platform.runLater(() -> {
								ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " FOLDED");
							});
							player.setTotalWinnings(player.getTotalWinnings() - player.getPairPlusBet() - player.getAnteBet());
							gameInstance.setRoundWinnings((player.getPairPlusBet() + player.getAnteBet()) * -1);
							gameInstance.setWillPushAnte(false); // prevent user from pushing ante after folding
						}
						else {
							Platform.runLater(() -> {
								ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " PLACED PLAY BET");
							});
							int gameResult = ThreeCardLogic.CompareHands(dealer.getDealersHand(), player.getHand());
							int pairPlusResult = ThreeCardLogic.evalPPWinnings(player.getHand(), player.getPairPlusBet());

							// if player lost the pair plus bet, subtract wager from total winnings
							if (pairPlusResult == 0) {
								if (player.getPairPlusBet() > 0) {
									Platform.runLater(() -> {
										ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " LOST PAIR PLUS BET");
									});
								}
								pairPlusResult = player.getPairPlusBet() * -1;
							}
							else {
								Platform.runLater(() -> {
									ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " WON PAIR PLUS BET");
								});
							}

							if (!dealer.getQualify()) {
								player.setKeepAnte(true);
								player.setTotalWinnings(player.getTotalWinnings() + pairPlusResult);
								Platform.runLater(() -> {
									ServerInfo.getServerController().getEventList().getItems().add("PUSHING CLIENT #" + clientID + "'S ANTE TO NEXT ROUND");
								});
								gameInstance.setRoundWinnings(pairPlusResult);
							}
							else if (gameResult == 2) {
								// player wins
								Platform.runLater(() -> {
									ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " WON ANTE BET");
								});
								player.setTotalWinnings(player.getTotalWinnings() + pairPlusResult + (2 * player.getAnteBet()));
								gameInstance.setRoundWinnings(pairPlusResult + (2 * player.getAnteBet()));
							}
							else if (gameResult == 1) {
								// player loses
								Platform.runLater(() -> {
									ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " LOST ANTE BET");
								});
								player.setTotalWinnings(player.getTotalWinnings() + pairPlusResult - (2 * player.getAnteBet()));
								gameInstance.setRoundWinnings(pairPlusResult - (2 * player.getAnteBet()));
							}
						}
						gameInstance.setTotalWinnings(player.getTotalWinnings());

						Platform.runLater(() -> {
							if (gameInstance.getRoundWinnings() < 0) {
								ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " LOST $" + Math.abs(gameInstance.getRoundWinnings()) + " THIS ROUND");
							}
							else {
								ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " WON $" + gameInstance.getRoundWinnings() + " THIS ROUND");
							}
						});

						try {
							out.writeObject(gameInstance);
						}
						catch (Exception e) {
							System.err.println("EVALUATE_WINNINGS response error");
						}
					}
					else if (request.equals("GET_RESULT")) {
						String result = player.resultToString(dealer) + "\n\n";

						if (gameInstance.getRoundWinnings() < 0) {
							result += "YOU LOST $" + Math.abs(gameInstance.getRoundWinnings()) + " THIS ROUND";
						}
						else {
							result += "YOU WON $" + gameInstance.getRoundWinnings() + " THIS ROUND";
						}

						gameInstance.setResult(result);

						try {
							out.writeObject(gameInstance);
						}
						catch (Exception e) {
							System.err.println("GET_RESULT response error");
						}
					}
					else if (request.equals("CONTINUE_GAME")) {
						Platform.runLater(() -> {
							ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " IS PLAYING ANOTHER ROUND");
						});
					}
				}
				catch (Exception e) {
					synchronized(clients) {
						clients.remove(this);
					}
					Platform.runLater(() -> {
						ServerInfo.getServerController().getClientCountLabel().setText(Integer.toString(getServerCount()));
						ServerInfo.getServerController().getEventList().getItems().add("CLIENT #" + clientID + " HAS LEFT THE SERVER");
					});

					break;
				}
			}
		}//end of run
	}//end of client thread
}