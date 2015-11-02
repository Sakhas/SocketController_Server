package pt.novaims.game.model;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class SlickGame extends BasicGame {

	private Music backgroundMusic;
	private Shape pad;
	
	public SlickGame(String title) {
		super(title);
	}
	
	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.setBackground(Color.blue);
		graphics.setColor(new Color(0, 255, 255));//inside color
	    graphics.fill(pad);
	    graphics.setColor(new Color(255, 0, 0));//red, green, blue OUTLINE of circle
	    graphics.draw(pad);

		
					
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		backgroundMusic = new Music("res/sounds/background1.ogg");
		backgroundMusic.setVolume(10);
		backgroundMusic.play();
		pad = new Circle(400, 550, 10);
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		
	}
	
	public Shape getPad() {
		return this.pad;
	}
	
	public void setPad(Shape pad) {
		this.pad = pad;
	}

}
