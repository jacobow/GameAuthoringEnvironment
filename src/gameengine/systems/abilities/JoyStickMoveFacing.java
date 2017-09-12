package gameengine.systems.abilities;

import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;

public class JoyStickMoveFacing extends MovementAbility implements AbilityInterface{

        public JoyStickMoveFacing(EntityInterface mover) {
                super(mover);
        }

        public void activate(double timePassed, double[] extraInputs) {
            double modifier2=1;
            double modifier1=1;
            if(extraInputs.length==2){
                modifier1 = extraInputs[0];
                modifier2 = extraInputs[1];

            }
                if(myMover!=null){
                        myMover.setX(myMover.retrieveX().get()-modifier2*myMover.getVelocity()*timePassed*Math.cos(Math.PI/180.0 * myMover.retrieveOrientation().get()));  
                        myMover.assignY(myMover.retrieveY().get()-modifier2*myMover.getVelocity()*timePassed*Math.sin(Math.PI/180.0 * myMover.retrieveOrientation().get()));
                        myMover.setX(myMover.retrieveX().get()-modifier1*myMover.getVelocity()*timePassed*Math.sin(Math.PI/180.0 * myMover.retrieveOrientation().get()));
                        myMover.assignY(myMover.retrieveY().get()+modifier1*myMover.getVelocity()*timePassed*Math.cos(Math.PI/180.0 * myMover.retrieveOrientation().get()));

                
                }
        }
}
