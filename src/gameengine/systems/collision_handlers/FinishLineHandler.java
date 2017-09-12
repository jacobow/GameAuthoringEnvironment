package gameengine.systems.collision_handlers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import gameengine.attributes.FinishLine;
import gameengine.attributes.Player;
import gameengine.attributes.Racer;
import gameengine.attributes.StartLine;
import gameengine.attributes.interfaces.FinishLineInterface;
import gameengine.attributes.interfaces.RacerInterface;
import gameengine.entities.EntityInterface;

public class FinishLineHandler implements CollisionHandlerInterface {

	private Queue<EntityInterface> myQueue;
	private Set<EntityInterface> myWinners;

	public FinishLineHandler() {
		myQueue = new LinkedList<EntityInterface>();
		myWinners = new HashSet<EntityInterface>();
	}

	public void handle(EntityInterface thisEntity, EntityInterface thatEntity) {
		if(thisEntity.containsAttribute(FinishLine.class)
		|| thatEntity.containsAttribute(FinishLine.class)
		|| thisEntity.containsAttribute(StartLine.class)
		|| thatEntity.containsAttribute(StartLine.class)) {
			myQueue.add(thisEntity);
			myQueue.add(thatEntity);
		}
	}

	public void update(double timeElapsed) {
		while(!myQueue.isEmpty()) {
			EntityInterface thisEntity = myQueue.poll();
			EntityInterface thatEntity = myQueue.poll();
			if(thisEntity.containsAttribute(FinishLine.class)
			&& thatEntity.containsAttribute(Racer.class)) finishLineCheck(thisEntity, thatEntity);
			if(thatEntity.containsAttribute(FinishLine.class)
			&& thisEntity.containsAttribute(Racer.class)) finishLineCheck(thatEntity, thisEntity);
			if(thisEntity.containsAttribute(StartLine.class)
			&& thatEntity.containsAttribute(Racer.class)) startLineCheck(thisEntity, thatEntity);
			if(thatEntity.containsAttribute(StartLine.class)
			&& thisEntity.containsAttribute(Racer.class)) startLineCheck(thatEntity, thisEntity);
		}

	}

	private void finishLineCheck(EntityInterface finishLineEntity, EntityInterface racerEntity) {
		RacerInterface racer = racerEntity.getAttribute(Racer.class);
		if(racer.isStart() && !racer.isFinish()) {
			racer.increaseLapCount();
			racer.flipStart();
			racer.flipFinishing();
		}
		if(racer.isStart() && racer.isFinish() && !racer.isFinishing()) {
			racer.flipFinish();
		}
		winCheck(finishLineEntity, racerEntity);
	}

	private void startLineCheck(EntityInterface startLineEntity, EntityInterface racerEntity) {
		RacerInterface racer = racerEntity.getAttribute(Racer.class);
		if(!racer.isStart() && !racer.isFinish()) racer.flipStart();
		if(racer.isFinish()) {
			racer.decreaseLapCount();
			racer.flipFinish();

		}
	}

	private void winCheck(EntityInterface finishLineEntity, EntityInterface racerEntity) {
		RacerInterface racer = racerEntity.getAttribute(Racer.class);
		FinishLineInterface finishLine = finishLineEntity.getAttribute(FinishLine.class);
		if(finishLine.getMaxLaps() == racer.retrieveLapCount() && racerEntity.containsAttribute(Player.class) && !myWinners.contains(racerEntity)) {
			myWinners.add(racerEntity);
			racerEntity.getAttribute(Player.class).assignWinStatus(1.0);
		}
	}

}
