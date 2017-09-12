package gameengine.systems.statistics;

import gameengine.attributes.Energy;
import gameengine.entities.EntityInterface;
import gameengine.systems.statistics.interfaces.EnergyStatisticsInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class EnergyStatistics implements StatisticsInterface, EnergyStatisticsInterface {

	private Energy energy;

	@Override
	public DoubleProperty getEnergy(EntityInterface entity) {
		if (entity.getAttribute(Energy.class) != null){
			energy = ((Energy)entity.getAttribute(Energy.class));
			return energy.retrieveEnergyPercentage();
		}
		return new SimpleDoubleProperty();
	}

}
