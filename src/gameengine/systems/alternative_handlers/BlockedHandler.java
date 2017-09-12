package gameengine.systems.alternative_handlers;

import gameengine.attributes.Blocked;
import gameengine.entities.EntityInterface;

public class BlockedHandler implements AttributeRemovalHandlerInterface{
    
    @Override
    public void update (double timeElapsed, EntityInterface entity) {
        if(entity.containsAttribute(Blocked.class)){
            block(timeElapsed, entity);
        }          
    }

    private void block(double timeElapsed, EntityInterface entity){
        if(entity.getAttribute(Blocked.class).retrieveTimeRemaining()>=0 && entity.getAttribute(Blocked.class).retrieveBlock()){
            entity.getAttribute(Blocked.class).decreaseRemainingTime(timeElapsed);
        }
        else{
            entity.getAttribute(Blocked.class).assignBlock(true);
            entity.getAttribute(Blocked.class).assignTimeRemaining(entity.getAttribute(Blocked.class).getTotalTime());
            entity.removeAttribute(entity.getAttribute(Blocked.class));
        }
    }



}
