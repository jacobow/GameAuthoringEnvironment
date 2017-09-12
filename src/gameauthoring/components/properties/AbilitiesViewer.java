package gameauthoring.components.properties;

import java.util.ArrayList;
import java.util.List;
import gameauthoring.AuthoringFactory;
import gameengine.systems.abilities.AbilityInterface;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Purpose: this class allows the program to display the abilities of a selected entity, along
 * with their input methods, in an accordion
 * Dependencies: the class is dependent on the the Viewer class
 * Example Use: used to allow the user to view that a selected entity has an MoveForward ability 
 * set to the W key
 * 
 * @author Larissa Cox 
 *
 */

public class AbilitiesViewer extends Viewer {
	private final static String TITLE = "Entity Abilities";

	public AbilitiesViewer() {
		super(TITLE);
	}

	/**
	 * purpose: to provide access to the list of abilities that the entity
	 * owns
	 */
	@Override
	protected List<Object> getProperties() {
		List<Object> myObjects = new ArrayList<Object>();
		for (AbilityInterface ability : getEntity().getAbilitiesList().keySet()) {
			myObjects.add(ability);
		}
		return myObjects;
	}

	/**
	 * purpose: to fill the pane with the abilities the entity has and their 
	 * properties
	 */
	@Override
	protected VBox fillPropteryPane(Object property) {
		VBox properties = new VBox();
		String key = getEntity().getAbilitiesList().get((AbilityInterface) property).getName();
		properties.getChildren().add(new HBox(AuthoringFactory.makeText("on key: " + key)));
		properties.getChildren()
				.add(new HBox(AuthoringFactory.makeText("cooldown: " + ((AbilityInterface) property).getCooldown())));
		return properties;
	}
}