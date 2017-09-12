package gameengine.systems.wincondition_handlers;

import java.util.ArrayList;

import gameengine.attributes.Player;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;

/**
 * @author DavidYoon
 *
 */
public class FinishLineWinCondition extends GeneralWinCondition {

	public FinishLineWinCondition(EntitySetSubject entities) {
		super(entities);

	}

	@Override
	public boolean isGameOver() {
		ArrayList<EntityInterface> temporaryListOfEntities = new ArrayList<>();
		for (EntityInterface player: remainingPlayerEntities) {
			if (player.getAttribute(Player.class).retrieveWinStatus().getValue() != 0) temporaryListOfEntities.add(player);
		}
		temporaryListOfEntities.forEach((player)-> { remainingPlayerEntities.remove(player);});

		if (remainingPlayerEntities.isEmpty()) return true;
		return false;
	}

}