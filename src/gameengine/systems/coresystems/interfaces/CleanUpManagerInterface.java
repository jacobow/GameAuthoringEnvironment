package gameengine.systems.coresystems.interfaces;

import gameengine.systems.clean_up_bypasses.CleanUpBypassInterface;

public interface CleanUpManagerInterface extends SystemsInterface {

	/**
	 * Manages the entities that need to be removed from the game.
	 * Allows for last second bypasses.
	 */

	/**
	 * Registers a CleanUpBypass to listen for the entities that
	 * are about to be cleaned up.
	 * @param bypass
	 */
	void register(CleanUpBypassInterface bypass);
}
