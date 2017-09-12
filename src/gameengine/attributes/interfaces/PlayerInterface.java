package gameengine.attributes.interfaces;

import javafx.beans.property.DoubleProperty;

public interface PlayerInterface extends AttributeInterface {
	public DoubleProperty retrieveWinStatus();

	public void assignWinStatus(double winStatus);
	
}
