package gameengine.systems.wincondition_handlers;

import gameengine.attributes.Base;
import gameengine.attributes.Health;
import gameengine.attributes.interfaces.BaseInterface;
import gameengine.attributes.interfaces.HealthInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;

/**
 * @author DavidYoon
 *
 */
public class ZeroHealthWinCondition extends GeneralWinCondition implements WinConditionInterface {

	private EntityInterface winner;
	private BaseInterface base;
	private HealthInterface health;
	
	public ZeroHealthWinCondition(EntitySetSubject entities) {
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
				health = entity.getAttribute(Health.class);
				if (health.getHealth() <= 0){
					base.assignSeized(true);
					calculateWinner();
					
					return true;
				}
			}
		}
		return false;
	}

	private void calculateWinner() {
		winner = base.retrieveSeizer();
	}

}
