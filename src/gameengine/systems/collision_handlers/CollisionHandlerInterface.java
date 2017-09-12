package gameengine.systems.collision_handlers;

import gameengine.entities.EntityInterface;
import gameengine.systems.coresystems.interfaces.SystemsInterface;
import gameengine.systems.interfaces.SubSystemInterface;

public interface CollisionHandlerInterface extends SystemsInterface, SubSystemInterface {

	void handle(EntityInterface thisEntity, EntityInterface thatEntity);

	void update(double timeElapsed);

}