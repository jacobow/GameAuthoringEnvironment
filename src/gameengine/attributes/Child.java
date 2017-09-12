package gameengine.attributes;

import gameengine.attributes.interfaces.ChildInterface;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;

/**
 * 
 * @author walker
 *
 */
public class Child implements ChildInterface  {
	
	private EntityInterface myParent;
	
	public Child(){
		myParent=null;
	}
	public Child(EntityInterface parent){
		assignParent(parent);
	}
	
	public void assignParent(EntityInterface parent){
		myParent = parent;
	}
	
	public EntityInterface retrieveParent(){
		return myParent;
	}
	
}
