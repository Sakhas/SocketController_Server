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
            	System.out.println("TCP socket connection started");
            	System.out.println("Game running:  " + game.isGameRunning());
            	clientSocket = this.serverSocket.accept();
            	if(playerCount < 2 || player1.isDisconnected() || player2.isDisconnected()) {
            		if(!game.isGameRunning() && playerCount < 2) {
	            		System.out.println("Adding new player");
	            		playerCount++;
	            		Player player = new Player(this, clientSocket, playerCount, game);
	            		new Thread(player).start();
	            		if(playerCount == 1) {
	            			player1 = player;
	            		} else if(playerCount == 2) {
	            			player2 = player;
	            		}
	            		System.out.println("Player " + playerCount +". Connected");
            		} else {
                		System.out.println("Player1 IP: " + player1.getIp()  + "Player connecting: " + clientSocket.getInetAddress().toString());
                		if(player1 != null && player1.getIp().equals(clientSocket.getInetAddress().toString())) {
                			player1.setClientSocket(clientSocket);
                			System.out.println("Player " + playerCount +". reconnected");
                			new Thread(player1).start();
                		} else if(player2 != null && player2.getIp().equals(clientSocket.getInetAddress().toString())) {
                			player2.setClientSocket(clientSocket);
                			System.out.println("Player " + playerCount +". reconnected");
                			new Thread(player2).start();
                		}
            		}
        		
            	}	
                    
            	/*} else if (game.isGameRunning()) {
            		
            		clientSocket = this.serverSocket.accept();
            		System.out.println("Player1 IP: " + player1.getIp()  + "Player connecting: " + clientSocket.getInetAddress().toString());
            		if(player1 != null && player1.getIp().equals(clientSocket.getInetAddress().toString())) {
            			player1.setClientSocket(clientSocket);
            			System.out.println("Player " + playerCount +". reconnected");
            			new Thread(player1).start();
            		} else if(player2 != null && player2.getIp().equals(clientSocket.getInetAddress().toString())) {
            			player2.setClientSocket(clientSocket);
            			System.out.println("Player " + playerCount +". reconnected");
            			new Thread(player2).start();
            		}
            		
            	}*/
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
    	} else {
    		game.pauseActiveGameDueDisconnection(player);
    	}
    }
    
    public void playerResumedGameAfterDisconnection(Player player) {
    	System.out.println("Player " + player.getPlayerNumber() + ". resumed game!");
    	game.playerConnectedBackToGame(player);
	}

    public void updatePlayerCount() {
		if(player1 != null) {
			if(!player1.isDisconnected()) {
				System.out.println("plauer 1 found");
			} else {
				playerDisconnected(player1);
			}	
		}
		if(player2 != null) {
			if(!player2.isDisconnected()) {
				System.out.println("player 2 found");
			} else {
				playerDisconnected(player2);
			}
		}
		
    }

}
