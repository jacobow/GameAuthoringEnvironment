package gameengine.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.wincondition_handlers.WinConditionInterface;
import gameengine.utilities.LiveSet;

/**
 * 
 * @author walker
 *
 */
public interface WinConditionSubject {

	public LiveSet<WinConditionInterface> getWinConditions();

	public LiveSet<EntityInterface> getEntities();
}
