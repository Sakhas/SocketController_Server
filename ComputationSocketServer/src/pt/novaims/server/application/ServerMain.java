package pt.novaims.server.application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import pt.novaims.server.model.SocketServer;

public class ServerMain {

	public static void main(String[] args) {
		
		
		SocketServer server;
		try {
			server = new SocketServer(9000);
			new Thread(server).start();
			System.out.println("Server started");
			
			DatagramSocket datagramSocket;
			
			datagramSocket = new DatagramSocket(80);
			byte[] buffer = new byte[10];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			
			while(!server.isStopped()) {				
				datagramSocket.receive(packet);	
				buffer = packet.getData(); 
				System.out.println(buffer.toString());
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
	
	

}
