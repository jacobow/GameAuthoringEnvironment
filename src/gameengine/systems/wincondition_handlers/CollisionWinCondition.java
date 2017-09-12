package gameengine.systems.wincondition_handlers;

import gameengine.attributes.Base;
import gameengine.attributes.interfaces.BaseInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;

/**
 * @author DavidYoon
 *
 */
public class CollisionWinCondition extends GeneralWinCondition implements WinConditionInterface {
	private EntityInterface winner;
	private BaseInterface base;
	
	public CollisionWinCondition(EntitySetSubject entities) {
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
				if (!base.retrieveEntitiesInteracted().isEmpty()){
					winner = base.retrieveEntitiesInteracted().get(0);
					return true;
				}
			}
			return false;
		}
		else return false;
	}

}
