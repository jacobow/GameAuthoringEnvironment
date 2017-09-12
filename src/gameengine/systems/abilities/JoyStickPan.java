package gameengine.systems.abilities;

import gameengine.entities.EntityInterface;

public class JoyStickPan extends MovementAbility implements AbilityInterface{

	public JoyStickPan(EntityInterface mover) {
		super(mover);
	}

	public void activate(double timeElapsed, double[] extraInputs) {
	    double modifier2=1;
	    double modifier1=1;

	    if(extraInputs.length==2){
	        modifier1 = extraInputs[0];
                modifier2 = extraInputs[1];
	    }
		if(myMover!=null){
	                

			myMover.setX(myMover.retrieveX().get()+myMover.getVelocity()*timeElapsed*modifier1);
			myMover.assignY(myMover.retrieveY().get()+myMover.getVelocity()*timeElapsed*modifier2);
		}
	}

}
