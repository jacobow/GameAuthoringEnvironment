package gameengine.systems.abilities;

import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;

/**
 * 
 * @author walker
 *
 */
public class MoveForward extends MovementAbility implements AbilityInterface{

	public MoveForward(EntityInterface mover) {
		super(mover);
	}

	public void activate(double timePassed, double[] extraInputs) {
		double modifier=1.0;
                if(extraInputs.length==2) modifier = -extraInputs[0];

		if(myMover!=null){
			myMover.setX(myMover.retrieveX().get()+modifier*myMover.getVelocity()*timePassed*Math.cos(Math.PI/180.0 * myMover.retrieveOrientation().get()));
			myMover.assignY(myMover.retrieveY().get()+modifier*myMover.getVelocity()*timePassed*Math.sin(Math.PI/180.0 * myMover.retrieveOrientation().get()));
		}
	}
}