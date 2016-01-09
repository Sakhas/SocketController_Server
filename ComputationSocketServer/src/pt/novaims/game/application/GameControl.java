package pt.novaims.game.application;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import pt.novaims.game.model.Menu;
import pt.novaims.game.model.MultiplayerGame;
import pt.novaims.game.model.PauseState;
import pt.novaims.game.model.SingleplayerGame;
import pt.novaims.game.util.GameInfo;
import pt.novaims.server.model.Player;
import pt.novaims.server.model.PlayerControl;

public class GameControl extends StateBasedGame implements Runnable {

	//State IDs
	public static final int MENU = 1;
	public static final int SINGLE = 2;
	public static final int MULTI = 3;
	
	private SingleplayerGame singlePlayerGame;
	private MultiplayerGame multiplayerGame;
	private PauseState pauseState;
	private AppGameContainer app;
	private PlayerControl playerControl;
	private Menu menu;
	private boolean gameRunning;
	private boolean gamePaused = false;
	
	public SingleplayerGame getSinglePlayerGame() {
		return singlePlayerGame;
	}

	public void setSinglePlayerGame(SingleplayerGame singlePlayerGame) {
		this.singlePlayerGame = singlePlayerGame;
	}

	public MultiplayerGame getMultiplayerGame() {
		return multiplayerGame;
	}

	public void setMultiplayerGame(MultiplayerGame multiplayerGame) {
		this.multiplayerGame = multiplayerGame;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}
		
	public AppGameContainer getApp() {
		return app;
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
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
		//this.addState(new MultiplayerGame(MULTI, playerControl.getPlayer1(), playerControl.getPlayer2()));
		
	}
	
	public void pauseActiveGameDueDisconnection(Player playerDisconnected) {
		int stateId = this.getCurrentStateID();
		if(!gamePaused) {
			if (stateId == 2) {
				pauseState = new PauseState(this.getCurrentState(), playerDisconnected, this, playerControl);
				this.addState(pauseState);
			} else if(stateId == 3) {
				pauseState = new PauseState(this.getCurrentState(), playerDisconnected, this, playerControl);
				this.addState(pauseState);
			}
			gamePaused = true;
			this.enterState(4);
		} else if (gamePaused) {
			System.out.println("Other player disconnected");
			pauseState.otherPlayerDisconnected(playerDisconnected);
		}
	}
	
	public void playerConnectedBackToGame(Player connectedPlayer) {
		if(gamePaused) {
			pauseState.playerConnectedBackToGame(connectedPlayer);
		}
	}
	
	

}
