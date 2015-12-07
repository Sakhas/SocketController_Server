package pt.novaims.game.model;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Tile extends Rectangle{
	
	private int shotsToDestroy;
	private Graphics graphics;
	private Color currentColor;
	private float x;
	private float y;
	private float width;
	private float height;
	
	public Tile(float x, float y, float width, float height, int shotsToDestroy, Graphics graphs){
		super(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.shotsToDestroy = shotsToDestroy;
		this.graphics = graphs;
		this.updateColor();
	}
	
	public int wasShot(){
		if(shotsToDestroy > 0) {
			shotsToDestroy--;
			this.updateColor();
		}
		//System.out.println("shotsLeft: " + shotsToDestroy);
		return shotsToDestroy;
	}
	
	public float[] getRect(){
		float[] dimensionArray = {x, y, width, height};
		return dimensionArray;
	}
	
	public void updateColor(){
		if(shotsToDestroy == 3) {
			 graphics.setColor(Color.red);
			 graphics.fill(this);
			 currentColor = Color.red;
		}
		else if(shotsToDestroy == 2) {
			 graphics.setColor(Color.orange);
			 graphics.fill(this);
			 currentColor = Color.orange;
		}
		else if(shotsToDestroy == 1) {
			 graphics.setColor(Color.green);
			 graphics.fill(this);
			 currentColor = Color.green;
		}
			//vaihda väriä/taustaa täällä
	}
	
	public Color getCurrentColor(){
		return currentColor;
	}
}
