package gameengine.interfaces;

import gameengine.GameWorld;
import gameengine.systems.coresystems.InputManager;
import gameengine.systems.coresystems.WinConditionManager;
import javafx.beans.property.BooleanProperty;

public interface GameSystemsInterface {
    public void update(double timePassed, GameWorld world);
    public InputManager getInputManager ();
    public WinConditionManager getMyWinConditionManager ();
    public BooleanProperty isTheGameWon();
}
