package gameauthoring.components.properties;

import gameengine.entities.EntityInterface;

public interface ViewerInterface {

	/**
	 * purpose: to provide access to the entity whose properties the
	 * viewer displays
	 * @return the selected entity
	 */
	public EntityInterface getEntity();

	/**
	 * purpose: when a new entity is selected, this method should be called to 
	 * update the viewer's information to reflect the new entity's properties
	 * @param newEntity the new selected entity 
	 */
	public void updateInfo(EntityInterface newEntity);

	
}
