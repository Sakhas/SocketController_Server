package pt.novaims.server.application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import pt.novaims.game.application.GameControl;
import pt.novaims.game.model.SlickGame;
import pt.novaims.server.model.SocketServer;

public class ServerMain {

	final static String KEYWORD = "PONG_REQUEST";
	final static String KEYWORD_RESPONSE = "PONG_CONNECTING_DATA";
	final static int SERVER_PORT = 8888;
	final static int SOCKET_PORT = 8888;
	
	static SlickGame slickGame;
	
	public static void main(String[] args) {
				
		GameControl gameControl = new GameControl();
		new Thread(gameControl).start();
		
		SocketServer server;
		try {
<<<<<<< HEAD
			server = new SocketServer(SERVER_PORT, slickGame);
=======
			server = new SocketServer(SERVER_PORT, gameControl);
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713
			new Thread(server).start();
			System.out.println("Server started");
					
			DatagramSocket serverSocket = new DatagramSocket(SOCKET_PORT, InetAddress.getByName("0.0.0.0")); 
			serverSocket.setBroadcast(true);
			byte[] receive = new byte[12];
			byte[] sendData = new byte[1500];
			
			while(true) {
				receive = new byte[12];
				DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);
				serverSocket.receive(receivePacket);	
				String receivedData = new String(receivePacket.getData());
				System.out.println("RECEIVED: " + receivedData + " From address: " + receivePacket.getAddress());
				
				if(receivedData.equals(KEYWORD)) {
					System.out.println("Contact made with client\n Client Address: " + receivePacket.getAddress());
					System.out.println("ok");
		            InetAddress IPAddress = receivePacket.getAddress();
		            	            
		            String sendString = KEYWORD_RESPONSE;
		            sendData = sendString.getBytes();
		            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, receivePacket.getPort());
		            String data = new String(sendPacket.getData());
		            System.out.println("Message: " + data + " sent back to the client");
		            serverSocket.send(sendPacket);
				}
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}		

		try {
		    Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();  
		}
		/*System.out.println("Stopping Server");
		server.stop();*/
	}
	
	public static void startGame() {
		
		slickGame = new SlickGame("SlickGame");
		AppGameContainer app;
		
		try {
			app = new AppGameContainer(slickGame);
			app.setDisplayMode(800, 600, false);
			
			app.setTitle("Slick game");
			app.start();
			
		} catch (SlickException e) {
			System.err.println("Error occured in the game: ");
			e.printStackTrace();
		}
		
		
		
	}
	
	public static String getIpFromAddress(String address) {
		
		String ipAddress = "";
	
		for(int i = 0; i < address.length(); i++) {
			if(Character.isDigit(address.charAt(i)) || address.charAt(i) == '.') {
				ipAddress += address.charAt(i);
			}		
		}		
		return ipAddress;
	}

}
