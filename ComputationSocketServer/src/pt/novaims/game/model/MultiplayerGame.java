package pt.novaims.game.model;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pt.novaims.game.util.GameInfo;
import pt.novaims.server.model.Player;

public class MultiplayerGame extends BasicGameState {

	private Player player1;
	private Player player2;
	private Rectangle p1Racket;
	private Rectangle p2Racket;
	private Ball ball;
	private boolean gameOver = false;
	private ArrayList<Tile> tileList;
	private int id;
	
	
	public MultiplayerGame(int id, Player player1, Player player2) {
		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		this.p1Racket = player1.getRacket();
		this.p2Racket = player2.getRacket();
	}

	@Override
	public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		p1Racket = player1.getRacket();
		p2Racket = player2.getRacket();
		ball = new Ball((int) Math.ceil(Math.random()* GameInfo.WIDTH), GameInfo.TILE_HEIGHT*GameInfo.ARRAY_ROWS + GameInfo.TILE_HEIGHT_LOC + 20, 6);
		org.newdawn.slick.geom.Vector2f ballVelocityVector = new org.newdawn.slick.geom.Vector2f();
		ballVelocityVector.x = (float) GameInfo.ballVelocity;
		ballVelocityVector.y = (float) GameInfo.ballVelocity;
		ball.setBallVelocity(ballVelocityVector);
		//initTileArray(container.getGraphics());
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.setBackground(Color.gray);
		graphics.setColor(new Color(0, 0, 0));
	    graphics.fill(p1Racket);
	    graphics.draw(p1Racket);
	    graphics.fill(p2Racket);
	    graphics.draw(p2Racket);
	    graphics.fill(ball);
	    graphics.drawString("Welcome to SlickGame", 300, 20);
	    graphics.drawString("P1 Balls left: " + Integer.toString(player1.getBallsLeft()), GameInfo.WIDTH - 200, 50);
	    graphics.drawString("P2 Balls left: " + Integer.toString(player2.getBallsLeft()), GameInfo.WIDTH - 200, 100);
	    
	    //drawTileArray(graphics);
	    
	    if(player1.didMissBall()) {
	    	player1.updateBallCount();
	    	if(player1.getBallsLeft() == 0){
	    		player1.resetBallsLeft();
	    		this.init(container, stateBasedGame);
	    	}
	    	else {
	    		this.tryAgain(container);
	    	}
	    }	
	    else if(player2.didMissBall()) {
	    	player2.updateBallCount();
	    	if(player2.getBallsLeft() == 0){
	    		player2.resetBallsLeft();
	    		this.init(container, stateBasedGame);
	    	}
	    	else {
	    		this.tryAgain(container);
	    	}
	    }
					
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {

		p1Racket = player1.getRacket();
		//System.out.println(p1Racket.getLocation().x + " " + p1Racket.getLocation().y);
		p2Racket = player2.getRacket();
		//System.out.println(p2Racket.getLocation().x + " " + p2Racket.getLocation().y);
		
		ball.setLocation(ball.getX() + ball.getBallVelocity().getX(), ball.getY() + ball.getBallVelocity().getY());
		
		if (ball.getMinX() <= 0) {
			   ball.getBallVelocity().x = -ball.getBallVelocity().getX();
			}
		if (ball.getMaxX() >= GameInfo.WIDTH) {
		   ball.getBallVelocity().x = -ball.getBallVelocity().getX();
		}
		
		if (ball.getMinY() <= 0)
		   ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		if (ball.getMaxY() >= GameInfo.HEIGHT)
		   ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		
		if(ball.intersects(p1Racket)) {
			ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		}
		else if(ball.intersects(p2Racket)) {
			ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		}
		
		/*if(ball.getLocation().y < GameInfo.TILE_HEIGHT*GameInfo.ARRAY_ROWS + GameInfo.TILE_HEIGHT_LOC) {
			checkBallInteractionWithTile();
		}*/
		
		if(ball.getLocation().y >= GameInfo.HEIGHT - 20) {
			player1.ballMissed();
		}
		else if(ball.getLocation().y <= 20) {
			player2.ballMissed();
		}
	}
	
	public void tryAgain(GameContainer container) throws SlickException {
		player1.resetRacket();
		p1Racket = player1.getRacket();
		player2.resetRacket();
		p2Racket = player2.getRacket();
				
		ball = new Ball((int) Math.ceil(Math.random()*GameInfo.WIDTH), GameInfo.TILE_HEIGHT*GameInfo.ARRAY_ROWS + GameInfo.TILE_HEIGHT_LOC + 20, 6);
		org.newdawn.slick.geom.Vector2f ballVelocityVector = new org.newdawn.slick.geom.Vector2f();
		ballVelocityVector.x = (float) GameInfo.ballVelocity;
		ballVelocityVector.y = (float) GameInfo.ballVelocity;
		ball.setBallVelocity(ballVelocityVector);
	}
	
	

	@Override
	public int getID() {
		return this.id;
	}

}
