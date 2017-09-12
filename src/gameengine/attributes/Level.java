package gameengine.attributes;

import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.attributes.interfaces.LevelInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class Level implements LevelInterface, AttributeInterface{

	private DoubleProperty level;

	public Level() {
		level = new SimpleDoubleProperty(1);
	}

	public void setLevel(double newLevel){
		level.set(newLevel);
	}
	@Override
	public DoubleProperty retrieveLevel() {
		return level;
	}
	
	public double getLevel(){
		return level.doubleValue();
	}

	@Override
	public void levelUp(double levelCount) {
		level.set(level.get()+levelCount);
	}
	
	@Override
	public void levelDown(double levelCount) {
		level.set(level.get()-levelCount);
		if (level.get()<1){
			level.set(1);
		}
	}

	@Override
	public void reset() {
		level.set(1);
	}

}
