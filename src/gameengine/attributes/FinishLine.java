package gameengine.attributes;

import java.util.HashSet;
import java.util.Set;

import gameengine.attributes.interfaces.FinishLineInterface;
import gameengine.entities.EntityInterface;

public class FinishLine implements FinishLineInterface {

	private Set<EntityInterface> myRacers;
	private double maxLaps;

	public FinishLine() {
		myRacers = new HashSet<EntityInterface>();
	}

	public Set<EntityInterface> retrieveEntities() {
		return myRacers;
	}

	public void setMaxLaps(double lapCount) {
		maxLaps = lapCount;
	}

	public double getMaxLaps() {
		return maxLaps;
	}

}
