package gameauthoring;

import application.MainController;
import gameengine.GameWorld;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Interface for translating authoring environment properties into game world structures
 * @author 
 *
 */
public interface AuthoringToPlayerInterface {
	/**
	 * 
	 * @return
	 */
	public GameWorld getWorld();
	
	/**
	 * 
	 * @param newWorld
	 */
	public void setWorld(GameWorld newWorld);
	
	/**
	 * 
	 * @param backEvent
	 * @param MainController
	 * @param s
	 * @return
	 */
	Scene init(EventHandler<?> backEvent, MainController MainController, Stage s);
}
