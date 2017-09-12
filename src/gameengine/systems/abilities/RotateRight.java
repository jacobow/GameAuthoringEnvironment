package gameengine.systems.abilities;

import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;
/**
 * 
 * @author walker
 *
 */
public class RotateRight extends MovementAbility implements AbilityInterface{

	public RotateRight(EntityInterface mover) {
		super(mover);
	}

	public void activate(double timePassed, double[] extraInputs) {
		double modifier=1.0;
		if(myMover!=null){	
			myMover.setOrientation(myMover.retrieveOrientation().get() + modifier*timePassed*myMover.getVelocity());
		}
	}

}
