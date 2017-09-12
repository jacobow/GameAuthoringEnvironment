package gameengine.systems.zone_handlers;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import gameengine.attributes.Spacial;
import gameengine.attributes.Team;
import gameengine.attributes.Turret;
import gameengine.attributes.Zone;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.attributes.interfaces.TeamInterface;
import gameengine.attributes.interfaces.ZoneInterface;
import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.CreateEntity;
import gameengine.systems.abilities.RotateToClick;
import gameengine.systems.coresystems.interfaces.AbilityManagerInterface;

public class TurretHandler implements ZoneHandlerInterface {

	public static final String NO_NAME = "No Name";

	private Queue<EntityInterface> zoneQueue;
	private Queue<Queue<EntityInterface>> inRangeQueue;
	private AbilityManagerInterface myAbilityManager;

	public TurretHandler() {
		zoneQueue = new LinkedList<EntityInterface>();
		inRangeQueue = new LinkedList<Queue<EntityInterface>>();
	}

	@Override
	public void update(double elaspedTime) {
		while(!zoneQueue.isEmpty()){
			EntityInterface zoneEntity = zoneQueue.poll();

			Queue<EntityInterface> inRangeEntities = inRangeQueue.poll();
			TargetAndFire(zoneEntity, inRangeEntities);
		}
	}

	public void handle(EntityInterface zoneEntity, Queue<EntityInterface> inRangeEntities) {
		if(zoneEntity.containsAttribute(Turret.class)) {
			zoneQueue.add(zoneEntity);
			inRangeQueue.add(inRangeEntities);
		}
	}

	public void TargetAndFire(EntityInterface zoneEntity, Queue<EntityInterface> inRangeEntities) {
		boolean activeTeams = false;
		String zoneTeamName = NO_NAME;
		if(zoneEntity.containsAttribute(Team.class)) zoneTeamName = zoneEntity.getAttribute(Team.class).getTeam();
		while(!inRangeEntities.isEmpty()) {
			EntityInterface entity = inRangeEntities.poll();
			if(entity.containsAttribute(Team.class)
					&& zoneTeamName.equals(entity.getAttribute(Team.class).getTeam())) {
				continue;
			}
			//Rotate to face in range entity
			SpacialInterface aimHere = null;
			if(entity.containsAttribute(Spacial.class)) aimHere = entity.getAttribute(Spacial.class);
			if(aimHere!=null){
				System.out.println("Aimhere coordinates: " + aimHere.retrieveX().get() + ", " + aimHere.retrieveY().get());
				myAbilityManager.addAbilityOnce(new RotateToClick(zoneEntity), new double[]{
						aimHere.retrieveX().get() + aimHere.retrieveShape().getBoundsInLocal().getWidth()/2.0,
						aimHere.retrieveY().get() + aimHere.retrieveShape().getBoundsInLocal().getHeight()/2.0
				});
				System.out.println("Attempting to aim");
				//If projectile exists, fire the projectile
				if(zoneEntity.getAbilityMap().containsKey(CreateEntity.class)){
					System.out.println("Firing");
					myAbilityManager.addAbilityOnce(zoneEntity.getAbilityMap().get(CreateEntity.class), new double[]{});
				}

				break;
			}
		}
	}

	@Override
	public void setAbilityManager(AbilityManagerInterface abilityManager) {
		myAbilityManager = abilityManager;
	}

}
