package gameengine.systems.alternativesystems;

import java.util.HashSet;
import java.util.Set;

import gameengine.attributes.Carryable;
import gameengine.attributes.Child;
import gameengine.attributes.Poisons;
import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.ChildInterface;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.systems.alternative_handlers.carryable_handlers.CarryableHandlerInterface;
import gameengine.systems.alternative_handlers.carryable_handlers.DropHandler;
import gameengine.utilities.LiveObserver;
import javafx.collections.SetChangeListener;
import javafx.collections.SetChangeListener.Change;

public class CarryableManager implements CarryableManagerInterface, LiveObserver{
    private Set<EntityInterface> myCarryables;
    private CarryableHandlerInterface handler;
	private EntitySetSubject mySubject;

    public CarryableManager (EntitySetSubject subject) {
        myCarryables = new HashSet<EntityInterface>();
        mySubject = subject;
        subject.getEntities().registerObserver(this);
        amend();
        register(new DropHandler());
    }

    protected void addPotentialCarryable(EntityInterface change) {
                if(change.containsAttribute(Carryable.class)){
                        myCarryables.add(change);
                }
                else myCarryables.remove(change);
        }

        protected void removePotentialCarryable(EntityInterface change) {
                if(myCarryables.contains(change)) myCarryables.remove(change);
        }

        public void update(double timeElapsed) {
            for(EntityInterface entity: myCarryables){
                //move carryables with owner
                if(entity.containsAttribute(Carryable.class) && entity.containsAttribute(Child.class)){

                    if(!entity.getAttribute(Carryable.class).isDropped()){
                        moveCarryable(entity);
                    }
                    else{
                        dropCarryable(entity);
                    }
                }
            }
        }

        public void moveCarryable(EntityInterface entity){
            entity.getAttribute(Carryable.class).assignDropped(true);
        	entity.getAttribute(Spacial.class).setX(
        			entity.getAttribute(Child.class).retrieveParent().getAttribute(Spacial.class).retrieveX().get());
            entity.getAttribute(Spacial.class).assignY(
            		entity.getAttribute(Child.class).retrieveParent().getAttribute(Spacial.class).retrieveY().get());

        }

        public void dropCarryable(EntityInterface entity){
            handler.update(entity);
        }

        @Override
        public void register (CarryableHandlerInterface handler) {

            this.handler=handler;
        }

		@Override
		public void amend() {
			mySubject.getEntities().forEach((carryable -> addPotentialCarryable(carryable)));
		}
}