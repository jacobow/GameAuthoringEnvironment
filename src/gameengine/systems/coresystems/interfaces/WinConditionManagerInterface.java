package gameengine.systems.coresystems.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.wincondition_handlers.WinConditionInterface;
import javafx.beans.property.BooleanProperty;

public interface WinConditionManagerInterface {
	
	public void register(WinConditionInterface winCondition);
	
	public BooleanProperty isGameOver();
	
	public EntityInterface getWinner();
	
}
