package pt.novaims.server.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pt.novaims.game.application.GameControl;

public class SocketServer implements Runnable {

    protected int          serverPort   = 0;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    
    GameControl game;
	
    public SocketServer(int port, GameControl game) throws IOException {
    	this.serverPort = port;
    	this.game = game;
    	//serverSocket = new ServerSocket(port, 0, InetAddress.getLocalHost());
    	//System.out.println(InetAddress.getLocalHost());
    }
    
    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            new Thread(new WorkerRunnable(clientSocket, "Computation Server", game)).start();
            System.out.println("Started new Multithread server");
        }
        
        System.out.println("Server Stopped.");
    }

 /*   private void processClientRequest(Socket clientSocket) throws IOException {
        InputStream  input  = clientSocket.getInputStream();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = br.readLine()) != null) {
			sb.append(line);
		}
        String result = new String(sb.toString());
        br.close();
        
        System.out.println("Input is: " + result);
        OutputStream output = clientSocket.getOutputStream();
        long time = System.currentTimeMillis();

        output.write(("HTTP/1.1 200 OK\n\n<html><body>" +"Server: " + time + "</body></html>").getBytes());
        output.close();
        input.close();
        System.out.println("Request processed: " + time);
    }*/

    public synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port" + this.serverPort, e);
        }
    }



}
