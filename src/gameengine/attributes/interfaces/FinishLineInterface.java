package gameengine.attributes.interfaces;

import java.util.Set;

import gameengine.entities.EntityInterface;

public interface FinishLineInterface extends AttributeInterface {

	public Set<EntityInterface> retrieveEntities();

	public void setMaxLaps(double lapCount);

	public double getMaxLaps();

}
