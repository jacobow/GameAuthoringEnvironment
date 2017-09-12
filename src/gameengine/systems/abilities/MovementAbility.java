package gameengine.systems.abilities;

import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;

/**
 * 
 * @author walker
 *
 */
abstract class MovementAbility implements AbilityInterface{
	protected SpacialInterface myMover;
	private double myCooldown;
	
	
	public MovementAbility(EntityInterface mover){
		assignControlledEntity(mover);
		myCooldown=0;
	}
	
	public MovementAbility(EntityInterface mover, double cooldown){
		assignCooldown(cooldown);
	}
	
	public void assignControlledEntity(EntityInterface mover){
		if(mover.containsAttribute(Spacial.class)) myMover = mover.getAttribute(Spacial.class);
	}
	
	public void assignCooldown(double newCooldown){
		myCooldown = newCooldown;
	}
	
	public double getCooldown(){
		return myCooldown;
	}
	
	
}
