package gameengine.systems.collision_handlers;

import java.util.LinkedList;
import java.util.Queue;

import gameengine.attributes.Carryable;
import gameengine.attributes.Child;
import gameengine.attributes.Player;
import gameengine.attributes.interfaces.CarryableInterface;
import gameengine.entities.EntityInterface;

public class CarryableHandler implements CollisionHandlerInterface {

        private Queue<EntityInterface> collisionQueue;

        public CarryableHandler() {
                collisionQueue = new LinkedList<EntityInterface>();
        }

        @Override
        public void handle (EntityInterface thisEntity, EntityInterface thatEntity) {
            if((thisEntity.containsAttribute(Carryable.class) && !thisEntity.getAttribute(Carryable.class).beingCarried() && thatEntity.containsAttribute(Player.class))||
                    (thatEntity.containsAttribute(Carryable.class) && !thatEntity.getAttribute(Carryable.class).beingCarried() && thisEntity.containsAttribute(Player.class))) {
                    collisionQueue.add(thisEntity);
                    collisionQueue.add(thatEntity);
            }
            else if((thisEntity.containsAttribute(Carryable.class) && thisEntity.getAttribute(Carryable.class).isDropped() && thisEntity.containsAttribute(Child.class) && thisEntity.getAttribute(Child.class).retrieveParent().equals(thatEntity))||
                    (thatEntity.containsAttribute(Carryable.class) && thatEntity.getAttribute(Carryable.class).isDropped() && thatEntity.containsAttribute(Child.class) && thatEntity.getAttribute(Child.class).retrieveParent().equals(thisEntity))){
                
                setDrop(thisEntity);
                setDrop(thatEntity);
            }
        }

        @Override
        public void update (double timeElapsed) {
            while(!collisionQueue.isEmpty()) {
                EntityInterface thisEntity = collisionQueue.poll();
                EntityInterface thatEntity = collisionQueue.poll();
                pickUp(thisEntity, thatEntity);
                pickUp(thatEntity, thisEntity);
            }
        }


        private void pickUp(EntityInterface thisEntity, EntityInterface thatEntity) {
                if(thatEntity.containsAttribute(Carryable.class)){
                   thatEntity.getAttribute(Carryable.class).assignCarryable(true);
                   thatEntity.getAttribute(Carryable.class).assignDropped(false);
                    thatEntity.addAttribute(new Child(thisEntity));
                }
        }
        
        private void setDrop(EntityInterface thisEntity) {
            if(thisEntity.containsAttribute(Carryable.class)){
               thisEntity.getAttribute(Carryable.class).assignDropped(false);
            }
        }
}
