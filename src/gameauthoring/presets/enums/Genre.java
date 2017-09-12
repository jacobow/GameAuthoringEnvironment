package gameauthoring.presets.enums;

import java.util.Set;
import java.util.HashSet;
import gameengine.GameWorld;
import gameengine.systems.collision_handlers.BaseHandler;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;
import gameengine.systems.collision_handlers.FinishLineHandler;
import gameengine.systems.collision_handlers.SolidHandler;
import gameengine.systems.statistics.HealthStatistics;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import gameengine.systems.wincondition_handlers.EntiretyWinCondition;
import gameengine.systems.wincondition_handlers.FinishLineWinCondition;
import gameengine.systems.wincondition_handlers.WinConditionInterface;
import gameengine.systems.wincondition_handlers.ZeroHealthWinCondition;
import gameengine.systems.zone_handlers.StealthHandler;
import gameengine.systems.zone_handlers.TurretHandler;
import gameengine.systems.zone_handlers.ZoneHandlerInterface;
import util.reflection.Reflection;

/**
 * @author DavidYoon
 *
 */
public enum Genre {
	
	RACING(){
		public void createLevel(GameWorld world){
			world.addCollisionSubsystems(new SolidHandler());
			world.addCollisionSubsystems(new FinishLineHandler());
			world.addWinCondition(new FinishLineWinCondition(world));
		}
	},

	STEALTH(){
		public void createLevel(GameWorld world){
			world.addCollisionSubsystems(new SolidHandler());
			world.addCollisionSubsystems(new BaseHandler());
			world.addZoneHandler(new StealthHandler());
			world.addWinCondition(new EntiretyWinCondition(world));
		}
	},
	
	TOWERDEFENSE(){
		public void createLevel(GameWorld world){
			world.addCollisionSubsystems(new SolidHandler());
			world.addCollisionSubsystems(new BaseHandler());
			world.addZoneHandler(new StealthHandler());
			world.addWinCondition(new EntiretyWinCondition(world));
		}
	},
	
	SLEAGUE(){
		public void createLevel(GameWorld world){
			world.addZoneHandler(new TurretHandler());
			world.addCollisionSubsystems(new BaseHandler());
			world.addCollisionSubsystems(new BaseHandler());
			world.addWinCondition(new ZeroHealthWinCondition(world));
			world.addStatistics(new HealthStatistics());
		}
	};

	private Reflection reflectionFactory;
	private GameWorld world;
	Set<String> setOfCollisionHandlers;
	protected Set<String> setOfWinConditions;
	protected Set<String> setOfStatistics;
	protected Set<String> setOfZoneHandlers;
	
	Genre() {
		setOfCollisionHandlers = new HashSet<String>();
		setOfZoneHandlers = new HashSet<String>();
		setOfWinConditions = new HashSet<String>();
		setOfStatistics = new HashSet<String>();
		
    }
	
	
	public void createLevel(GameWorld world){
		setOfCollisionHandlers.forEach((handler)-> addCollisionHandlers(handler));
		setOfWinConditions.forEach((handler)-> addWinConditions(handler));
		setOfStatistics.forEach((handler)-> addStatistics(handler));
		setOfZoneHandlers.forEach((handler)-> addZoneHandler(handler));
	}
	
	private void addZoneHandler(String handler) {
		ZoneHandlerInterface zoneHandler = (ZoneHandlerInterface) reflectionFactory.createInstance(handler);
		world.addZoneHandler(zoneHandler);
	}

	private void addCollisionHandlers(String handler){
		CollisionHandlerInterface collisionHandler = (CollisionHandlerInterface) reflectionFactory.createInstance(handler);
		world.addCollisionSubsystems(collisionHandler);
	}
	
	private void addWinConditions(String handler){
		WinConditionInterface winCondition = (WinConditionInterface) reflectionFactory.createInstance(handler);
		world.addWinCondition(winCondition);
	}
	
	private void addStatistics(String handler){
		StatisticsInterface statistics = (StatisticsInterface) reflectionFactory.createInstance(handler);
		world.addStatistics(statistics);
	}
	
}
