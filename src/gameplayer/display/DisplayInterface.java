package gameplayer.display;

import application.MainController;
import gameauthoring.AuthoringToPlayerInterface;
import gameengine.interfaces.ExternalEngineInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * 
 * @author SamFurlong
 *
 */
public interface DisplayInterface {
	/**
	 * 
	 * @param ElapsedTime
	 * step loop that runs for a game
	 */
	public void step(double ElapsedTime);
	/**
	 * 
	 * @return
	 * the scene being used by the player
	 */
	public Scene getScene();
	/**
	 * 
	 * @param currentChoice
	 * returns the current engine being manipulated by the player
	 */
	void setEngine(ExternalEngineInterface currentChoice);
	/**
	 * paused anything that is being updated within the step loop
	 */
	void togglePause();
	/**
	 * 
	 * @param newScene
	 * sets the scene for the player environment
	 */
	void setScene(Scene newScene);
	/**
	 * 
	 * @return
	 * returns the current stage
	 */
	Stage getStage();


	/**
	 * 
	 * @param backEvent
	 * return to the screen that initialized player
	 * @param mainController
	 *
	 * @param s
	 * the stage
	 * @return
	 * initialized the player environment
	 */

	Scene init(EventHandler<?> backEvent, MainController mainController, Stage s);

}
