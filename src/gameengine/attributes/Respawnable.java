package gameengine.attributes;

import gameengine.attributes.interfaces.RespawnableInterface;

/**
 * 
 * @author jacob
 * @author walker
 *
 */
public class Respawnable implements RespawnableInterface{

	private double myLives;
	private double x;
	private double y;

	public Respawnable(){
		myLives = 0;
	}

	public Respawnable(double lives) {
		myLives = lives;
	}

	public void setLives(double newLives){
		myLives = newLives;
	}
	
	public double getLives(){
		return myLives;
	}

	public void loseLife() {
		myLives--;
	}

	public double lifeCount() {
		return myLives;
	}

	public void setRespawnX(double x) {
		this.x = x;
	}

	public void setRespawnY(double y) {
		this.y = y;
	}

	public double getRespawnX() {
		return x;
	}

	public double getRespawnY() {
		return y;
	}

}
