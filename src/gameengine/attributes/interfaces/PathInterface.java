package gameengine.attributes.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.AbilityInterface;
import gameengine.systems.abilities.MoveForward;
import gameengine.systems.abilities.RotateToClick;

public interface PathInterface {
	
	abstract void setEntity(EntityInterface entityToControl);
	
	abstract void setRepeating(boolean repeat);
	
	abstract void addPoint(double[] newPoint);

	abstract void addPoint(double[] newPoint, AbilityInterface newAbility);
	
	abstract RotateToClick retrieveRotator();
	
	abstract MoveForward retrieveMover();

	abstract double[] retrieveCurrentPoint();
	
	abstract void removeAllPoints();
	
	abstract EntityInterface retrieveControlledEntity();
	
	abstract AbilityInterface retrieveCurrentAbility();
	
	abstract void increaseIterator();
	
	abstract boolean isRepeating();

}