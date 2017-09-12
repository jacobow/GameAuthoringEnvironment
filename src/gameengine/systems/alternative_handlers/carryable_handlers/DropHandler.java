package gameengine.systems.alternative_handlers.carryable_handlers;

import gameengine.attributes.Carryable;
import gameengine.attributes.Child;
import gameengine.attributes.interfaces.CarryableInterface;
import gameengine.entities.EntityInterface;

public class DropHandler implements CarryableHandlerInterface{
    @Override
    public void update (EntityInterface entity) {
        entity.removeAttribute(entity.getAttribute(Child.class));
    }

}
