package gameengine.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;
import gameengine.utilities.LiveSet;

/**
 * 
 * @author jacob
 * @author walker
 *
 */
public interface CollisionSubject {

	public LiveSet<CollisionHandlerInterface> getCollisionSubsystems();

	public LiveSet<EntityInterface> getEntities();
}
