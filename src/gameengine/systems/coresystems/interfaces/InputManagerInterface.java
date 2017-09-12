package gameengine.systems.coresystems.interfaces;

import java.util.List;
import java.util.Map;

import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.AbilityInterface;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public interface InputManagerInterface {
	abstract Map<KeyCode,AbilityInterface> getKeyToAbilityMap();
	
    abstract void swapKeys(KeyCode oldKey, KeyCode newKey);

	abstract void setOnClickEntity(EntityInterface newClickMe);

	abstract void keyboardButtonPressed(KeyCode keyPressed);
	
	abstract void keyboardButtonReleased(KeyCode keyReleased);
	
	abstract void mouseButtonPressed(MouseEvent mousePressedEvent);
	
	abstract void mouseButtonReleased(MouseEvent mouseReleasedEvent);

	abstract void addAbility(KeyCode key, List<EntityInterface> entity, AbilityInterface ability);
	
}
