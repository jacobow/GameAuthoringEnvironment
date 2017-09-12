package gameauthoring.components.selectors.systemsselectors;

import configuration.MenuLanguage;
import gameauthoring.components.selectors.abstractselectors.AbstractCheckBoxSelector;
import gameengine.GameWorld;
import gameengine.systems.wincondition_handlers.WinConditionInterface;
import util.reflection.Reflection;

/**
 * Purpose: this class allows the user to add win conditions to their game, which tell the game
 * engine when the game is over and which player is the winner
 * Dependencies: the class is dependent on the AbstractCheckBoxSelector, the AbstractSelector, 
 * the game world, and the package of WinCondition classes
 * Example Use: used to add a FinishLineWinCondition to a racing game in order to allow the
 * player who first crosses the finish line object to win the game
 * 
 * @author Larissa Cox
 *
 */

public class WinConditionSelector extends AbstractCheckBoxSelector {
	private GameWorld myGameWorld;
	
	public WinConditionSelector(GameWorld world) {
		super(MenuLanguage.getInstance().getValue("SelectYourWinConditions"));
		myGameWorld = world;
	}
	
	/**
	 * purpose: initializes the pop up, allowing the user to then input their preferences 
	 * and then close the pop up when they are done
	 */
	public void initialize(){
		
		for (WinConditionInterface handler: myGameWorld.getWinConditions()){
			getClassSet().add(handler.getClass().getName());
		}
		super.initialize(WinConditionInterface.class);
	}
	
	@Override
	protected Object getObject(String className) {
		return Reflection.createInstance(className, new Object[] {myGameWorld});
	}
	@Override	
	protected void addSelections(){
		for (Object winCondition : getSelected()){
			myGameWorld.addWinCondition((WinConditionInterface)winCondition);
		}
	}
}