package gameengine.systems.statistics.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.interfaces.SubSystemInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

/**
 * @author DavidYoon
 *
 */
public interface LevelStatisticsInterface extends SubSystemInterface{

	public DoubleProperty getLevel(EntityInterface entity);

}
