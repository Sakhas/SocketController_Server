package pt.novaims.server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

<<<<<<< HEAD
import pt.novaims.game.model.SlickGame;
=======
import pt.novaims.game.application.GameControl;
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713

public class WorkerRunnable implements Runnable {
	
    protected Socket clientSocket = null;
    protected String serverText   = null;
<<<<<<< HEAD
    protected SlickGame game = null;

    public WorkerRunnable(Socket clientSocket, String serverText, SlickGame game) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
        this.game = game;
        //persereika
=======
    protected GameControl gameControl;

    public WorkerRunnable(Socket clientSocket, String serverText, GameControl gameControl) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
        this.gameControl = gameControl;
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713
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
<<<<<<< HEAD
				//game.getPad().setLocation(x, y);
=======
				updateControlLocation(line);
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713
				
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
<<<<<<< HEAD
=======
	
	private void updateControlLocation(String message) {
		Double angle = Double.parseDouble(message);
		if(gameControl.getSlickGame().getRacket() != null) {
			if(angle > 0) {
				gameControl.getSlickGame().getRacket().setLocation(200, 550);
			} else {
				gameControl.getSlickGame().getRacket().setLocation(600, 550);
			}	
		}
	}
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713

}
