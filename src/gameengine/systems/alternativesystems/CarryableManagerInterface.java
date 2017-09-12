package gameengine.systems.alternativesystems;

import gameengine.entities.EntityInterface;
import gameengine.systems.alternative_handlers.carryable_handlers.CarryableHandlerInterface;
import gameengine.systems.coresystems.interfaces.SystemsInterface;
import gameengine.systems.interfaces.SubSystemInterface;

public interface CarryableManagerInterface extends SystemsInterface {

    public void register(CarryableHandlerInterface handler);

    public void update(double timeElapsed);

    public void moveCarryable(EntityInterface entity);

    public void dropCarryable(EntityInterface entity);
}
