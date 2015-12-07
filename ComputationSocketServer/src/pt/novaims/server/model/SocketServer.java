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
            new Thread(new Player(clientSocket, "Computation Server", game)).start();
            System.out.println("Started new Multithread server");
        }
        
        System.out.println("Server Stopped.");
    }

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
