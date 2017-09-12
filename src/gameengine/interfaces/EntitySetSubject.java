package gameengine.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.utilities.LiveSet;

/**
 * 
 * @author walker
 *
 */
public interface EntitySetSubject {

	public LiveSet<EntityInterface> getEntities();
	
	public LiveSet<EntityInterface> getFakeEntities();

}
