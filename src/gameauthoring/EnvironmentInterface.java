package gameauthoring;

import gamedata.data.GameData;
import javafx.scene.Scene;

/**
 * Default interface for the AuthoringEnviroment class
 * @author michaelseaberg
 *
 */
public interface EnvironmentInterface {
	
	/**
	 * Returns the full JavaFX Scene of the UserInterface to be placed in a stage
	 * @return Scene Object
	 */
	Scene getScene();
	
	/**
	 * Used to initialize a UserInterface object and populate it with an initial simulation.
	 * Should: Associate a group with the scene
	 */


	public void initialize(GameData data);
}
