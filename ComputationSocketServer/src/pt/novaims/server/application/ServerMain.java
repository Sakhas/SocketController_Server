package pt.novaims.server.application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import pt.novaims.server.model.SocketServer;

public class ServerMain {

	final static String KEYWORD = "Computation";
	final static int SERVER_PORT = 9001;
	final static int SOCKET_PORT = 8888;
	
	public static void main(String[] args) {
				
		SocketServer server;
		try {
			server = new SocketServer(SERVER_PORT);
			new Thread(server).start();
			System.out.println("Server started");
			
			DatagramSocket serverSocket = new DatagramSocket(SOCKET_PORT, InetAddress.getByName("0.0.0.0")); 
			serverSocket.setBroadcast(true);
			byte[] receive = new byte[11];
			byte[] sendData = new byte[11];
			
			while(true) {
				DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);
				serverSocket.receive(receivePacket);	
				String receivedData = new String(receivePacket.getData());
				System.out.println("RECEIVED: " + receivedData);
				
				if(receivedData.equals(KEYWORD)) {
					System.out.println("Contact made with client");
		            InetAddress IPAddress = receivePacket.getAddress();
		            	            
		            String sendString = getIpFromAddress(InetAddress.getLocalHost().toString()) + " " + Integer.toString(SERVER_PORT);
		            sendData = sendString.getBytes();
		            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, receivePacket.getPort());
		            String data = new String(sendPacket.getData());
		            System.out.println(data);
		            serverSocket.send(sendPacket);
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
