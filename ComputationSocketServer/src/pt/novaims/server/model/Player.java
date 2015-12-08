package pt.novaims.server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Vector2f;

import pt.novaims.game.application.GameControl;
import pt.novaims.game.util.GameInfo;

public class Player implements Runnable {
	
    protected Socket clientSocket = null;
    protected String playerOutput   = null;
    protected GameControl gameControl;
    protected int width;
    private PlayerControl playerControl;
    private Rectangle racket;
    private String ip;
    private int playerNumber;
    
    
    public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public Rectangle getRacket() {
		return racket;
	}

	public void setRacket(Rectangle racket) {
		this.racket = racket;
	}

	public Player(PlayerControl playerControl, Socket clientSocket, int playerNumber, GameControl gameControl) {
        this.playerControl = playerControl;
    	this.clientSocket = clientSocket;
        this.playerNumber = playerNumber;
        this.ip = clientSocket.getInetAddress().toString();
        this.gameControl = gameControl;
        this.racket = new RoundedRectangle(GameInfo.WIDTH / 2 - 40, 550, GameInfo.RACKET_WIDTH, GameInfo.RACKET_HEIGHT, 3);
        this.playerOutput = "Player " + Integer.toString(playerNumber) + ". ";
    }

    public void run() {
        try {     	
        	InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            String inputMessage = getStringFromInputStream(input);
            System.err.println(playerOutput + " message: " + inputMessage);      
        	
        	long time = System.currentTimeMillis();
            output.close();
            input.close();
            System.out.println(playerOutput + " request processed: " + time);
            playerControl.playerDisconnected(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	private String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		sb.append(playerOutput + ". left the game.");

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				System.out.println(playerOutput + "controller data:" + line);
				updateControlLocation(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
	private void updateControlLocation(String message) {
		Vector2f currLocation;
		if(gameControl.isGameRunning()) {
			currLocation = racket.getLocation();
			
			if(message.equals("fastRight") && currLocation.x < GameInfo.WIDTH -80) {
				racket.setLocation(currLocation.x + 4, currLocation.y);
			} else if(message.equals("slowRight") && currLocation.x < GameInfo.WIDTH -80) {
				racket.setLocation(currLocation.x + 2, currLocation.y);
			} else if(message.equals("fastLeft") && currLocation.x > 0) {
				racket.setLocation(currLocation.x - 4, currLocation.y);
			} else if(message.equals("slowLeft") && currLocation.x > 0) {
				racket.setLocation(currLocation.x - 2, currLocation.y);
			}
		}	
	}
	
	public void resetRacket() {
		this.racket = new RoundedRectangle(GameInfo.WIDTH / 2 - 40, 550, GameInfo.RACKET_WIDTH, GameInfo.RACKET_HEIGHT, 3);
	}
}
