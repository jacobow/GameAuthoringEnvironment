package gameengine.systems.coresystems;

import java.util.HashSet;
import java.util.Set;
import gameengine.attributes.CleanUp;
import gameengine.attributes.Health;
import gameengine.attributes.KillDeathCount;
import gameengine.attributes.Range;
import gameengine.attributes.Child;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.systems.coresystems.interfaces.SystemsInterface;
import gameengine.utilities.LiveObserver;
import javafx.collections.SetChangeListener;

/**
 * @author DavidYoon
 *
 */
public class LifeDeathManager implements SystemsInterface, LiveObserver {
	private Set<EntityInterface> myLivings;
	private EntitySetSubject mySubject;

	public LifeDeathManager(EntitySetSubject subject) {
		mySubject = subject;
		myLivings = new HashSet<EntityInterface>();
		subject.getEntities().registerObserver(this);
		amend();
	}

	@Override
	public void update(double elaspedTime) {
		myLivings.forEach((entity)->{
			double health = entity.getAttribute(Health.class).getHealth();
			EntityInterface killer = entity.getAttribute(Health.class).retrieveKiller();
			if (health <= 0){
				entity.addAttribute(new CleanUp());
			}
		});
	}

	@Override
	public void amend() {
		myLivings.clear();
		mySubject.getEntities().forEach(entity -> {
            if(entity.containsAttribute(Health.class)) myLivings.add(entity);
        });
	}

}
