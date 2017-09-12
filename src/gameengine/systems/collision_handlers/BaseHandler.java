package gameengine.systems.collision_handlers;

import gameengine.attributes.Base;
import gameengine.attributes.Damages;
import gameengine.attributes.Health;
import gameengine.attributes.interfaces.BaseInterface;
import gameengine.attributes.interfaces.DamageInterface;
import gameengine.attributes.interfaces.HealthInterface;
import gameengine.entities.EntityInterface;

/**
 *
 * @author DavidYoon
 *
 */
public class BaseHandler implements CollisionHandlerInterface {

	private BaseInterface base;
	private EntityInterface interactedEntity;

	@Override
	public void handle(EntityInterface thisEntity, EntityInterface thatEntity) {
		if (thisEntity.containsAttribute(Base.class)){
			base = thisEntity.getAttribute(Base.class);
			interactedEntity = thatEntity;
		}
		else if (thatEntity.containsAttribute(Base.class)){
			base = thatEntity.getAttribute(Base.class);
			interactedEntity = thisEntity;
		}
	}


	@Override
	public void update(double timeElapsed) {
		if (base != null){
			base.addEntitiesInteracted(interactedEntity);
			base = null;
		}
	}

}
