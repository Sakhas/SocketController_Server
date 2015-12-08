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
    private Player player1;
    private Player player2;
    
    public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

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
            	if(playerCount < 2) {
            		clientSocket = this.serverSocket.accept();
            		playerCount++;
            		Player player = new Player(this, clientSocket, playerCount, game);
            		new Thread(player).start();
            		if(playerCount == 1) {
            			player1 = player;
            		} else if(playerCount == 2) {
            			player2 = player;
            		}
                    System.out.println("Player " + playerCount +". Connected");
            	}
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }     
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

    public void playerDisconnected(Player player) {
    	//Pause multiplayer game
    	System.out.println("Player " + player.getPlayerNumber() + ". disconnected!");
    	
    	if(!game.isGameRunning()) {
    		
    		if(player.getPlayerNumber() == 1 && playerCount == 2) {
    			player1 = player2;
    			player1.setPlayerNumber(1);
    			player2 = null;
    		} else if (player.getPlayerNumber() == 2) {
    			player2 = null;
    		} else if(player.getPlayerNumber() == 1 && playerCount == 1) {
    			player1 = null;
    		}
    		
    		playerCount--;	
    	}
    }


}
