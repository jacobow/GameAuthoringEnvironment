package gameengine.systems.collision_handlers;

import java.util.LinkedList;
import java.util.Queue;
import gameengine.attributes.Blocked;
import gameengine.attributes.Health;
import gameengine.attributes.Player;
import gameengine.attributes.Poisons;
import gameengine.attributes.Shields;
import gameengine.attributes.Shielded;
import gameengine.attributes.Slows;
import gameengine.attributes.Slowed;
import gameengine.attributes.interfaces.BlockedInterface;
import gameengine.attributes.interfaces.ShieldInterface;
import gameengine.attributes.interfaces.SlowInterface;
import gameengine.entities.EntityInterface;

public class SlowHandler implements CollisionHandlerInterface{

    private Queue<EntityInterface> collisionQueue;

    public SlowHandler() {
            collisionQueue = new LinkedList<EntityInterface>();
    }

    @Override
    public void handle (EntityInterface thisEntity, EntityInterface thatEntity) {
    	if((thisEntity.containsAttribute(Slows.class)) ||
                (thatEntity.containsAttribute(Slows.class))) {
                collisionQueue.add(thisEntity);
                collisionQueue.add(thatEntity);
        }
    }

    @Override
    public void update (double timeElapsed) {
        while(!collisionQueue.isEmpty()) {
            EntityInterface thisEntity = collisionQueue.poll();
            EntityInterface thatEntity = collisionQueue.poll();

            slow(thisEntity, thatEntity);
            slow(thatEntity, thisEntity);
        }
    }


    private void slow(EntityInterface thisEntity, EntityInterface thatEntity) {
        if(thisEntity.containsAttribute(Player.class)){
            if(thisEntity.containsAttribute(Blocked.class)){
                thisEntity.getAttribute(Blocked.class).assignBlock(false);
            }
            else{
                if(thisEntity.containsAttribute(Slowed.class)){
                    thisEntity.removeAttribute(thisEntity.getAttribute(Slowed.class));
                }
                thisEntity.addAttribute(new Slowed(thatEntity.getAttribute(Slows.class).getSpeedModifier(),thatEntity.getAttribute(Slows.class).getTotalTime()));
            }
        }
    }
}
