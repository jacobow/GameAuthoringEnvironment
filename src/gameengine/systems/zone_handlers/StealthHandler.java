package gameengine.systems.zone_handlers;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import gameengine.attributes.Guard;
import gameengine.attributes.Player;
import gameengine.attributes.Spacial;
import gameengine.attributes.Respawnable;
import gameengine.entities.EntityInterface;

public class StealthHandler implements ZoneHandlerInterface {

	private Queue<EntityInterface> zoneQueue;
	private Queue<Queue<EntityInterface>> inRangeQueue;

	public StealthHandler() {
		zoneQueue = new LinkedList<EntityInterface>();
		inRangeQueue = new LinkedList<Queue<EntityInterface>>();
	}

	@Override
	public void update(double elaspedTime) {
		while(!zoneQueue.isEmpty()){
			EntityInterface zoneEntity = zoneQueue.poll();
			Queue<EntityInterface> inRangeEntities = inRangeQueue.poll();
			while(!inRangeEntities.isEmpty()) {
				EntityInterface entity = inRangeEntities.poll();
				if(entity.containsAttribute(Player.class)
				&& entity.containsAttribute(Respawnable.class)
				&& entity.containsAttribute(Spacial.class)) {
					entity.getAttribute(Spacial.class).setX(entity.getAttribute(Respawnable.class).getRespawnX());
					entity.getAttribute(Spacial.class).assignY(entity.getAttribute(Respawnable.class).getRespawnY());
				}
			}
		}
	}

	@Override
	public void handle(EntityInterface zoneEntity, Queue<EntityInterface> inRangeEntities) {
		if(zoneEntity.containsAttribute(Guard.class)) {
			zoneQueue.add(zoneEntity);
			inRangeQueue.add(inRangeEntities);
		}
	}

}
