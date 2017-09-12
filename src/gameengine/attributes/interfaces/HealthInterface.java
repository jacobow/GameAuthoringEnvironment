package gameengine.attributes.interfaces;

import gameengine.entities.EntityInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;

/**
 * 
 * @author Walker & David
 *
 * Gives health to an entity so it can be damaged
 * @param Amount of health 
 */
public interface HealthInterface extends AttributeInterface{
	/**
	 * 
	 * @return amount of health remaining
	 */
	double getHealth();

	/**
	 * 
	 * @return get the entity that killed the 
	 */
	public EntityInterface retrieveKiller();

	/**
	 * Get the original amount of health
	 * @return maximum health double
	 */
	double getMaxHealth();

	/**
	 * Get the percentage of health
	 * @return health percentage in double
	 */
	DoubleProperty retrieveHealthPercentage();

	/**
	 * Decreases health left in entity
	 * @param health: CHANGE in health (positive number for a decrease)
	 */
	void decreaseHealth(double health);

	/**
	 * Increases health left in entity
	 * @param health: CHANGE in health (positive number for an increase)
	 */
	void increaseHealth(double health);

	/**
	 * Set health left in entity
	 * @param health: Absolute amount of health remaining
	 */
	void setHealth(double health);

	/**
	 * Lowers the maximum health remaining
	 * @param health: Change in max health (Positive number for a decrease)
	 */
	void decreaseMaxHealth(double health);

	/**
	 * Increases max health left in entity
	 * @param health: CHANGE in max health (positive number for an increase)
	 */
	void increaseMaxHealth(double health);

	/**
	 * Sets max health in entity
	 * @param health: New maximum health
	 */
	void setMaxHealth(double health);
}