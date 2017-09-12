package gameengine.systems.coresystems.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;


public interface CollisionManagerInterface {

	/**
	 *
	 * Manages the physical entities that collide into
	 * each other.  Collision handlers can listen for
	 * these collision events.
	 *
	 * @author Jacob Warner
	 */

	/**
	 * registers a CollisionHandler to listen to collision events
	 * @param handler
	 */
	void register(CollisionHandlerInterface handler);

	/**
	 * Notifies the registered CollisionHandlers of a collision
	 * @param thisEntity the first physical entity in the collison
	 * @param thatEntity the second physical entity in the collison
	 */
	void notifyCollisionHandlers(EntityInterface thisEntity, EntityInterface thatEntity);

	/**
	 * Stores the last position where the entity was not colliding inside
	 * of its physical attribute
	 * @param entity The entity in question
	 */
	void setPreCollisionPosition(EntityInterface entity);

}
