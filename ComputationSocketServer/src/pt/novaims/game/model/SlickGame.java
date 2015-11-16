package pt.novaims.game.model;

import java.awt.List;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;

public class SlickGame extends BasicGame {

	final int WIDTH = 800;
	final int HEIGHT = 600;
	final int RACKET_WIDTH = 80;
	final int RACKET_HEIGHT = 10;
	final int ARRAY_ROWS = 5;
	final int ARRAY_COLUMNS = (WIDTH-100) / 5;
	final int TILE_HEIGHT = 30;
	final int TILE_WIDTH = 70;
	final int TILE_HEIGHT_LOC = 100;
	final float ballVelocity = (float) 1.6;
	
	
	private Music backgroundMusic;
	private Rectangle racket;
	private Ball ball;
	private int missCount = 0;
	private boolean gameOver = false;
	private ArrayList<Tile> tileList;
	
	public SlickGame(String title) {
		super(title);
	}
	
	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.setBackground(Color.blue);
		graphics.setColor(new Color(0, 0, 0));//inside color
	    graphics.fill(racket);
	    graphics.fill(ball);
	   // graphics.setColor(new Color(255, 0, 0));//red, green, blue OUTLINE of circle
	    graphics.draw(racket);
	    graphics.drawString("Welcome to SlickGame", 300, 20);
	    graphics.drawString("Miss count: " + Integer.toString(missCount), WIDTH - 200, 50);
	    
	    drawTileArray(graphics);
	    
	    if(gameOver) {
	    	this.init(container);
	    	gameOver = false;
	    	missCount++;
	    }	
					
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		backgroundMusic = new Music("res/sounds/background1.ogg");
		backgroundMusic.setVolume(10);
		backgroundMusic.play();
		racket = new RoundedRectangle(WIDTH / 2 - 40, 550, RACKET_WIDTH, RACKET_HEIGHT, 3);
				
		ball = new Ball((int) Math.ceil(Math.random()*WIDTH), TILE_HEIGHT*ARRAY_ROWS + TILE_HEIGHT_LOC + 20, 6);
		org.newdawn.slick.geom.Vector2f ballVelocityVector = new org.newdawn.slick.geom.Vector2f();
		ballVelocityVector.x = (float) ballVelocity;
		ballVelocityVector.y = (float) ballVelocity;
		ball.setBallVelocity(ballVelocityVector);
		initTileArray(container.getGraphics());
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {

		ball.setLocation(ball.getX() + ball.getBallVelocity().getX(), ball.getY() + ball.getBallVelocity().getY());
		
		if (ball.getMinX() <= 0) {
			   ball.getBallVelocity().x = -ball.getBallVelocity().getX();
			}
		if (ball.getMaxX() >= WIDTH) {
		   ball.getBallVelocity().x = -ball.getBallVelocity().getX();
		}
		
		if (ball.getMinY() <= 0)
		   ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		if (ball.getMaxY() >= HEIGHT)
		   ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		
		if(ball.intersects(racket)) {
			ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		}
		
		if(ball.getLocation().y < TILE_HEIGHT*ARRAY_ROWS + TILE_HEIGHT_LOC) {
			checkBallInteractionWithTile();
		}
		
		if(ball.getLocation().y >= HEIGHT - 20) {
			gameOver = true;
		}
	}
	
	public void initTileArray(Graphics graphics) {
		//tileArray = new Rectangle[ARRAY_ROWS][ARRAY_COLUMNS];
		tileList = new ArrayList<>();
		float tileWidthLoc = 50;
		int tileHeightLoc = 100;
		for(int i = 0; i < ARRAY_ROWS; i++) {
			for(int j = 0; j < ARRAY_COLUMNS && tileWidthLoc < WIDTH -50; j++) {			
				//tileArray[i][j] = new Rectangle((float)tileWidthLoc, (float)tileHeightLoc, (float)TILE_SIZE, (float)TILE_SIZE);
				tileList.add(new Tile((float)tileWidthLoc, (float)tileHeightLoc, (float)TILE_WIDTH, (float)TILE_HEIGHT, j, graphics));
				//tileList.add(new Rectangle((float)tileWidthLoc, (float)tileHeightLoc, (float)TILE_WIDTH, (float)TILE_HEIGHT));
				tileWidthLoc += TILE_WIDTH;
			}
			tileHeightLoc += TILE_HEIGHT;
			tileWidthLoc = 50;
		}
	} 
	
	public void drawTileArray(Graphics graphics) {
		/*
		for(int i = 0; i < ARRAY_ROWS; i++) {
			for(int j = 0; j < ARRAY_COLUMNS; j++) {
				if(tileArray[i][j] != null)
					graphics.draw(tileArray[i][j]);
			}
		}
		*/
		
		for(Tile t : tileList){
			if(t != null)
				graphics.draw(t);
		}
	}
	
	public void checkBallInteractionWithTile() {
		ArrayList <Tile> toRemove = new ArrayList<>();
		/*
		for(int i = 0; i < ARRAY_ROWS; i++) {
			for(int j = 0; j < ARRAY_COLUMNS; j++) {
				if(tileArray[i][j] != null){
					if(tileArray[i][j].intersects(ball)) {
						//Poista/tyhjennä/disabloi tiili.
						//tileArray[i][j] = null;
						//tileArray.remove
						System.out.println("osuma");
						ball.getBallVelocity().x = -ball.getBallVelocity().getX();
						ball.getBallVelocity().y = -ball.getBallVelocity().getY();
					}
				}
				
			}
		}
		*/
		for(Tile t : tileList){
			if(t.intersects(ball)){
				ball.getBallVelocity().y = -ball.getBallVelocity().getY();
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
