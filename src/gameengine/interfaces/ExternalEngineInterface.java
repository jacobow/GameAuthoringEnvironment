package gameengine.interfaces;


import gameengine.GameWorld;
import gameengine.entities.EntityInterface;
import gameengine.systems.coresystems.StatisticsManager;
import gameengine.systems.coresystems.WinConditionManager;
import gameengine.systems.coresystems.interfaces.AddPathInterface;
import gameengine.systems.coresystems.interfaces.InputManagerInterface;
import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

/**
 * @author david
 * @author walker
 * @author jacob
 * @author sam
 *
 */
public interface ExternalEngineInterface {
    public void update(double timePassed);
    
    public void loadGame(String filePath);
    
    public void loadGameWorld(GameWorld world);
      
    public void storeGame(String filePath);
    
    public GameWorld getWorld();
    
    public void setOnKeyPressed(KeyCode key);
    
    public void setOnKeyReleased(KeyCode key);
    
    public void setOnMouseAction(MouseEvent mouseEvent, EntityInterface entityToAlter);
    
    public void setOnMouseAction(MouseEvent mouseEvent);
    
    public InputManagerInterface getInputManager ();
    
    public WinConditionManager getMyWinConditionManager ();
    
    public AddPathInterface getPathingManager();
    
	public BooleanProperty gameOver();

	public StatisticsManager getStatisticsManager();
   
}
