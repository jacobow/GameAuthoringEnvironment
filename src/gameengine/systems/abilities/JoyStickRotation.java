package gameengine.systems.abilities;

import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;

public class JoyStickRotation extends MovementAbility implements AbilityInterface{

	public JoyStickRotation(EntityInterface mover) {
		super(mover);
	}

	public void activate(double timePassed, double[] extraInputs) {
		double modifier=1;
		if(extraInputs.length==2){
			modifier = -extraInputs[1];
		}
		if(myMover!=null){
			myMover.setOrientation(myMover.retrieveOrientation().get() - modifier*timePassed*myMover.getVelocity());
		}
	}
}
