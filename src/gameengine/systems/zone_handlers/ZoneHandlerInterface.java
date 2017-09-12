package gameengine.systems.zone_handlers;

import java.util.List;
import java.util.Queue;

import gameengine.entities.EntityInterface;
import gameengine.systems.coresystems.interfaces.AbilityManagerInterface;
import gameengine.systems.coresystems.interfaces.SystemsInterface;
import gameengine.systems.interfaces.SubSystemInterface;

public interface ZoneHandlerInterface extends SystemsInterface, SubSystemInterface {

	public void handle(EntityInterface zoneEntity, Queue<EntityInterface> inRangeEntities);

	default void setAbilityManager(AbilityManagerInterface abilityManager) {
	}
}
