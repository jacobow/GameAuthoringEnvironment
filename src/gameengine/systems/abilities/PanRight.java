package gameengine.systems.abilities;

import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;

/**
 * 
 * @author walker
 *
 */
public class PanRight extends MovementAbility implements AbilityInterface {

	public PanRight(EntityInterface mover) {
		super(mover);
	}

	public void activate(double timePassed, double[] extraInputs) {
            double modifier=1.0;
            if(extraInputs.length==2) modifier = extraInputs[0];
		if(myMover!=null){
			myMover.setX(myMover.retrieveX().get() + modifier*timePassed*myMover.getVelocity());
		}
	}

}
