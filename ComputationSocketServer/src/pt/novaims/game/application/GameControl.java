package pt.novaims.game.application;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import pt.novaims.game.model.SlickGame;

public class GameControl implements Runnable {

	SlickGame slickGame;
	
	@Override
	public void run() {
		
		slickGame = new SlickGame("SlickGame");
		AppGameContainer app;
		
		try {
			app = new AppGameContainer(slickGame);
			app.setDisplayMode(800, 600, false);
			
			app.setTitle("Slick game");
			app.start();
			
		} catch (SlickException e) {
			System.err.println("Error occured in the game: ");
			e.printStackTrace();
		}
		
	}

}
