package gameengine.systems.statistics.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.interfaces.SubSystemInterface;
import javafx.beans.property.DoubleProperty;

/**
 * @author DavidYoon
 *
 */
public interface KillDeathCountStatisticsInterface extends SubSystemInterface{
	public DoubleProperty getKillCount(EntityInterface entity);

	public DoubleProperty getDeathCount(EntityInterface entity);

}
