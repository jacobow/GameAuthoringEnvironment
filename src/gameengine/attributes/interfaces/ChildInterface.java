package gameengine.attributes.interfaces;

import gameengine.entities.EntityInterface;

public interface ChildInterface extends AttributeInterface {

	abstract void assignParent(EntityInterface parental);
	
	abstract EntityInterface retrieveParent();

}