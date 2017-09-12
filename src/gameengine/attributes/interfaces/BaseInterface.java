package gameengine.attributes.interfaces;

import java.util.List;

import gameengine.entities.EntityInterface;

public interface BaseInterface extends AttributeInterface{
	
	/**
	 * Returns status of the base
	 * @return true if seized, false if not.
	 */
	public abstract boolean isSeized();

	/**
	 * Change the status of the base
	 * @param seized
	 */
	public void assignSeized(boolean seized);

	/**
	 * Returns which entities interacted with the base
	 * @return
	 */
	public List<EntityInterface> retrieveEntitiesInteracted();

	/**
	 * Adds an entity to the list.
	 * @param interactedEntity
	 */
	public void addEntitiesInteracted(EntityInterface interactedEntity);
	
	/**
	 * 
	 * @return The entity that seized the base most recently
	 */
	public EntityInterface retrieveSeizer();

	public boolean interacted();
}
