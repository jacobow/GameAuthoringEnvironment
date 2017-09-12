package gameengine.systems.abilities;

import gameengine.entities.EntityInterface;

/**
 * 
 * @author walker
 *
 */
public class PanLeft extends MovementAbility implements AbilityInterface {

	public PanLeft(EntityInterface mover) {
		super(mover);
	}

	public void activate(double timePassed, double[] extraInputs) {
            double modifier=1.0;
            if(extraInputs.length==2) modifier = -extraInputs[0];
		if(myMover!=null){
			myMover.setX(myMover.retrieveX().get() - modifier*timePassed*myMover.getVelocity());
		}
	}

}