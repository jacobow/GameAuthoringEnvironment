package gameengine.systems.wincondition_handlers;

import gameengine.entities.EntityInterface;
import gameengine.systems.interfaces.SubSystemInterface;

/**
 * @author DavidYoon
 *
 */
public interface WinConditionInterface extends SubSystemInterface{

	public EntityInterface getWinner();

	public boolean isGameOver();

}
