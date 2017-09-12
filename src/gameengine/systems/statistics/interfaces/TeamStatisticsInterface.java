package gameengine.systems.statistics.interfaces;

import java.util.Map;
import java.util.Set;

import gameengine.entities.EntityInterface;
import gameengine.utilities.Pair;
import javafx.beans.property.DoubleProperty;

public interface TeamStatisticsInterface {

	Map<String, Pair<DoubleProperty, DoubleProperty>> getTeamKillDeathStatistics(
			Set<EntityInterface> entities);

}
