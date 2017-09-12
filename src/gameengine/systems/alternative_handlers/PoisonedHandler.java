package gameengine.systems.alternative_handlers;

import gameengine.attributes.Blocked;
import gameengine.attributes.Health;
import gameengine.attributes.Poisoned;
import gameengine.attributes.Shielded;
import gameengine.entities.EntityInterface;

public class PoisonedHandler implements AttributeRemovalHandlerInterface{
    @Override
    public void update (double timeElapsed, EntityInterface entity) {
        if(entity.containsAttribute(Poisoned.class)){
            dealDamage(timeElapsed, entity);
        }          
    }
    
    private void dealDamage(double timeElapsed, EntityInterface entity){
        if(entity.getAttribute(Poisoned.class).getTimeRemaining()>=0){
            double damage= entity.getAttribute(Poisoned.class).getDamage();
            if(entity.containsAttribute(Shielded.class) && damage>0){
                entity.getAttribute(Shielded.class).decreaseCurrentHealth(damage);
//                System.out.println(((ShieldedInterface)entity.getAttributeMap().get(Shielded.class)).getCurrentHealth());
                damage=Math.max(damage-entity.getAttribute(Shielded.class).getCurrentHealth(), 0);
            }
           entity.getAttribute(Health.class).decreaseHealth(damage);
           entity.getAttribute(Poisoned.class).decreaseRemainingTime(timeElapsed);
        }
        else{
            entity.getAttribute(Poisoned.class).setTimeRemaining(entity.getAttribute(Poisoned.class).getTotalTime());
            entity.removeAttribute(entity.getAttribute(Poisoned.class));
        }
    }
}
