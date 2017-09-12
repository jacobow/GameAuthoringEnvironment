package gameengine.attributes;

import gameengine.attributes.interfaces.PlayerInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * 
 * @author david
 * @author walker
 *
 */
public class Player implements PlayerInterface{
    
	private DoubleProperty winStatus;
	
	public Player(){
		winStatus = new SimpleDoubleProperty();
	}

	public DoubleProperty retrieveWinStatus() {
		return winStatus;
	}

	public void assignWinStatus(double winStatus) {
		this.winStatus.set(winStatus);
	}


}
