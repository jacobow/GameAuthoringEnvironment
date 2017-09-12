package gameengine.systems.alternativesystems;

import java.util.HashSet;
import java.util.Set;
import gameengine.attributes.Blocks;
import gameengine.attributes.Blocked;
import gameengine.attributes.Damages;
import gameengine.attributes.Health;
import gameengine.attributes.Physical;
import gameengine.attributes.Poisons;
import gameengine.attributes.Poisoned;
import gameengine.attributes.Shielded;
import gameengine.attributes.Slowed;
import gameengine.attributes.interfaces.PoisonInterface;
import gameengine.attributes.interfaces.PoisonedInterface;
import gameengine.attributes.interfaces.ShieldedInterface;
import gameengine.attributes.interfaces.BlockedInterface;
import gameengine.attributes.interfaces.HealthInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.systems.alternative_handlers.AttributeRemovalHandlerInterface;
import gameengine.systems.alternative_handlers.BlockedHandler;
import gameengine.systems.alternative_handlers.DamageHandler;
import gameengine.systems.alternative_handlers.PoisonedHandler;
import gameengine.systems.alternative_handlers.ShieldedHandler;
import gameengine.systems.alternative_handlers.SlowedHandler;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;
import gameengine.utilities.LiveObserver;
import javafx.collections.SetChangeListener;
import javafx.collections.SetChangeListener.Change;

public class AttributeRemovalManager implements AttributeRemovalManagerInterface, LiveObserver{
    private Set<EntityInterface> myPhysicalEntities;
    private Set<AttributeRemovalHandlerInterface> myHandlers;
    private EntitySetSubject mySubject;


    public AttributeRemovalManager (EntitySetSubject subject) {
    	mySubject = subject;
        myPhysicalEntities = new HashSet<EntityInterface>();
        myHandlers = new HashSet<AttributeRemovalHandlerInterface>();
        register(new BlockedHandler());
        register(new PoisonedHandler());
        register(new ShieldedHandler());
        register(new SlowedHandler());
        register(new DamageHandler());
        subject.getEntities().registerObserver(this);
        amend();
    }

    protected void addPotentialPhysical(EntityInterface change) {
                if(change.containsAttribute(Physical.class)){
                        myPhysicalEntities.add(change);
                }
                else myPhysicalEntities.remove(change);
        }

    protected void removePotentialPhysical(EntityInterface change) {
            if(myPhysicalEntities.contains(change)) myPhysicalEntities.remove(change);
    }

    public void update(double timeElapsed) {
        for(EntityInterface entity: myPhysicalEntities){
            updateHandlers(timeElapsed, entity);
        }
    }

    private void register(AttributeRemovalHandlerInterface handler){
        myHandlers.add(handler);
    }

    private void updateHandlers(double timeElapsed, EntityInterface entity){
        for(AttributeRemovalHandlerInterface handler: myHandlers){
            handler.update(timeElapsed, entity);
        }
    }

	@Override
	public void amend() {
		mySubject.getEntities().forEach((block -> addPotentialPhysical(block)));
	}

}
