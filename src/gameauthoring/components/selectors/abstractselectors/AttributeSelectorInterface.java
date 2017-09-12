package gameauthoring.components.selectors.abstractselectors;

import java.util.List;

import gameengine.attributes.interfaces.AttributeInterface;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.VBox;

public interface AttributeSelectorInterface {

	/**
	 * purpose: creates the menu of attributes from which the user can choose
	 * which attributes to add to their entity
	 * @param existingAttributes the list of attributes that the entity already has
	 * @return the VBox containing the menu of attributes for display
	 */
	public VBox createAttributeSection(List<AttributeInterface> existingAttributes);
	
	/**
	 * purpose: creates a listener that helps the pop-up to be responsive and resizable
	 * @return the change listener that acts when the pop-up is resized
	 */
	public ChangeListener<Number> getWidthListener();
	
	/**
	 * purpose: provides access to the list of attributes that the user has 
	 * chosen to add to their entity
	 * assumptions: assumes that the list of attributes has not already been compiled
	 * and that myNewEntityAttributes has been initialized
	 * @return the list of added attributes
	 */
	public List<AttributeInterface> getAttributes();
	
}
