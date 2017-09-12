package gameengine.systems.clean_up_bypasses;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import gameengine.attributes.CleanUp;
import gameengine.attributes.Respawnable;
import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.RespawnableInterface;
import gameengine.entities.EntityInterface;

public class RespawnBypass implements CleanUpBypassInterface {

	/**
	 * Removes the CleanUp label from entities with extra
	 * respawn lives left, and teleports these entities to
	 * their respawn location
	 *
	 * @author Jacob Warner
	 */

	private Queue<EntityInterface> myEntityQueue;
	private Random myRandom;

	public RespawnBypass() {
		myEntityQueue = new LinkedList<EntityInterface>();
		myRandom = new Random();
	}

	public boolean handle(EntityInterface entity) {
		boolean bypass = false;
		if(entity.containsAttribute(Respawnable.class)) {
			if(entity.getAttribute(Respawnable.class).lifeCount() > 0) {
				bypass = true;
				entity.removeAttribute(entity.getAttribute(CleanUp.class));
				myEntityQueue.add(entity);
			}
		}
		return bypass;
	}

	public void update(double timeElasped) {
		while(!myEntityQueue.isEmpty()) {
			EntityInterface entity = myEntityQueue.poll();
			entity.getAttribute(Respawnable.class).loseLife();
			entity.getAttribute(Spacial.class).setX(entity.getAttribute(Respawnable.class).getRespawnX() + myRandom.nextDouble()*25);
			entity.getAttribute(Spacial.class).assignY(entity.getAttribute(Respawnable.class).getRespawnY() + myRandom.nextDouble()*25);
		}
	}

}
