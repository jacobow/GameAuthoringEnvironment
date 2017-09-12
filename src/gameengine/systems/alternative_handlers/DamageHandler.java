package gameengine.systems.alternative_handlers;

import gameengine.attributes.Damages;
import gameengine.attributes.interfaces.DamageInterface;
import gameengine.entities.EntityInterface;

public class DamageHandler implements AttributeRemovalHandlerInterface{
	@Override
	public void update (double timeElapsed, EntityInterface entity) {
		if(entity.containsAttribute(Damages.class)){
			remove(timeElapsed, entity);
		}
	}

	private void remove(double timeElapsed, EntityInterface entity){
		if((entity.getAttribute(Damages.class)).retrieveTimeRemaining()>=0){
			entity.getAttribute(Damages.class).decreaseRemainingTime(timeElapsed);
		}
		else{
			if(entity.getAttribute(Damages.class).retrieveTimeRemaining()!=-1){
				entity.getAttribute(Damages.class).assignTimeRemaining(entity.getAttribute(Damages.class).getTotalTime());
				entity.removeAttribute(entity.getAttribute(Damages.class));
			}
		}

	}

}
