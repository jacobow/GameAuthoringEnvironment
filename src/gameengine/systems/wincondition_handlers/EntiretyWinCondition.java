package gameengine.systems.wincondition_handlers;

import gameengine.attributes.Base;
import gameengine.attributes.interfaces.BaseInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;

/**
 * @author DavidYoon
 *
 */
public class EntiretyWinCondition extends GeneralWinCondition implements WinConditionInterface {
	
	private EntityInterface winner;
	private BaseInterface base;

	public EntiretyWinCondition(EntitySetSubject entities) {
		super(entities);
	}

	@Override
	public EntityInterface getWinner() {
		return winner;
	}

	@Override
	public boolean isGameOver() {
		if (!remainingBaseEntities.isEmpty()) {
			for (EntityInterface entity: remainingBaseEntities) {
				base = entity.getAttribute(Base.class);
				if (base.interacted()){
					remainingBaseEntities.remove(entity);
					winner = base.retrieveSeizer();
				}
			}	
			return false;
		}
		else return true;
	}


}
