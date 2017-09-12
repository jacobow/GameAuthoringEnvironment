package gameauthoring.components.selectors.systemsselectors;

import configuration.MenuLanguage;
import gameauthoring.components.selectors.abstractselectors.AbstractCheckBoxSelector;
import gameengine.GameWorld;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;
import util.reflection.Reflection;

/**
 * Purpose: this class allows the user to add systems to their game, which allow for different 
 * kinds of gameplay
 * Dependencies: the class is dependent on the AbstractCheckBoxSelector, the AbstractSelector, 
 * the game world, and the package of CollisionHandler classes
 * Example Use: used to add a SolidHandler to a game in order to keep things that are solid from
 * going through one another
 * 
 * @author Larissa Cox
 *
 */

public class SystemsSelector extends AbstractCheckBoxSelector{
	private GameWorld myGameWorld;
	
	public SystemsSelector(GameWorld world) {
		super(MenuLanguage.getInstance().getValue("SelectYourSystems"));
		myGameWorld = world;
	}
	
	/**
	 * purpose: initializes the selector pop-up to display by calling the
	 * AbstractCheckBoxSelector's initialize method
	 */
	public void initialize(){
		for (CollisionHandlerInterface handler: myGameWorld.getCollisionSubsystems()){
			getClassSet().add(handler.getClass().getName());
		}
		super.initialize(CollisionHandlerInterface.class);
	}
	
	@Override
	protected Object getObject(String className) {
		return Reflection.createInstance(className);
	}
	
	@Override	
	protected void addSelections(){
		for (Object system: getSelected()){
			myGameWorld.addCollisionSubsystems((CollisionHandlerInterface)system);
		}
	}

}