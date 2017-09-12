package gameplayer.interfaces;

import java.util.Map;

import gameengine.entities.EntityInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;

public interface NodeStartInterface {

	ReadOnlyBooleanProperty startProperty();

	Node getNode();

	String getChosenName();

	Map<EntityInterface, String> getChosenNameMap();
	
}
