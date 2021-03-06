package gameengine.systems.collision_handlers;

import java.util.LinkedList;
import java.util.Queue;
import gameengine.attributes.Blocks;
import gameengine.attributes.Blocked;
import gameengine.attributes.Health;
import gameengine.attributes.Player;
import gameengine.attributes.Poisons;
import gameengine.attributes.Poisoned;
import gameengine.attributes.Shields;
import gameengine.attributes.Shielded;
import gameengine.attributes.Slowed;
import gameengine.attributes.interfaces.PoisonInterface;
import gameengine.attributes.interfaces.ShieldInterface;
import gameengine.entities.EntityInterface;

public class BlockHandler implements CollisionHandlerInterface{

    private Queue<EntityInterface> collisionQueue;

    public BlockHandler() {
            collisionQueue = new LinkedList<EntityInterface>();
    }

    @Override
    public void handle (EntityInterface thisEntity, EntityInterface thatEntity) {
        if((thisEntity.containsAttribute(Blocks.class)) ||
                (thatEntity.containsAttribute(Blocks.class))) {
                collisionQueue.add(thisEntity);
                collisionQueue.add(thatEntity);
        }
    }

    @Override
    public void update (double timeElapsed) {
        while(!collisionQueue.isEmpty()) {
            EntityInterface thisEntity = collisionQueue.poll();
            EntityInterface thatEntity = collisionQueue.poll();
            poison(thisEntity, thatEntity);
            poison(thatEntity, thisEntity);
        }
    }


    private void poison(EntityInterface thisEntity, EntityInterface thatEntity) {
        if(thisEntity.containsAttribute(Health.class)){
            if(thisEntity.containsAttribute(Blocked.class)){
                thisEntity.removeAttribute(thisEntity.getAttribute(Blocked.class));
            }
            thisEntity.addAttribute(new Blocked(thatEntity.getAttribute(Shields.class).getTotalTime()));
        }
    }

}
