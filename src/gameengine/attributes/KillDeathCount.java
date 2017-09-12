package gameengine.attributes;

import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.attributes.interfaces.KillDeathCountInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class KillDeathCount implements KillDeathCountInterface, AttributeInterface{

	private DoubleProperty killCount;
	private DoubleProperty deathCount;
	
	public KillDeathCount(){
		killCount = new SimpleDoubleProperty();
		deathCount = new SimpleDoubleProperty();
	}
	
	@Override
	public void addKillCount(double count) {
		killCount.set(killCount.intValue()+count);
	}

	@Override
	public void addDeathCount(double count) {
		deathCount.set(deathCount.intValue() + count);
	}
	
	@Override
	public DoubleProperty retrieveKillCount() {
		return killCount;
	}
	
	public double getKillCount() {
		return killCount.doubleValue();
	}

	@Override
	public DoubleProperty retrieveDeathCount() {
		return deathCount;
	}
	
	public double getDeathCount() {
		return deathCount.doubleValue();
	}

}
