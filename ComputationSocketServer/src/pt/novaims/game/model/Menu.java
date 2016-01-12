package pt.novaims.game.model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pt.novaims.game.application.GameControl;
import pt.novaims.game.util.GameInfo;
import pt.novaims.server.model.PlayerControl;

public class Menu extends BasicGameState {

	private int id;
	private PlayerControl playerControl;
	private GameControl gameControl;
	private int playerCount = 0;
	
	public Menu(int id, PlayerControl playerControl, GameControl gameControl) {
		this.playerControl = playerControl;
		this.gameControl = gameControl;
		this.id = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		gameControl.setGameRunning(false);
		
		
		/*singleText = new TextField(container, container.getDefaultFont(), 150, 100, 200, 30);
		multiplayerText = new TextField(container, container.getDefaultFont(), 150, 100, 200, 30);
		singleText.setText("1. Single player");
		multiplayerText.setText("2. Multiplayer");
		singleText.setLocation(200, 300);
		multiplayerText.setLocation(200, 400);*/
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics) throws SlickException {
		
		graphics.drawString("Welcome to SlickGame", GameInfo.WIDTH/3, 20);
		graphics.drawString("Connect your controller to start the game!", GameInfo.WIDTH/4, 40);
		if(playerCount > 0) {
			graphics.drawString("1. Single player", 200, 300);
		}
		if(playerCount > 1) {
			graphics.drawString("2. Multiplayer", 200, 400);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int graphics) throws SlickException {
		playerCount = playerControl.getPlayerCount();
		if (playerCount > 0 && container.getInput().isKeyPressed(Input.KEY_1)) {
			gameControl.setGameRunning(true);
			SingleplayerGame singleGame = new SingleplayerGame(2, playerControl.getPlayer1(), gameControl);
			singleGame.init(container, sbg);
			gameControl.setSinglePlayerGame(singleGame);
			sbg.addState(singleGame);
			sbg.enterState(2);
		}
		else if (playerCount > 1 && container.getInput().isKeyPressed(Input.KEY_2)) {
			gameControl.setGameRunning(true);
			MultiplayerGame multiGame = new MultiplayerGame(3, playerControl.getPlayer1(), playerControl.getPlayer2(), gameControl);
			multiGame.init(container, sbg);
			gameControl.setMultiplayerGame(multiGame);
			sbg.addState(multiGame);
			sbg.enterState(3);
		}
	}
	

	@Override
	public int getID() {
		return this.id;
	}

	
	
}
