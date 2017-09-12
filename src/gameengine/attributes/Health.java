package gameengine.attributes;

import java.util.ArrayList;
import java.util.List;

import gameengine.attributes.interfaces.HealthInterface;
import gameengine.entities.EntityInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class Health implements HealthInterface{
	private double currentHealth;
	private double maxHealth;
	private List<EntityInterface> entitiesInteracted;
	private EntityInterface killer;
	private DoubleProperty healthPercentage;

	public Health () {
		healthPercentage = new SimpleDoubleProperty();
		entitiesInteracted = new ArrayList<EntityInterface>();
	}
	
	public Health (double health) {
		healthPercentage = new SimpleDoubleProperty();
		entitiesInteracted = new ArrayList<EntityInterface>();
		setHealth(health);
		setMaxHealth(health);
	}
	
	public EntityInterface retrieveKiller() {
		if (!entitiesInteracted.isEmpty()){
			killer = entitiesInteracted.get(entitiesInteracted.size()-1);
		}
		return killer;
	}

	@Override
	public double getHealth () {
		return currentHealth;
	}

	@Override
	public double getMaxHealth () {
		return maxHealth;
	}

	@Override
	public void decreaseHealth (double health) {

		setHealth(currentHealth-health);        
	}

	@Override
	public void increaseHealth (double health) {
		setHealth(currentHealth+health);
	}

	@Override
	public void setHealth (double health) {
		currentHealth=Math.max(Math.min(health,maxHealth),0);
		healthPercentage.set(currentHealth/maxHealth);

	}

	@Override
	public void decreaseMaxHealth (double health) {
		setMaxHealth(maxHealth-health);       
	}

	@Override
	public void increaseMaxHealth (double health) {
		setMaxHealth(maxHealth+health);       

	}

	@Override
	public void setMaxHealth (double health) {
		maxHealth=Math.max(health,0);
	}

	@Override
	public DoubleProperty retrieveHealthPercentage() {
		return healthPercentage;
	}
	
	public double getHealthPercentage(){
		return healthPercentage.doubleValue();
	}

	public void addEntityInteracted(EntityInterface thisEntity) {
		entitiesInteracted.add(thisEntity);
	}
}