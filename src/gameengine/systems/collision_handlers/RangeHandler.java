package gameengine.systems.collision_handlers;

import java.util.LinkedList;
import java.util.Queue;

import gameengine.attributes.Child;
import gameengine.attributes.CleanUp;
import gameengine.attributes.Health;
import gameengine.attributes.Range;
import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.attributes.interfaces.ChildInterface;
import gameengine.entities.EntityInterface;

public class RangeHandler implements CollisionHandlerInterface {

	private Queue<EntityInterface> collisionQueue;

	public RangeHandler() {
		collisionQueue = new LinkedList<EntityInterface>();
	}

	@Override
	public void handle(EntityInterface thisEntity, EntityInterface thatEntity) {
		collisionQueue.add(thisEntity);
		collisionQueue.add(thatEntity);
	}

	@Override
	public void update(double timeElapsed) {
		while(!collisionQueue.isEmpty()) {
			EntityInterface thisEntity = collisionQueue.poll();
			EntityInterface thatEntity = collisionQueue.poll();
			giveCleanUp(thisEntity, thatEntity);
			giveCleanUp(thatEntity, thisEntity);
		}

	}

	private void giveCleanUp(EntityInterface thisEntity, EntityInterface thatEntity) {

		if(thisEntity.containsAttribute(Range.class) ||
				   thatEntity.getID() != ((ChildInterface)thisEntity.getAttribute(Child.class)).retrieveParent().getID()) {
			thisEntity.addAttribute(new CleanUp());
		}
	}

}
