package gameengine.interfaces;

import gameengine.entities.EntityInterface;

/**
 * 
 * @author walker
 *
 */
public interface EntityCreationInterface {
    abstract void addEntity(EntityInterface entity);
    
    abstract void addFakeEntity(EntityInterface entity);

}
