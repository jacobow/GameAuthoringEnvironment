package gameengine.systems.statistics;

import gameengine.attributes.KillDeathCount;
import gameengine.entities.EntityInterface;
import gameengine.systems.statistics.interfaces.KillDeathCountStatisticsInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import gameengine.utilities.Pair;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class KillDeathCountStatistics implements KillDeathCountStatisticsInterface, StatisticsInterface {

	private KillDeathCount killDeathCount;
	private Pair<DoubleProperty, DoubleProperty> killDeath;
	
	public DoubleProperty getKillCount(EntityInterface entity) {
		if (entity.containsAttribute(KillDeathCount.class)){
			killDeathCount = entity.getAttribute(KillDeathCount.class);
			return killDeathCount.retrieveKillCount();
		}
		return new SimpleDoubleProperty();
	}

	public DoubleProperty getDeathCount(EntityInterface entity) {
		if (entity.containsAttribute(KillDeathCount.class)){
			killDeathCount = entity.getAttribute(KillDeathCount.class);
			return killDeathCount.retrieveDeathCount();
		}
		return new SimpleDoubleProperty();
	}

	public Pair<DoubleProperty, DoubleProperty> getKillDeath(EntityInterface entity) {
		this.killDeath = new Pair<DoubleProperty, DoubleProperty>(getKillCount(entity), getDeathCount(entity));
		return killDeath;
	}

}
