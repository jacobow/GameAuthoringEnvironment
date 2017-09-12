package gameengine.systems.statistics.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.interfaces.SubSystemInterface;
import javafx.beans.property.DoubleProperty;

/**
 * @author DavidYoon
 *
 */
public interface HealthStatisticsInterface extends SubSystemInterface{
	public DoubleProperty getHealth(EntityInterface entity);

}
