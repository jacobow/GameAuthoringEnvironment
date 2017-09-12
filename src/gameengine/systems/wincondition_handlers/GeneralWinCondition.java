package gameengine.systems.wincondition_handlers;

import java.util.HashSet;
import java.util.Set;

import gameengine.attributes.Base;
import gameengine.attributes.Player;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.utilities.LiveObserver;

/**
 * @author DavidYoon
 *
 */
abstract class GeneralWinCondition implements WinConditionInterface, LiveObserver {

	protected Set<EntityInterface> remainingBaseEntities;
	protected Set<EntityInterface> remainingPlayerEntities;
	protected EntityInterface winner;
	private EntitySetSubject mySubject;

	public GeneralWinCondition(EntitySetSubject subject){
		mySubject = subject;
		remainingBaseEntities = new HashSet<EntityInterface>();
		remainingPlayerEntities = new HashSet<EntityInterface>();
		amend();
	}

	public void removePotentialPlayer(EntityInterface change) {
		if(remainingPlayerEntities.contains(change)) remainingPlayerEntities.remove(change);
	}

	public void addPotentialPlayer(EntityInterface change) {
		if(change.containsAttribute(Player.class)) remainingPlayerEntities.add(change);
	}

	public void removePotentialBase(EntityInterface change) {
		if(remainingBaseEntities.contains(change)) remainingBaseEntities.remove(change);
	}

	public void addPotentialBase(EntityInterface change) {
		if(change.containsAttribute(Base.class)) remainingBaseEntities.add(change);
	}

	public EntityInterface getWinner() {
		return winner;
	}

	@Override
	public void amend() {
		mySubject.getEntities().forEach((block -> addPotentialPlayer(block)));
		mySubject.getEntities().forEach((block -> addPotentialBase(block)));
	}

	public abstract boolean isGameOver();
}
