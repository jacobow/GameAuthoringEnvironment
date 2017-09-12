package gameengine.systems.alternative_handlers.carryable_handlers;

import gameengine.attributes.Carryable;
import gameengine.attributes.Child;
import gameengine.attributes.CleanUp;
import gameengine.attributes.interfaces.CarryableInterface;
import gameengine.entities.EntityInterface;

public class CleanUpCarryableHandler implements CarryableHandlerInterface{
    @Override
    public void update (EntityInterface entity) {
        entity.removeAttribute(entity.getAttribute(Child.class));
        entity.addAttribute(new CleanUp());
    }

}
