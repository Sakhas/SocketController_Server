package pt.novaims.server.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pt.novaims.game.application.GameControl;

public class PlayerControl implements Runnable {

    protected int          serverPort   = 0;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    private int playerCount = 0;
    private GameControl game;
    
    public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public GameControl getGame() {
		return game;
	}

	public void setGame(GameControl game) {
		this.game = game;
	}

	public PlayerControl(int port, GameControl game) throws IOException {
    	this.serverPort = port;
    	this.game = game;
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
            playerCount++;
            new Thread(new Player(this, clientSocket, "Player " + playerCount, game)).start();
            System.out.println("Player " + playerCount +". Connected");
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
