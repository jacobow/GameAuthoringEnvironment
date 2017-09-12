package gameplayer.interfaces;

import java.util.ArrayList;
import java.util.Map;

import gameengine.entities.EntityInterface;
import gameplayer.display.choosecharacter.TeamNameColor;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
/**
 * 
 * @author SamFurlong
 *
 */
public interface CharacterChooserInterface {
/**
 * 
 * @return
 * the node to be displayed
 */
	Node getNode();
/**
 * 
 * @return
 * returns a map of teams to entities 
 */
	Map<TeamNameColor, ArrayList<EntityInterface>> getChosenTeamMap();
/**
 * 
 * @return
 * returns a map of the chosen entities 
 */
	Map<EntityInterface, String> getChosenNameMap();
/**
 * returns true if start conditions are met
 * @return
 */
	BooleanProperty startProperty();

}
