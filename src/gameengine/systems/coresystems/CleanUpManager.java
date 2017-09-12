package gameengine.systems.coresystems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gameengine.attributes.CleanUp;
import gameengine.attributes.Player;
import gameengine.attributes.interfaces.CleanUpInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.systems.clean_up_bypasses.CleanUpBypassInterface;
import gameengine.systems.coresystems.interfaces.CleanUpManagerInterface;
import gameengine.utilities.LiveObserver;
import javafx.collections.SetChangeListener;

public class CleanUpManager implements CleanUpManagerInterface, LiveObserver {

	private List<CleanUpBypassInterface> myBypasses;
	private Set<EntityInterface> myCleanUps;
	private EntitySetSubject mySubject;

	public CleanUpManager(EntitySetSubject subject) {
		mySubject = subject;
		myBypasses = new ArrayList<CleanUpBypassInterface>();
		myCleanUps = new HashSet<EntityInterface>();
		subject.getEntities().registerObserver(this);
	}


	public void update(double timeElapsed) {
		amend();
		HashSet<EntityInterface> toClean = new HashSet<EntityInterface>();
		for(EntityInterface entity: myCleanUps) {
			boolean toBypass = false;
			for(CleanUpBypassInterface bypass: myBypasses) {
				if(bypass.handle(entity)) {
					toBypass = true;
					break;
				};
			}
			if(!toBypass) {
				//entity.clear();
				toClean.add(entity);
			}
		}
		for(EntityInterface e : toClean){
			mySubject.getEntities().remove(e);
		}
	}

	public void register(CleanUpBypassInterface bypass) {
		myBypasses.add(bypass);
	}

	@Override
	public void amend() {
		myCleanUps.clear();
		mySubject.getEntities().forEach(entity -> {
            if(entity.containsAttribute(CleanUp.class)){
            	myCleanUps.add(entity);
            }
        });
	}
}
