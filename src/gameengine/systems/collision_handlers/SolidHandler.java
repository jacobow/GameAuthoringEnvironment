// This entire file is part of my masterpiece.
// Jacob Warner
/*
 * This class is an very good example of how a sub system works in our
 * entity component system design pattern.  As it receives events, it
 * queues the entities relevant to this particular class, in this case
 * only queuing entity pairs containing a wall attribute.  Then it updates
 * these entities, performing a rule it set out to do.
 */


package gameengine.systems.collision_handlers;

import java.util.LinkedList;
import java.util.Queue;

import gameengine.attributes.Physical;
import gameengine.attributes.Player;
import gameengine.attributes.Wall;
import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.PhysicalInterface;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;

public class SolidHandler implements CollisionHandlerInterface {

	/**
	 * This class listens for collisions involving an entity
	 * with a Wall attribute.  If one of the
	 * entities contained within an event thrown by a collision
	 * manager is passed to this sub system, the entities will
	 * not be allowed to occupy the same space in any intersection.
	 */

	private Queue<EntityInterface> collisionQueue;

	public SolidHandler() {
		collisionQueue = new LinkedList<EntityInterface>();
	}

	/**
	 * Only considers collision events involving a Wall to be added to the queue of entities to
	 * be updated later
	 */
	@Override
	public void handle(EntityInterface thisEntity, EntityInterface thatEntity) {
		if(thisEntity.containsAttribute(Wall.class) || thatEntity.containsAttribute(Wall.class)) {
			collisionQueue.add(thisEntity);
			collisionQueue.add(thatEntity);
		}
	}

	/**
	 * Returns colliding entities to pre-collision positions
	 */
	@Override
	public void update(double timeElapsed) {
		while(!collisionQueue.isEmpty()) {
			EntityInterface thisEntity = collisionQueue.poll();
			EntityInterface thatEntity = collisionQueue.poll();
			returnToPreCollisionPosition(thisEntity);
			returnToPreCollisionPosition(thatEntity);
		}

	}

	private void returnToPreCollisionPosition(EntityInterface entity) {
		if(!entity.containsAttribute(Wall.class)) {
			entity.getAttribute(Spacial.class).setX(entity.getAttribute(Physical.class).retrievePreCollisionX());
			entity.getAttribute(Spacial.class).assignY(entity.getAttribute(Physical.class).retrievePreCollisionY());
			entity.getAttribute(Spacial.class).setOrientation(entity.getAttribute(Physical.class).retrievePreCollisionOrientation());
		}
	}
}
