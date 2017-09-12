package gameengine.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.zone_handlers.ZoneHandlerInterface;
import gameengine.utilities.LiveSet;

/**
 * 
 * @author jacob
 * @author walker
 *
 */
public interface ZoneSubject {

	public LiveSet<ZoneHandlerInterface> getZoneSubsystems();

	public LiveSet<EntityInterface> getEntities();

}
