package pt.novaims.game.model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import pt.novaims.game.util.GameInfo;
import pt.novaims.server.model.Player;

public class PauseState extends BasicGameState {

	private GameState game;
	private Player disconnectedPlayer1;
	private Player disconnectedPlayer2;
	private boolean playerReconnected = false;

	public boolean isPlayerReconnected() {
		return playerReconnected;
	}

	public void setPlayerReconnected(boolean playerReconnected) {
		this.playerReconnected = playerReconnected;
	}

	public PauseState(GameState game, Player disconnectedPlayer) {
		this.game = game;
		this.disconnectedPlayer1 = disconnectedPlayer;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics) throws SlickException {
		graphics.drawString("Game paused!", GameInfo.WIDTH/3, 20);
		
		if(disconnectedPlayer1 != null) {
			graphics.drawString("Player " + disconnectedPlayer1.getPlayerNumber() + ". disconnected.", GameInfo.WIDTH/4, 100);
		}
		if(disconnectedPlayer2 != null) {
			graphics.drawString("Player " + disconnectedPlayer2.getPlayerNumber() + ". disconnected.", GameInfo.WIDTH/4, 140);
		}
		
		if(disconnectedPlayer1 == null && disconnectedPlayer2 == null) {
			graphics.drawString("1. Resume to game", 200, 250);
		}
		
		graphics.drawString("2. Quit to Menu", 200, 300);

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int arg2) throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_1) && disconnectedPlayer1 == null && disconnectedPlayer2 == null) {
			sbg.enterState(game.getID());
		} else if(container.getInput().isKeyPressed(Input.KEY_2)) {
			sbg.enterState(1);
		}
	}
	
	public void otherPlayerDisconnected(Player disconnectedPlayer) {
		if(disconnectedPlayer1 == null) {
			disconnectedPlayer1 = disconnectedPlayer;
		} else {
			disconnectedPlayer2 = disconnectedPlayer;
		}
	}
	
	public void playerConnectedBackToGame(Player connectedPlayer) {
		if(disconnectedPlayer1 != null && disconnectedPlayer1.getIp().equals(connectedPlayer.getIp())){
			disconnectedPlayer1 = null;
		} else if(disconnectedPlayer2 != null && disconnectedPlayer1.getIp().equals(connectedPlayer.getIp())) {
			disconnectedPlayer2 = null;
		}
	}

	@Override
	public int getID() {
		return 4;
	}

}