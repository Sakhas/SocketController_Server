package pt.novaims.game.application;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import pt.novaims.game.model.SlickGame;

public class GameControl implements Runnable {

	private SlickGame slickGame;
	private AppGameContainer app;
	
	public SlickGame getSlickGame() {
		return slickGame;
	}

	public void setSlickGame(SlickGame slickGame) {
		this.slickGame = slickGame;
	}
		
	public AppGameContainer getApp() {
		return app;
	}

	public GameControl() {
		this.slickGame = new SlickGame("Pinkiponki");

	}

	@Override
	public void run() {
		
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
