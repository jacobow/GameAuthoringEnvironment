package gameengine.systems.abilities;

import java.util.ArrayList;

import gameengine.attributes.Path;
import gameengine.entities.EntityInterface;
import gameengine.systems.coresystems.interfaces.AddPathInterface;

/**
 * 
 * @author walker
 *
 */
public class MoveToClick implements AbilityInterface{
	EntityInterface myEntity;
	Path myPath;
	
	public MoveToClick(EntityInterface newEntity){
		assignControlledEntity(newEntity);
	}
	
	public MoveToClick(AddPathInterface newpathadder, EntityInterface newentity){
		assignControlledEntity(newentity);
	}
	
	
	public void assignControlledEntity(EntityInterface newentity){
		myEntity = newentity;
	}
	
	public void activate(double timeElapsed, double[] extraInputs) {
		if(extraInputs.length==2){
			if(myPath==null){
				ArrayList<double[]> pointFromInputs = new ArrayList<double[]>();
				pointFromInputs.add(extraInputs);
				myPath = new Path(myEntity, new ArrayList<AbilityInterface>(), pointFromInputs);
				myEntity.addAttribute(myPath);
			}else{
				myPath.removeAllPoints();
				myPath.addPoint(extraInputs);
			}
		}
	}

}
