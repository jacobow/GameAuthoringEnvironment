package gameengine.attributes.interfaces;

import javafx.beans.property.DoubleProperty;

public interface KillDeathCountInterface {

	public DoubleProperty retrieveKillCount();
	
	public DoubleProperty retrieveDeathCount();

	public void addDeathCount(double count);

	public void addKillCount(double count);
	
}
