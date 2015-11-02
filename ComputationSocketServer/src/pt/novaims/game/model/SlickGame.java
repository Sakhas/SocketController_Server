package pt.novaims.game.model;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
<<<<<<< HEAD
import org.newdawn.slick.geom.Circle;
=======
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713
import org.newdawn.slick.geom.Shape;

public class SlickGame extends BasicGame {

<<<<<<< HEAD
	private Music backgroundMusic;
	private Shape pad;
=======
	final int WIDTH = 800;
	final int HEIGHT = 600;
	
	private Music backgroundMusic;
	private Rectangle racket;
	private Ball ball;
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713
	
	public SlickGame(String title) {
		super(title);
	}
	
	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.setBackground(Color.blue);
<<<<<<< HEAD
		graphics.setColor(new Color(0, 255, 255));//inside color
	    graphics.fill(pad);
	    graphics.setColor(new Color(255, 0, 0));//red, green, blue OUTLINE of circle
	    graphics.draw(pad);
=======
		graphics.setColor(new Color(0, 0, 0));//inside color
	    graphics.fill(racket);
	    graphics.fill(ball);
	   // graphics.setColor(new Color(255, 0, 0));//red, green, blue OUTLINE of circle
	    graphics.draw(racket);
	    graphics.drawString("Welcome to SlickGame", 300, 50);
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713

		
					
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		backgroundMusic = new Music("res/sounds/background1.ogg");
		backgroundMusic.setVolume(10);
		backgroundMusic.play();
<<<<<<< HEAD
		pad = new Circle(400, 550, 10);
=======
		racket = new RoundedRectangle(WIDTH / 2 - 40, 550, 80, 10, 3);
		ball = new Ball(WIDTH / 2, HEIGHT / 2, 6);
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
<<<<<<< HEAD
		
		
	}
	
	public Shape getPad() {
		return this.pad;
	}
	
	public void setPad(Shape pad) {
		this.pad = pad;
=======

		ball.setLocation(ball.getX() + ball.getBallVelocity().getX(), ball.getY() + ball.getBallVelocity().getY());
			
		if (ball.getMinY() <= 0)
		   ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		if (ball.getMaxY() >= HEIGHT)
		   ball.getBallVelocity().y = -ball.getBallVelocity().getY();
		
	}
	
	public Shape getRacket() {
		return this.racket;
	}
	
	public void setRacket(Rectangle racket) {
		this.racket = racket;
>>>>>>> 54054d1bf66de1316fe030f524fa2be9181aa713
	}

}
