package gameauthoring.components.selectors.abstractselectors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gameauthoring.components.entitycreation.EntityCreator;
import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.AbilityInterface;
import gameengine.utilities.HandHeld;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public interface AbilitySelectorInterface {

	/**
	 * purpose: provides access to the VBox that contains the selector content
	 * @return the selectors main VBox
	 */
	public VBox getMyContentArea() ;

	/**
	 * purpose: initializes the selector and the corresponding ability items
	 * for selection by the user
	 */
	public void initialize() ;
	
	/**
	 * purpose: creates a listener that helps the pop-up to be responsive and resizable
	 * @return the change listener that acts when the pop-up is resized
	 */
	public ChangeListener<Number> getWidthListener() ;
	
	/**
	 * purpose: provides access to the abilities that the use has selected to be added to
	 * their entity
	 * @return the list of selected abilities
	 */
	public List<AbilityInterface> getAbilities() ;
	
	/**
	 * purpose: provides access to the key codes that the use has selected to correspond to
	 * the abilities that they have added to their entity
	 * @return the list of selected key codes in the same order as the abilities
	 */
	public List<KeyCode> getKeyCodes();

	/**
	 * purpose: adds the HandHelds that contain information about any controller inputs '
	 * mapped to abilities by the user to the world for use in the engine
	 * assumptions: assumes that myHandHelds has been initialized
	 */
	public void getControllerCodes() ;
	
	/**
	 * purpose: compiles all of the users selected abilities from their AbiltiyMappings into
	 * the key codes list, abilities list, and handheld objects for use by outside classes 
	 */
	public void compileAbilities() ;
	
	/**
	 * purpose: allows outside classes to update the entity that the selector adds abilities to
	 * @param entity the new entity to be added to 
	 */
	public void setMyEntity(EntityInterface entity);

	/**
	 * purpose: to create and provide access to the ability selection VBox
	 * for use in other classes
	 * @return the VBox containing the necessary components for ability selection
	 */
	public VBox getAbilitySection();

	
}
