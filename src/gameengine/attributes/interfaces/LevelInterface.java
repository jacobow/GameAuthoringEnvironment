package gameengine.attributes.interfaces;

import javafx.beans.property.DoubleProperty;

public interface LevelInterface{

	public DoubleProperty retrieveLevel();

	public void setLevel(double newLevel);

	public void reset();

	public void levelUp(double levelCount);

	public void levelDown(double levelCount);

}

