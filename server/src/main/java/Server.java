import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.function.Consumer;
import java.util.ArrayList;


public class Server{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	
	synchronized int increase() {
	    int current = count;
	    count++;
	    return current;
	}
	
	Server(Consumer<Serializable> call){
	
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), increase());
				callback.accept("client has connected to server: " + "client #" + count);
				synchronized(clients) {
					clients.add(c);
				}
				c.start();
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			
			public synchronized void updateClients(String message) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					if(message.equals("Join")) {
						try {
							t.out.writeObject(message);
						}
						catch(Exception e) {}
					}
					else if(message.equals("Quit")) {
						try {
							
							t.out.writeObject(message);
						}
						catch(Exception e) {}
					}
					else if(message.equals("Fold")) {
						try {
							
							t.out.writeObject(message);
						}
						catch(Exception e) {}
					}
					else if(message.equals("Play")) {
						try {
							
							t.out.writeObject(message);
						}
						catch(Exception e) {}
					}
					else if(message.equals("PairPlus")) {
						try {
							
							t.out.writeObject(message);
						}
						catch(Exception e) {}
					}
					else if(message.equals("Ante")) {
						try {
							
							t.out.writeObject(message);
						}
						catch(Exception e) {}
					}
					else if(message.equals("FreshStart")) {
						try {
							
							t.out.writeObject(message);
						}
						catch(Exception e) {}
					}
				}
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				updateClients("new client on server: client #"+count);
					
				 while(true) {
					    try {
						    	String data = in.readObject().toString();
						    	callback.accept("client: " + count + " sent: " + data);
						    	updateClients("client #"+count+" said: "+data);
						    	if(data.equals("Join")) {
						    		//something
						    	}
						    	else if(data.equals("Quit")) {
						    		//something
						    	}
						    	else if(data.equals("Fold")) {
						    		//something
						    	}
						    	else if(data.equals("Play")) {
						    		//something
						    	}
						    	else if(data.equals("PairPlus")) {
						    		//something
						    	}
						    	else if(data.equals("Ante")) {
						    		//something
						    	}
						    	else if(data.equals("FreshStart")) {
						    		//something
						    	}
					    	}
					    catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	updateClients("Client #"+count+" has left the server!");
					    	synchronized(clients) {
					    		clients.remove(this);
					    	}
					    	break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}