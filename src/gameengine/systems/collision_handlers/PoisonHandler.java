package gameengine.systems.collision_handlers;

import java.util.LinkedList;
import java.util.Queue;

import gameengine.attributes.Blocked;
import gameengine.attributes.Health;
import gameengine.attributes.Poisons;
import gameengine.attributes.Poisoned;
import gameengine.attributes.interfaces.BlockedInterface;
import gameengine.attributes.interfaces.PoisonInterface;
import gameengine.entities.EntityInterface;

public class PoisonHandler implements CollisionHandlerInterface {

        private Queue<EntityInterface> collisionQueue;

        public PoisonHandler() {
                collisionQueue = new LinkedList<EntityInterface>();
        }

        @Override
        public void handle (EntityInterface thisEntity, EntityInterface thatEntity) {
            if(thisEntity.containsAttribute(Poisons.class) || thatEntity.containsAttribute(Poisons.class)) {
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
                    thisEntity.getAttribute(Blocked.class).assignBlock(false);
                }
                else{
                    if(thisEntity.containsAttribute(Poisoned.class)){
                        thisEntity.removeAttribute(thisEntity.getAttribute(Poisoned.class));
                    }
                    thisEntity.addAttribute(new Poisoned(thatEntity.getAttribute(Poisons.class).getDamage(),thatEntity.getAttribute(Poisons.class).getTotalTime()));

                }
            }
        }
}
