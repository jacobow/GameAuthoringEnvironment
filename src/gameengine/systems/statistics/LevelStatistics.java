package gameengine.systems.statistics;

import gameengine.attributes.Level;
import gameengine.entities.EntityInterface;
import gameengine.systems.statistics.interfaces.LevelStatisticsInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import javafx.beans.property.DoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class LevelStatistics implements StatisticsInterface, LevelStatisticsInterface{

	private Level level;

	@Override
	public DoubleProperty getLevel(EntityInterface entity) {
		if (entity.containsAttribute(Level.class)){
			level = entity.getAttribute(Level.class);
			return level.retrieveLevel();
		}
		return null;
	}

}
