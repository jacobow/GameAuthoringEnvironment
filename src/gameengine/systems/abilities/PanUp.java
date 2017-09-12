package gameengine.systems.abilities;

import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;

/**
 * 
 * @author walker
 *
 */
public class PanUp extends MovementAbility implements AbilityInterface{


	public PanUp(EntityInterface mover) {
		super(mover);
	}

	public void activate(double timePassed, double[] extraInputs) {
            double modifier=1.0;
            if(extraInputs.length==2) modifier = -extraInputs[1];
		if(myMover!=null){
			myMover.assignY(myMover.retrieveY().get() - modifier*timePassed*myMover.getVelocity());
		}
	}

}
