package pt.novaims.game.model;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Tile extends Rectangle{
	
	private int shotsToDestroy;
	private Graphics graphics;
	
	public Tile(float x, float y, float width, float height, int shotsToDestroy, Graphics graphs){
		super(x, y, width, height);
		this.shotsToDestroy = shotsToDestroy;
		this.graphics = graphs;
		this.updateColor();
	}
	
	public int wasShot(){
		if(shotsToDestroy < 0) {
			shotsToDestroy--;
			this.updateColor();
		}
		return shotsToDestroy;
	}
	
	public void updateColor(){
		if(shotsToDestroy == 3) {
			 graphics.setColor(Color.red);
			 graphics.fill(this);
		}
		else if(shotsToDestroy == 2) {
			 graphics.setColor(Color.orange);
			 graphics.fill(this);
		}
		else if(shotsToDestroy == 1) {
			 graphics.setColor(Color.green);
			 graphics.fill(this);
		}
			//vaihda väriä/taustaa täällä
	}
}
