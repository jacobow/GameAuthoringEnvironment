package gameengine.systems.statistics;

import java.util.Map;
import java.util.Set;

import gameengine.attributes.KillDeathCount;
import gameengine.attributes.Team;
import gameengine.entities.EntityInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import gameengine.systems.statistics.interfaces.TeamStatisticsInterface;
import gameengine.utilities.Pair;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class TeamStatistics implements StatisticsInterface, TeamStatisticsInterface {
	
	private Map<String, Pair<DoubleProperty, DoubleProperty>> teamKillDeathCount;
	private double killCount;
	private double deathCount;
	private double teamKillCount;
	private double teamDeathCount;

	@Override
	public Map<String, Pair<DoubleProperty, DoubleProperty>> getTeamKillDeathStatistics(Set<EntityInterface> entities) {
		entities.forEach((entity)-> {
			if (entity.containsAttribute(Team.class)){
				String teamName = entity.getAttribute(Team.class).getTeam();
				if (entity.containsAttribute(KillDeathCount.class)){
					killCount = entity.getAttribute(KillDeathCount.class).retrieveKillCount().get();
					deathCount = entity.getAttribute(KillDeathCount.class).retrieveDeathCount().get();
				}
				if (teamKillDeathCount.containsKey(teamName)){
					teamKillCount = teamKillDeathCount.get(teamName).getFirst().get();
					teamDeathCount = teamKillDeathCount.get(teamName).getLast().get();
				}
				teamKillDeathCount.put(teamName, new Pair(new SimpleDoubleProperty(killCount+teamKillCount), new SimpleDoubleProperty(deathCount+teamDeathCount)));
			}
		});
		
		return teamKillDeathCount;
	}

}
