package gameengine.systems.coresystems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import gameengine.attributes.Player;
import gameengine.attributes.Range;
import gameengine.attributes.Spacial;
import gameengine.attributes.Team;
import gameengine.attributes.Zone;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.attributes.interfaces.TeamInterface;
import gameengine.attributes.interfaces.ZoneInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.interfaces.ZoneSubject;
import gameengine.systems.coresystems.interfaces.AbilityManagerInterface;
import gameengine.systems.coresystems.interfaces.ZoneManagerInterface;
import gameengine.systems.zone_handlers.ZoneHandlerInterface;
import gameengine.utilities.LiveObserver;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.collections.SetChangeListener.Change;
import javafx.scene.shape.Shape;

public class ZoneManager implements ZoneManagerInterface, LiveObserver {

	private Set<ZoneHandlerInterface> myHandlers;
	private Set<EntityInterface> myEntities;
	private Set<EntityInterface> myZones;
	private AbilityManagerInterface myAbilityManager;
	private ZoneSubject myWorld;

	public ZoneManager(ZoneSubject subject, AbilityManagerInterface abilityManager) {
		myWorld = subject;
		myAbilityManager = abilityManager;
		myHandlers = new HashSet<ZoneHandlerInterface>();
		myZones = new HashSet<EntityInterface>();
		myWorld=subject;
		myWorld.getEntities().registerObserver(this);
		myWorld.getZoneSubsystems().registerObserver(this);
		amend();
	}

	public void register(ZoneHandlerInterface handler) {
		handler.setAbilityManager(myAbilityManager);
		myHandlers.add(handler);
	}

	public void notifyZoneHandlers(EntityInterface zoneEntity, Queue<EntityInterface> inRangeEntities) {
		for(ZoneHandlerInterface handler: myHandlers) {
			handler.handle(zoneEntity, inRangeEntities);
		}
	}

	public void update(double elaspedTime) {
		for(EntityInterface thisEntity: myZones) {
			ZoneInterface zone = (ZoneInterface)thisEntity.getAttribute(Zone.class);
			zone.getInRangeEntities().clear();
			if(thisEntity.containsAttribute(Spacial.class)) {
				zone.assignX(thisEntity.getAttribute(Spacial.class).retrieveX().getValue());
				zone.assignY(thisEntity.getAttribute(Spacial.class).retrieveY().getValue());
			}
			LinkedList<EntityInterface> inRangeEntities = new LinkedList<EntityInterface>();
			for(EntityInterface thatEntity: myWorld.getEntities()) {
				if(thisEntity == thatEntity || thatEntity.containsAttribute(Range.class)) {
					continue;
				}
				Shape thatShape = thatEntity.getAttribute(Spacial.class).retrieveShape();
				Shape intersect = Shape.intersect(zone.retrieveZone(), thatShape);
				if(intersect.getBoundsInLocal().getWidth() != -1 && !teamCheck(thisEntity, thatEntity)) {
					if(thatEntity.containsAttribute(Player.class)) {
						inRangeEntities.add(thatEntity);
					}
					inRangeEntities.addFirst(thatEntity);
				}
			}
			if(!inRangeEntities.isEmpty()) {
				thisEntity.getAttribute(Zone.class).assignInRangeEntities(inRangeEntities);
				notifyZoneHandlers(thisEntity, inRangeEntities);
			}
		}

	}

	private boolean teamCheck(EntityInterface thisEntity, EntityInterface thatEntity) {
		if(thisEntity.containsAttribute(Team.class) && thatEntity.containsAttribute(Team.class)) {
			TeamInterface team1 = thisEntity.getAttribute(Team.class);
			TeamInterface team2 = thisEntity.getAttribute(Team.class);
			if(team1.getTeam().equals(team2.getTeam())) return true;
		}
		return false;
	}

	@Override
	public void amend() {
		myHandlers = myWorld.getZoneSubsystems();
		myHandlers.forEach(l->l.setAbilityManager(myAbilityManager));
		myZones.clear();
		myWorld.getEntities().forEach(change->{
			if(change.containsAttribute(Zone.class)){
				myZones.add(change);
				System.out.println("Zone added to zone manager");
			}
		});
	}
}
