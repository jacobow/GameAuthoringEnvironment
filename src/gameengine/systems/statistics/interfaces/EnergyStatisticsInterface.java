package gameengine.systems.statistics.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.interfaces.SubSystemInterface;
import javafx.beans.property.DoubleProperty;

/**
 * @author DavidYoon
 *
 */
public interface EnergyStatisticsInterface extends SubSystemInterface {

	public DoubleProperty getEnergy(EntityInterface entity);

}
