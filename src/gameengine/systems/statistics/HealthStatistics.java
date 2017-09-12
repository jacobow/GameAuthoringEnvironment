package gameengine.systems.statistics;

import gameengine.attributes.Health;
import gameengine.entities.EntityInterface;
import gameengine.systems.statistics.interfaces.HealthStatisticsInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Returns the healthPercentage of the entity
 *
 * @author DavidYoon
 *
 */
public class HealthStatistics implements StatisticsInterface, HealthStatisticsInterface {

	private Health health;

	@Override
	public DoubleProperty getHealth(EntityInterface entity) {
		if (entity.containsAttribute(Health.class)){
			health = entity.getAttribute(Health.class);
			return health.retrieveHealthPercentage();
		}
		return new SimpleDoubleProperty();
	}

}
