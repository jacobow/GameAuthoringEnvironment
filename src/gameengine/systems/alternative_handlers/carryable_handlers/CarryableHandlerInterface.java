package gameengine.systems.alternative_handlers.carryable_handlers;

import java.util.Set;
import gameengine.entities.EntityInterface;
import gameengine.systems.interfaces.SubSystemInterface;

public interface CarryableHandlerInterface extends SubSystemInterface{
    public void update(EntityInterface entity);
}
