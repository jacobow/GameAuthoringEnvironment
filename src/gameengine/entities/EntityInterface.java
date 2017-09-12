package gameengine.entities;
import java.util.List;
import java.util.Map;

import gameengine.attributes.Team;
import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.systems.abilities.AbilityInterface;
import javafx.scene.input.KeyCode;
public interface EntityInterface {
	/**
	 * A list version of the attribute interface
	 * Used to actually get the attributes.
	 * @return
	 */
	abstract List<AttributeInterface> getAttributesList();

	/**
	 * A list version of the ability interface
	 * Used to actually get the abilities for display in the UI.
	 * @return
	 */
	abstract Map<AbilityInterface, KeyCode> getAbilitiesList();

	/**
	 * Map version of the attribute interface,
	 * used to check if the entity has certain attributes or not.
	 * @return
	 */

	public boolean containsAttribute(Class<? extends AttributeInterface> attribute);

	@Deprecated
	abstract Map<Class<? extends AttributeInterface>, AttributeInterface> getAttributeMap();

	/**
	 * Map version of the abilities interface,
	 * used to check if the entity has certain attributes or not.
	 * @return
	 */
	abstract Map<Class<? extends AbilityInterface>, AbilityInterface> getAbilityMap();
	/**
	 * Add an attribute to the given entity.
	 * @param attribute: Recommended implementation: \n "new Physical();" or something similar
	 */
	abstract void addAttribute(AttributeInterface attribute);
	/**
	 * Add an ability to the given entity's abilities list.
	 * @param ability the ability being added
	 */

	public void removeAttribute(AttributeInterface attribute);

	abstract void addAbility(AbilityInterface ability, KeyCode key);

	/**
	 *
	 */
	abstract void clear();
	/**
	 *
	 * @return the given entity's ID number
	 */
	abstract String getID();

	/**
	 *
	 * @return set the entitys ID
	 */
	abstract void setID(String id);

	public <T extends AttributeInterface> T getAttribute(Class<T> attributeClass);

	abstract void addAbility(AbilityInterface myAbility, String myControllerCode, int myControllerNumber);

}