package gameengine.systems.collision_handlers;


import java.util.LinkedList;
import java.util.Queue;
import gameengine.attributes.Blocked;
import gameengine.attributes.Child;
import gameengine.attributes.CleanUp;
import gameengine.attributes.Damages;
import gameengine.attributes.Health;
import gameengine.attributes.Shielded;
import gameengine.entities.EntityInterface;

public class DamageHandler implements CollisionHandlerInterface {

	private Queue<EntityInterface> collisionQueue;

	public DamageHandler() {
		collisionQueue = new LinkedList<EntityInterface>();
	}

	@Override
	public void update(double timeElapsed) {
		while(!collisionQueue.isEmpty()) {
			EntityInterface thisEntity = collisionQueue.poll();
			EntityInterface thatEntity = collisionQueue.poll();
			handleProjectile(thisEntity, thatEntity);
		}
	}

	@Override
	public void handle(EntityInterface thisEntity, EntityInterface thatEntity) {
		collisionQueue.add(thisEntity);
		collisionQueue.add(thatEntity);
	}

	private void handleProjectile(EntityInterface thisEntity, EntityInterface thatEntity) {
		dealDamage(thisEntity, thatEntity);
		dealDamage(thatEntity, thisEntity);
	}

	private void dealDamage(EntityInterface thisEntity, EntityInterface thatEntity) {
		
		if(thisEntity.containsAttribute(Child.class)) {
			if(thatEntity.getID() == thisEntity.getAttribute(Child.class).retrieveParent().getID()) return;
		}
		if(thisEntity.containsAttribute(Damages.class) && thatEntity.containsAttribute(Health.class)) {
			if(thatEntity.containsAttribute(Blocked.class)){
				thatEntity.getAttribute(Blocked.class).assignBlock(false);
			}
			else{
				double damage=thisEntity.getAttribute(Damages.class).getDamageDone();
				if(thatEntity.containsAttribute(Shielded.class) && damage>0){
					thatEntity.getAttribute(Shielded.class).decreaseCurrentHealth(damage);
					damage=Math.max(damage-thatEntity.getAttribute(Shielded.class).getCurrentHealth(), 0);
				}
				Health health = thatEntity.getAttribute(Health.class);
				health.decreaseHealth(thisEntity.getAttribute(Damages.class).getDamageDone());
				health.addEntityInteracted(thisEntity);
			}
		}
	}

}
