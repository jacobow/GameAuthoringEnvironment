package gameauthoring.components.entitycreation;

import gameengine.entities.EntityInterface;

/**
 * Default interface for a general entity
 * @author michaelseaberg
 *
 */
public interface EntityCreatorInterface {

	  /**
     * purpose: to create the pop-up window that allows the user to
     * customize an entity to be added to their game 
     */
    public void initialize();
    
    
    /**
     * purpose: to provide access to the selected entity
     * @return the selected entity
     */
    public EntityInterface getEntity();
	
}
