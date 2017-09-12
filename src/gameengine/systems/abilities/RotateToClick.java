package gameengine.systems.abilities;

import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;
/**
 * 
 * @author walker
 *
 */
public class RotateToClick extends MovementAbility implements AbilityInterface{

	double myCooldown;

	public RotateToClick(EntityInterface mover){
		super(mover);
	}

	public RotateToClick(EntityInterface mover, double cooldown){
		super(mover,cooldown);
	}

	public void activate(double timePassed, double[] extraInputs) {
		if(myMover!=null){
			double adjustedWidth = myMover.retrieveShape().getLayoutBounds().getWidth()/2.0;
			double adjustedHeight = myMover.retrieveShape().getLayoutBounds().getHeight()/2.0;
			double clickedX=0.0;
			double clickedY=0.0;
			if(extraInputs.length==2){
				clickedX = extraInputs[0];
				clickedY = extraInputs[1];
			}
			myMover.setOrientation(-90+180/Math.PI*(Math.atan2((clickedX-(myMover.retrieveX().get()+adjustedWidth)),((myMover.retrieveY().get()+adjustedHeight) - clickedY))));
		}
	}

}
