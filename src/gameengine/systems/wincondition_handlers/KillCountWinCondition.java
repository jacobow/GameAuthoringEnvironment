package gameengine.systems.wincondition_handlers;

import gameengine.attributes.KillDeathCount;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;

/**
 * @author DavidYoon
 *
 */
public class KillCountWinCondition extends GeneralWinCondition implements WinConditionInterface {

	private double killsNeededToWin = 2;
	private KillDeathCount killDeathCount;
	
	public KillCountWinCondition(EntitySetSubject entities){
		super(entities);
		
	}
	
	@Override
	public boolean isGameOver() {
		for (EntityInterface player: remainingPlayerEntities) {
			if (player.containsAttribute(KillDeathCount.class)){
				killDeathCount = player.getAttribute(KillDeathCount.class);
				if (killDeathCount.retrieveKillCount().get()>=getKillsNeededToWin()){
					winner = player;
					return true;
				}
			}
		}
		return false;
	}

	public double getKillsNeededToWin() {
		return killsNeededToWin;
	}

	public void setKillsNeededToWin(double killsNeededToWin) {
		this.killsNeededToWin = killsNeededToWin;
	}
			
}
