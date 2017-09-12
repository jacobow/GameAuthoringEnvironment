package gameauthoring.components.selectors.systemsselectors;

import configuration.MenuLanguage;
import gameauthoring.components.selectors.abstractselectors.AbstractCheckBoxSelector;
import gameengine.GameWorld;
import gameengine.systems.zone_handlers.ZoneHandlerInterface;
import util.reflection.Reflection;

/**
 * Purpose: this class allows the user to add zones to their game, which indicate an area 
 * surrounding an entity that has some special properties 
 * Dependencies: the class is dependent on the AbstractCheckBoxSelector, the AbstractSelector, 
 * the game world, and the package of ZoneHandler classes
 * Example Use: used to add a TurretZoneHandler to a tower defense game in order to allow the
 * turret to shoot at enemies within it's zone
 * 
 * @author Larissa Cox
 *
 */

public class ZoneHandlerSelector extends AbstractCheckBoxSelector{
	private GameWorld myGameWorld;
	
	public ZoneHandlerSelector(GameWorld world) {
		super(MenuLanguage.getInstance().getValue("SelectYourZoneHandlers"));
		myGameWorld = world;
	}

	/**
	 * purpose: initializes the pop up, allowing the user to then input their preferences 
	 * and then close the pop up when they are done
	 */
	public void initialize(){
		for (ZoneHandlerInterface handler: myGameWorld.getZoneSubsystems()){
			getClassSet().add(handler.getClass().getName());
		}
		super.initialize(ZoneHandlerInterface.class);
	}
	
	@Override
	protected void addSelections() {
		for (Object system: getSelected()){
			myGameWorld.addZoneHandler((ZoneHandlerInterface)system);
		}
	}

	@Override
	protected Object getObject(String className) {
		return Reflection.createInstance(className);
	}

}
