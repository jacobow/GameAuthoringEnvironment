package gameengine.systems.abilities;

import gameengine.entities.EntityInterface;

/**
 * 
 * @author walker
 *
 */
public class PanDown extends MovementAbility implements AbilityInterface{


	public PanDown(EntityInterface mover) {
		super(mover);

	}

	public void activate(double timePassed, double[] extraInputs) {
            double modifier=1.0;
            if(extraInputs.length==2) modifier = extraInputs[1];
		if(myMover!=null){
			myMover.assignY(myMover.retrieveY().get() + modifier*timePassed*myMover.getVelocity());
		}
	}
}
