package gameengine.systems.coresystems;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import gameengine.attributes.Path;
import gameengine.attributes.Spacial;
import gameengine.attributes.Zone;
import gameengine.attributes.interfaces.PathInterface;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.attributes.interfaces.ZoneInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.systems.coresystems.interfaces.AbilityManagerInterface;
import gameengine.systems.coresystems.interfaces.AddPathInterface;
import javafx.collections.SetChangeListener;
public class PathingManager implements AddPathInterface{
	private AbilityManagerInterface myAbilityManager;
	private EntitySetSubject myEntities;
	private Set<PathInterface> myPaths;
	private Set<PathInterface> toRemove;

	public PathingManager(EntitySetSubject entities, AbilityManagerInterface abilityManager) {
		myAbilityManager = abilityManager;
		myEntities = entities;
		myPaths = new HashSet<PathInterface>();
		toRemove = new HashSet<PathInterface>();
		updateTrackedPaths();
	}

	public void addNewPath(PathInterface newPath){
		myPaths.add(newPath);
	}

	public void updateTrackedPaths(){
		myPaths.clear();
		myEntities.getEntities().forEach(entity -> {
			if(entity.containsAttribute(Path.class)) {
				myPaths.add(entity.getAttribute(Path.class));
			}		});
	}

	public void update(double timeElapsed){
		updateTrackedPaths();
		Iterator<PathInterface> pathIter = myPaths.iterator();
		while(pathIter.hasNext()){
			PathInterface currentPath = pathIter.next();
			updatePath(currentPath);
		}

		Iterator<PathInterface> removeIter = toRemove.iterator();
		while(removeIter.hasNext()){
			myPaths.remove(removeIter.next());
		}
	}
	private void updatePath(PathInterface currentPath) {
		if(currentPath.retrieveCurrentPoint()!=null){
			double targetX = currentPath.retrieveCurrentPoint()[0];
			double targetY = currentPath.retrieveCurrentPoint()[1];
			if(currentPath.retrieveControlledEntity().containsAttribute(Spacial.class)){
				checkPathStatus(currentPath, targetX, targetY);
			}

		}else{
			myAbilityManager.removeAbility(currentPath.retrieveMover());
			myAbilityManager.removeAbility(currentPath.retrieveRotator());
		}
	}
	private void checkPathStatus(PathInterface currentPath, double targetX, double targetY) {
		SpacialInterface currSpacial = currentPath.retrieveControlledEntity().getAttribute(Spacial.class);
		if(currSpacial.retrieveX().get()<=targetX 
				&& (currSpacial.retrieveX().get()+ currSpacial.retrieveShape().getLayoutBounds().getWidth()) >=targetX
				&& currSpacial.retrieveY().get()<=targetY 
				&& (currSpacial.retrieveY().get()+currSpacial.retrieveShape().getLayoutBounds().getHeight())>=targetY){
			myAbilityManager.addAbilityOnce(currentPath.retrieveCurrentAbility(), new double[]{});
			if(currentPath.isRepeating()){
				myAbilityManager.addAbilityOnce(currentPath.retrieveRotator(), currentPath.retrieveCurrentPoint());
				myAbilityManager.addAbilityOnce(currentPath.retrieveMover(), new double[]{});
				currentPath.increaseIterator(); 

			}else{
				currentPath.removeAllPoints();
			}
		}/*else if(currentPath.getEntity().containsAttribute(Zone.class)){
			ZoneInterface myZone = currentPath.getEntity().getAttribute(Zone.class);
		}*/else{
			myAbilityManager.addAbilityOnce(currentPath.retrieveRotator(), currentPath.retrieveCurrentPoint());
			myAbilityManager.addAbilityOnce(currentPath.retrieveMover(), new double[]{});
		}
	}
}