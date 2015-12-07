package pt.novaims.server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.newdawn.slick.geom.Vector2f;
import pt.novaims.game.application.GameControl;

public class Player implements Runnable {
	
    protected Socket clientSocket = null;
    protected String serverText   = null;
    protected GameControl gameControl;
    protected int width;

    public Player(Socket clientSocket, String serverText, GameControl gameControl) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
        this.gameControl = gameControl;
        this.width = gameControl.getApp().getWidth();
        System.out.println(width);
    }

    public void run() {
        try {     	
        	InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            String inputMessage = getStringFromInputStream(input);
            System.err.println("WorkerRunnable Run-method, message: " + inputMessage);      
        	
        	long time = System.currentTimeMillis();
            output.close();
            input.close();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	private String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				System.out.println(line);
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
		if(gameControl.getSlickGame().getRacket() != null) {
			currLocation = gameControl.getSlickGame().getRacket().getLocation();
			
			if(message.equals("fastRight") && currLocation.x < width -80) {
				gameControl.getSlickGame().getRacket().setLocation(currLocation.x + 4, currLocation.y);
			} else if(message.equals("slowRight") && currLocation.x < width -80) {
				gameControl.getSlickGame().getRacket().setLocation(currLocation.x + 2, currLocation.y);
			} else if(message.equals("fastLeft") && currLocation.x > 0) {
				gameControl.getSlickGame().getRacket().setLocation(currLocation.x - 4, currLocation.y);
			} else if(message.equals("slowLeft") && currLocation.x > 0) {
				gameControl.getSlickGame().getRacket().setLocation(currLocation.x - 2, currLocation.y);
			}
		}	
	}
}
