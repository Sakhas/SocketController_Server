package pt.novaims.game.application;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import pt.novaims.game.model.Menu;
import pt.novaims.game.model.MultiplayerGame;
import pt.novaims.game.model.SingleplayerGame;
import pt.novaims.game.util.GameInfo;
import pt.novaims.server.model.PlayerControl;

public class GameControl extends StateBasedGame implements Runnable {

	//State IDs
	private static final int MENU = 1;
	private static final int SINGLE = 2;
	private static final int MULTI = 3;
	
	private SingleplayerGame slickGame;
	private MultiplayerGame multiplayerGame;
	private AppGameContainer app;
	private PlayerControl playerControl;
	private Menu menu;
	private boolean gameRunning;
	
	
	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

	public SingleplayerGame getSlickGame() {
		return slickGame;
	}

	public void setSlickGame(SingleplayerGame slickGame) {
		this.slickGame = slickGame;
	}
		
	public AppGameContainer getApp() {
		return app;
	}

	public GameControl(String name, PlayerControl playerControl) {
		super(name);
		this.playerControl = playerControl;
		gameRunning = false;
	}

	@Override
	public void run() {
		try {
			app = new AppGameContainer(this);
			app.setDisplayMode(800, 600, false);	
			app.setTargetFrameRate(GameInfo.FPS);
			app.setTitle("Slick game");
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new Menu(MENU, playerControl, this));
		//this.addState(new SingleplayerGame(SINGLE, playerControl.getPlayer1()));
		this.addState(new MultiplayerGame(MULTI));
		
	}

}
