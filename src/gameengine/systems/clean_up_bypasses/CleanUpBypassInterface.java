package gameengine.systems.clean_up_bypasses;

import gameengine.entities.EntityInterface;
import gameengine.systems.coresystems.interfaces.SystemsInterface;
import gameengine.systems.interfaces.SubSystemInterface;

public interface CleanUpBypassInterface extends SystemsInterface, SubSystemInterface{

	/**
	 * Allows entities that are to be cleaned up to undergo
	 * final changes, most notably the removal of the clean
	 * up label.  This would allow some entities to remain
	 * in play if the rules of the game dictate so.
	 */

	boolean handle(EntityInterface entity);

}
