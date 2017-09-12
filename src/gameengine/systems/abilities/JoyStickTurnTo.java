package gameengine.systems.abilities;

import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;

public class JoyStickTurnTo extends MovementAbility implements AbilityInterface{

	public JoyStickTurnTo(EntityInterface mover) {
		super(mover);
	}


	private static final double HALF_CIRCLE=180.0;


	public void activate(double timePassed, double[] extraInputs) {
            double modifier2=1;
            double modifier1=1;
            if(extraInputs.length==2){
                modifier1 = extraInputs[0];
                modifier2 = extraInputs[1];

            }
		if(myMover!=null){
			double angle=Math.toDegrees(Math.atan2(modifier2,modifier1));
			if(extraInputs[0]<0){
			//	angle+=HALF_CIRCLE;
			}
			myMover.setOrientation(angle);
		}
	}
}
