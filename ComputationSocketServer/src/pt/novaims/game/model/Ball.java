package pt.novaims.game.model;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Ball extends Circle {

	private Vector2f ballVelocity;
	
	
	public Vector2f getBallVelocity() {
		return ballVelocity;
	}

	public void setBallVelocity(Vector2f ballVelocity) {
		this.ballVelocity = ballVelocity;
	}

	public Ball(float centerPointX, float centerPointY, float radius) {
		super(centerPointX, centerPointY, radius);
		this.ballVelocity = new Vector2f((float) -0.3, (float) 0.4);		
	}
	
	
	

}
