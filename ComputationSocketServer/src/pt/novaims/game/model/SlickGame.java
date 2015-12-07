package pt.novaims.game.model;

import java.util.ArrayList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;

import pt.novaims.game.util.GameInfo;

public class SlickGame extends BasicGame {

	private Music backgroundMusic;
	private Rectangle racket;
	private Ball ball;
	private int missCount = 0;
	private int ballsLeft = 3;
	private boolean ballMissed = false;
	private boolean gameOver = false;
	private ArrayList<Tile> tileList;
	
	public SlickGame(String title) {
		super(title);
	}
	
	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.setBackground(Color.gray);
		graphics.setColor(new Color(0, 0, 0));//inside color
	    graphics.fill(racket);
	    graphics.fill(ball);
	   // graphics.setColor(new Color(255, 0, 0));//red, green, blue OUTLINE of circle
	    graphics.draw(racket);
	    graphics.drawString("Welcome to SlickGame", 300, 20);
	    graphics.drawString("Balls left: " + Integer.toString(ballsLeft), GameInfo.WIDTH - 200, 50);
	    
	    drawTileArray(graphics);
	    
	    if(ballMissed) {
	    	ballMissed = false;
	    	ballsLeft--;
	    	missCount++;
	    	if(ballsLeft == 0){
	    		ballsLeft = 3;
	    		this.init(container);
	    	}
	    	else {
	    		this.tryAgain(container);
	    	}
	    }	
					
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		backgroundMusic = new Music("res/sounds/background1.ogg");
		backgroundMusic.setVolume(10);
		backgroundMusic.play();
		racket = new RoundedRectangle(GameInfo.WIDTH / 2 - 40, 550, GameInfo.RACKET_WIDTH, GameInfo.RACKET_WIDTH, 3);
				
		ball = new Ball((int) Math.ceil(Math.random()* GameInfo.WIDTH), GameInfo.TILE_HEIGHT*GameInfo.ARRAY_ROWS + GameInfo.TILE_HEIGHT_LOC + 20, 6);
		org.newdawn.slick.geom.Vector2f ballVelocityVector = new org.newdawn.slick.geom.Vector2f();
		ballVelocityVector.x = (float) GameInfo.ballVelocity;
		ballVelocityVector.y = (float) GameInfo.ballVelocity;
		ball.setBallVelocity(ballVelocityVector);
		initTileArray(container.getGraphics());
		
	}
	
	public void tryAgain(GameContainer container) throws SlickException{
		racket = new RoundedRectangle(GameInfo.WIDTH / 2 - 40, 550, GameInfo.RACKET_WIDTH, GameInfo.RACKET_HEIGHT, 3);
				
		ball = new Ball((int) Math.ceil(Math.random()*GameInfo.WIDTH), GameInfo.TILE_HEIGHT*GameInfo.ARRAY_ROWS + GameInfo.TILE_HEIGHT_LOC + 20, 6);
		org.newdawn.slick.geom.Vector2f ballVelocityVector = new org.newdawn.slick.geom.Vector2f();
		ballVelocityVector.x = (float) GameInfo.ballVelocity;
		ballVelocityVector.y = (float) GameInfo.ballVelocity;
		ball.setBallVelocity(ballVelocityVector);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {

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
		
		if(ball.intersects(racket)) {
			ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		}
		
		if(ball.getLocation().y < GameInfo.TILE_HEIGHT*GameInfo.ARRAY_ROWS + GameInfo.TILE_HEIGHT_LOC) {
			checkBallInteractionWithTile();
		}
		
		if(ball.getLocation().y >= GameInfo.HEIGHT - 20) {
			ballMissed = true;
		}
	}
	
	public void initTileArray(Graphics graphics) {
		tileList = new ArrayList<>();
		float tileWidthLoc = 50;
		int tileHeightLoc = 100;
		for(int i = 0; i < GameInfo.ARRAY_ROWS; i++) {
			for(int j = 0; j < GameInfo.ARRAY_COLUMNS && tileWidthLoc < GameInfo.WIDTH -50; j++) {			
				tileList.add(new Tile((float)tileWidthLoc, (float)tileHeightLoc, (float)GameInfo.TILE_WIDTH, (float)GameInfo.TILE_HEIGHT, (int) Math.ceil(Math.random()*3), graphics));
				tileWidthLoc += GameInfo.TILE_WIDTH;
			}
			tileHeightLoc += GameInfo.TILE_HEIGHT;
			tileWidthLoc = 50;
		}
	} 
	
	public void drawTileArray(Graphics graphics) {
		for(Tile t : tileList){
			if(t != null){
				
				Color color = t.getCurrentColor();
				graphics.setColor(color);
				graphics.fill(t);
				graphics.draw(t);
				
			    graphics.setLineWidth(2);
			    graphics.setColor(Color.black);
			    float [] dimensions = t.getRect();
			    graphics.drawRect(dimensions[0], dimensions[1], dimensions[2], dimensions[3]);
			}
		}
	}
	
	public void checkBallInteractionWithTile() {
		ArrayList <Tile> toRemove = new ArrayList<>();
		
		for(Tile t : tileList){
			float [] dimensions = t.getRect();
			if(ball.intersects(new Line(dimensions[0], dimensions[1], dimensions[0]
													+ dimensions[2], dimensions[1]))
			|| ball.intersects(new Line(dimensions[0], dimensions[1] + dimensions[3],
					dimensions[0] + dimensions[2], dimensions[1] + dimensions[3]))){
				ball.getBallVelocity().y = -ball.getBallVelocity().getY();
				if(t.wasShot() == 0){
					toRemove.add(t);
				}	
			}
			
			else if(ball.intersects(new Line(dimensions[0] + dimensions[2], dimensions[1],
							dimensions[0] + dimensions[2], dimensions[1] + dimensions[3]))
					|| ball.intersects(new Line(dimensions[0], dimensions[1], dimensions[0],
														dimensions[1] + dimensions[3]))){
						ball.getBallVelocity().x = -ball.getBallVelocity().getX();
						if(t.wasShot() == 0){
							toRemove.add(t);
						}	
					}
		}
		
		for(Tile t : toRemove){
			tileList.remove(t);
		}
		
		toRemove.clear();
	}
	
	public Shape getRacket() {
		return this.racket;
	}
	
	public void setRacket(Rectangle racket) {
		this.racket = racket;
	}

}
