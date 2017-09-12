package gameengine.interfaces;

import java.util.Map;

import org.lwjgl.input.Controller;

import gameengine.systems.abilities.AbilityInterface;
import gameengine.utilities.HandHeld;
import gameengine.utilities.LiveList;
import gameengine.utilities.LiveMap;
import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;

/**
 * 
 * @author walker
 * @author vincent
 * 
 */
public interface WorldControllerInterface {
    /**
     * List of maps: Maps string representation of a controller button to the ability it activates
     * 
     * @return
     */
    public LiveList<HandHeld> getControllerToAbilityMap();
    
    /**
     * Representation of Controller by the index in the list of buttons to abilities. 
     * @return
     */
    public LiveMap<Controller,Integer> getControllerToIndexMap();

    /**
     * Add controller and its index in the controller-index map to World.
     * @return
     */
    public void addController(Controller controller, int index);

    /**
     * Add handheld class that allows for controller to ability mapping.
     * @param map
     */
    public void addControllerToAbilityMap(HandHeld map);

    
    /**
     * Map of keys to abilities
     * @return
     */
    abstract LiveMap<KeyCode,AbilityInterface> getKeyToAbilityMap();
    
    public AbilityInterface getOnClickAbility();
    
    public BooleanProperty mouseAbilityNotifier();

}
