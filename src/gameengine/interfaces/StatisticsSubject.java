package gameengine.interfaces;

import gameengine.entities.EntityInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import gameengine.utilities.LiveSet;

/**
 * 
 * @author walker
 * @author david
 */
public interface StatisticsSubject {

	public LiveSet<StatisticsInterface> getStatistics();

	public LiveSet<EntityInterface> getEntities();
}
