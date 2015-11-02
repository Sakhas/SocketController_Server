package pt.novaims.server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import pt.novaims.game.model.SlickGame;

public class WorkerRunnable implements Runnable {
	
    protected Socket clientSocket = null;
    protected String serverText   = null;
    protected SlickGame game = null;

    public WorkerRunnable(Socket clientSocket, String serverText, SlickGame game) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
        this.game = game;
        //persereika
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
				//game.getPad().setLocation(x, y);
				
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

}
