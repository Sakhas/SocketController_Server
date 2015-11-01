package pt.novaims.game.model;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class SlickGame extends BasicGame {

	Music backgroundMusic;
	
	public SlickGame(String title) {
		super(title);
	}
	
	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.setColor(Color.blue);
		graphics.setBackground(Color.blue);
		graphics.drawString("Hello there", 4, 4);
	
		graphics.drawRect((float) 200, 300,20,20);
					
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		backgroundMusic = new Music("res/sounds/background1.ogg");
		backgroundMusic.setVolume(10);
		backgroundMusic.play();
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		
	}

}
