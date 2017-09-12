package gameengine.systems.collision_handlers;
import java.util.LinkedList;
import java.util.Queue;

import gameengine.attributes.Physical;
import gameengine.attributes.Player;
import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.PhysicalInterface;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;
public class PlayerHandler implements CollisionHandlerInterface {

	private Queue<EntityInterface> collisionQueue;
	public PlayerHandler() {
		collisionQueue = new LinkedList<EntityInterface>();
	}
	@Override
	public void handle(EntityInterface thisEntity, EntityInterface thatEntity) {
		if(thisEntity.containsAttribute(Player.class)&&
				thatEntity.containsAttribute(Player.class)) {
			collisionQueue.add(thisEntity);
			collisionQueue.add(thatEntity);

		}
	}
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
		entity.getAttribute(Spacial.class).setX(entity.getAttribute(Physical.class).retrievePreCollisionX());
		entity.getAttribute(Spacial.class).assignY(entity.getAttribute(Physical.class).retrievePreCollisionY());
		entity.getAttribute(Spacial.class).setOrientation(entity.getAttribute(Physical.class).retrievePreCollisionOrientation());
	}


}