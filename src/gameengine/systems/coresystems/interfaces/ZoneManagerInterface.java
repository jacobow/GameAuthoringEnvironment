package gameengine.systems.coresystems.interfaces;

import java.util.List;
import java.util.Queue;

import gameengine.entities.EntityInterface;
import gameengine.systems.zone_handlers.ZoneHandlerInterface;

public interface ZoneManagerInterface extends SystemsInterface {

	void register(ZoneHandlerInterface handler);

	void notifyZoneHandlers(EntityInterface zoneEntity, Queue<EntityInterface> inRangeEntities);
}
