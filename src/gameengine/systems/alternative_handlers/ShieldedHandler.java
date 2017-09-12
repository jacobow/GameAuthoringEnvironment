package gameengine.systems.alternative_handlers;

import gameengine.attributes.Shielded;
import gameengine.entities.EntityInterface;

public class ShieldedHandler implements AttributeRemovalHandlerInterface{
    @Override
    public void update (double timeElapsed, EntityInterface entity) {
                if(entity.containsAttribute(Shielded.class)){
                        removeShield(timeElapsed, entity);
                }
    }

    private void removeShield(double timeElapsed, EntityInterface entity){

        if(entity.getAttribute(Shielded.class).getTimeRemaining()>0 && entity.getAttribute(Shielded.class).getCurrentHealth()>=0){
                entity.getAttribute(Shielded.class).decreaseRemainingTime(timeElapsed);
        }
        else{
              entity.getAttribute(Shielded.class).setCurrentShieldHealth(entity.getAttribute(Shielded.class).getTotalShieldHealth());
            entity.getAttribute(Shielded.class).setTimeRemaining(entity.getAttribute(Shielded.class).getTotalTime());
                entity.removeAttribute(entity.getAttribute(Shielded.class));
        }
    }
}
