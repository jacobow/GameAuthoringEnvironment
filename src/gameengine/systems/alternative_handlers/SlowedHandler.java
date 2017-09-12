package gameengine.systems.alternative_handlers;

import gameengine.attributes.Slowed;
import gameengine.attributes.Spacial;
import gameengine.entities.EntityInterface;

public class SlowedHandler implements AttributeRemovalHandlerInterface{
    @Override
    public void update (double timeElapsed, EntityInterface entity) {
        if(entity.containsAttribute(Slowed.class)){
            slowDown(timeElapsed, entity);
        }        
    }

    private void slowDown(double timeElapsed, EntityInterface entity){
        if((entity.getAttribute(Slowed.class)).getTimeRemaining()>=0){
            entity.getAttribute(Spacial.class).assignSpeedModifier(entity.getAttribute(Slowed.class).getSpeedModifier());
            entity.getAttribute(Slowed.class).decreaseRemainingTime(timeElapsed);

        }
        else{
            entity.getAttribute(Slowed.class).setTimeRemaining(entity.getAttribute(Slowed.class).getTotalTime());
            entity.getAttribute(Spacial.class).assignSpeedModifier(1);
            entity.removeAttribute(entity.getAttribute(Slowed.class));
        }
    }
}
