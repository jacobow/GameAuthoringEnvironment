package gameengine.systems.coresystems;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.systems.coresystems.interfaces.StatisticsManagerInterface;
import gameengine.systems.coresystems.interfaces.SystemsInterface;
import gameengine.systems.statistics.EnergyStatistics;
import gameengine.systems.statistics.HealthStatistics;
import gameengine.systems.statistics.KillDeathCountStatistics;
import gameengine.systems.statistics.LevelStatistics;
import gameengine.systems.statistics.TeamStatistics;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import gameengine.systems.statistics.interfaces.TeamStatisticsInterface;
import gameengine.utilities.Pair;
import javafx.beans.property.DoubleProperty;

public class StatisticsManager implements StatisticsManagerInterface {

	private List<StatisticsInterface> statisticsList = new ArrayList<StatisticsInterface>();
	private HealthStatistics healthStatistics;
	private EnergyStatistics energyStatistics;
	private LevelStatistics levelStatistics;
	private KillDeathCountStatistics killDeathCountStatistics;
	private TeamStatistics teamStatistics;

	public StatisticsManager(){
		healthStatistics = new HealthStatistics();
		levelStatistics = new LevelStatistics();
		killDeathCountStatistics = new KillDeathCountStatistics();
		teamStatistics = new TeamStatistics();
		energyStatistics = new EnergyStatistics();

		this.register(healthStatistics);
		this.register(levelStatistics);
		this.register(energyStatistics);
		this.register(teamStatistics);
		this.register(killDeathCountStatistics);
	}

	public DoubleProperty getHealth(EntityInterface entity) {
		return healthStatistics.getHealth(entity);
	}
	
	public DoubleProperty getEnergy(EntityInterface entity) {
		return energyStatistics.getEnergy(entity);
	}

	public DoubleProperty getKillCountStatistics(EntityInterface entity) {
		return killDeathCountStatistics.getKillCount(entity);
	}

	public DoubleProperty getDeathCountStatistics(EntityInterface entity) {
		return killDeathCountStatistics.getDeathCount(entity);
	}

	public TeamStatistics getTeamStatistics() {
		return teamStatistics;
	}
	
	public void register(StatisticsInterface statistics) {
    	statisticsList.add(statistics);
	}


}
