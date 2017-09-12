// This entire file is part of my masterpiece.
// Jacob Warner
/*
 * This class is an excellent example of both sides of an Observer design pattern.
 * First, it is an observer of the game world, so that it can receive amended version
 * of the currently active entities in the game.  It is also a subject, as it allows
 * collision handler subsystems to register themselves to this class and calls on
 * these subsystems to handle collisions when they occur.  This class shows how dynamic
 * and flexible the very backbone of our engine is, allowing for many different collision
 * rules to be implemented as listeners and updating its current entities the moment they
 * are added, which is especially import for live editing.  This is also a prime example
 * of how we interpreted the entity component system design pattern, in that we saw it
 * necessary to differentiate core systems like this one, which exist in every game and
 * just throw events, and sub systems, which are added by the user and define the acting
 * rules of the game.  An example of a collision handler sub system is the next part of
 * this code masterpiece.
 */

package gameengine.systems.coresystems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import gameengine.attributes.Physical;
import gameengine.attributes.Spacial;
import gameengine.attributes.Team;
import gameengine.attributes.interfaces.TeamInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.CollisionSubject;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;
import gameengine.systems.coresystems.interfaces.CollisionManagerInterface;
import gameengine.utilities.LiveObserver;
import gameengine.utilities.LiveSet;
import javafx.scene.shape.Shape;

public class CollisionManager implements CollisionManagerInterface, LiveObserver{

	/**
	 * This class manages all of the physical entities in a given game world.
	 * If two physical entities ever occupy the same space, this system's update
	 * method throws an event to collision handlers that contains the two entities
	 * in question to be dealt with as seen fit.
	 *
	 * @author Jacob Warner
	 */
	private Set<CollisionHandlerInterface> myHandlers;
	private Set<EntityInterface> myPhysicals;
	private CollisionSubject mySubject;

	public CollisionManager(CollisionSubject subject) {
		mySubject = subject;
		myHandlers = new LiveSet<CollisionHandlerInterface>();
		myPhysicals = new LiveSet<EntityInterface>();
		subject.getEntities().registerObserver(this);
		subject.getCollisionSubsystems().registerObserver(this);
		amend();
	}

	/**
	 * This method scans through the physical entities of the game world and checks
	 * to see if any two are colliding.  If they are, it notifies the handlers that
	 * are registered to observe this class so that the collision might be handled
	 * by one of these subsystems.
	 * @param timeElapsed  The difference in time for the update to take place in,
	 * irrelevant in this particular core system.
	 */
	public void update(double timeElapsed) {
		List<EntityInterface> physicalEntities = new ArrayList<EntityInterface>(myPhysicals);
		ListIterator<EntityInterface> i = physicalEntities.listIterator();
		while( i.hasNext()) {
			EntityInterface thisEntity = i.next();
			Iterator<EntityInterface> j = physicalEntities.listIterator(i.nextIndex());
			while(j.hasNext()){
				EntityInterface thatEntity = j.next();
				if(thisEntity == thatEntity) continue;
				Shape thisShape = thisEntity.getAttribute(Spacial.class).retrieveShape();
				Shape thatShape = thatEntity.getAttribute(Spacial.class).retrieveShape();
				Shape intersect = Shape.intersect(thisShape, thatShape);
				if(intersect.getBoundsInLocal().getWidth() != -1 && !teamCheck(thisEntity, thatEntity)) {
					notifyCollisionHandlers(thisEntity, thatEntity);
				}
			}
		}
	}

	/**
	 * Registers an observing collision handler to later be called
	 * to handle collisions
	 */
	public void register(CollisionHandlerInterface handler) {
		myHandlers.add(handler);
	}
	/**
	 * Notifies the registered CollisionHandlers of a collision
	 * @param thisEntity the first physical entity in the collison
	 * @param thatEntity the second physical entity in the collison
	 */
	public void notifyCollisionHandlers(EntityInterface thisEntity, EntityInterface thatEntity) {
		for(CollisionHandlerInterface handler : myHandlers) {
		    if(!(thisEntity.containsAttribute(Team.class) && thatEntity.containsAttribute(Team.class)) ||
		            ((thisEntity.containsAttribute(Team.class) && thatEntity.containsAttribute(Team.class)) &&
		            thisEntity.getAttribute(Team.class).getTeam()!=thatEntity.getAttribute(Team.class).getTeam())){
	                  handler.handle(thisEntity, thatEntity);
		    }
		}
	}
	/**
	 * Stores the last position where the entity was not colliding inside
	 * of its physical attribute
	 * @param entity The entity in question
	 */
	public void setPreCollisionPosition(EntityInterface entity) {
		if(entity.containsAttribute(Spacial.class) && entity.containsAttribute(Physical.class)) {
			entity.getAttribute(Physical.class).assignPreCollisionX(
					entity.getAttribute(Spacial.class).retrieveX().get());
			entity.getAttribute(Physical.class).assignPreCollisionY(
					entity.getAttribute(Spacial.class).retrieveY().get());
			entity.getAttribute(Physical.class).assignPreCollisionOrientation(
					entity.getAttribute(Spacial.class).retrieveOrientation().get());
		}
	}

	//checks to see if entities are on the same team
	private boolean teamCheck(EntityInterface thisEntity, EntityInterface thatEntity) {
		if(thisEntity.containsAttribute(Team.class) && thatEntity.containsAttribute(Team.class)) {
			TeamInterface team1 = thisEntity.getAttribute(Team.class);
			TeamInterface team2 = thisEntity.getAttribute(Team.class);
			if(team1.getTeam().equals(team2.getTeam())) return true;
		}
		return false;
	}

	/**
	 * Determines that only physical entities should be cached into this
	 * particular system
	 */
	@Override
	public void amend() {
		myHandlers = mySubject.getCollisionSubsystems();
		myPhysicals.clear();
		mySubject.getEntities().forEach(entity -> {
            if(entity.containsAttribute(Physical.class))
            	myPhysicals.add(entity);
        });
	}
}
