package gameengine;

import gamedata.fileIO.XMLFileIO;
import gameengine.attributes.Spacial;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.ExternalEngineInterface;
import gameengine.systems.alternativesystems.AttributeRemovalManager;
import gameengine.systems.alternativesystems.CarryableManager;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;
import gameengine.systems.coresystems.AbilityManager;
import gameengine.systems.coresystems.CleanUpManager;
import gameengine.systems.coresystems.CollisionManager;
import gameengine.systems.coresystems.InputManager;
import gameengine.systems.coresystems.LifeDeathManager;
import gameengine.systems.coresystems.PathingManager;
import gameengine.systems.coresystems.StatisticsManager;
import gameengine.systems.coresystems.WinConditionManager;
import gameengine.systems.coresystems.ZoneManager;
import gameengine.systems.coresystems.interfaces.AddPathInterface;
import gameengine.systems.coresystems.interfaces.InputManagerInterface;
import gameengine.systems.zone_handlers.ZoneHandlerInterface;
import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

/**
 * 
 * @author jacob
 * @author walker
 * @author vincent
 * @author david
 * 
 */
public class GameEngine implements ExternalEngineInterface {

	private GameWorld myWorld;
	private InputManager myInputManager;
	private CollisionManager myCollisionManager;
	private CleanUpManager myCleanUpManager;
	private WinConditionManager myWinConditionManager;
	private AbilityManager myAbilityManager;
	private PathingManager myPathingManager;
	private StatisticsManager myStatisticsManager;

	private XMLFileIO myFileIO;

	private ZoneManager myZoneManager;
	private LifeDeathManager myLifeDeathManager;
    private CarryableManager myCarryableManager;
    private AttributeRemovalManager myAttributeRemovalManager;

	public GameEngine () {
		myWorld = new GameWorld();
		//myFileIO=new XMLFileIO();
		//System.out.println("Engine created");
		// Tester:
		//loadGame("XMLdata/test2XML.xml");
		initializeCoreSystems();
		initialize();
		//storeGame("XMLdata/TestEngine.xml");
	}

	public void update (double timePassed) {
	        myInputManager.update();
		myPathingManager.update(timePassed);
		myAbilityManager.update(timePassed);
		myCollisionManager.update(timePassed);
		for (CollisionHandlerInterface subsystem : myWorld.getCollisionSubsystems()) {
			subsystem.update(timePassed);
		}
		myZoneManager.update(timePassed);
		for(ZoneHandlerInterface zoneHandler : myWorld.getZoneSubsystems()) {
			zoneHandler.update(timePassed);
		}
		for (EntityInterface entity : myWorld.getEntities()) {
			if (entity.containsAttribute(Spacial.class)) {
				myCollisionManager.setPreCollisionPosition(entity);
			}
		}
                myCarryableManager.update(timePassed);
		myAttributeRemovalManager.update(timePassed);
		myCleanUpManager.update(timePassed);
		myWinConditionManager.update(timePassed);
		myLifeDeathManager.update(timePassed);
	}
	public void loadGame (String filePath) {
		//try {
		//setGameWorld(hardCodeFakeInputsToGetThisToWork());
		//	setGameWorld(myFileIO.loadGame(filePath));

		//}
		/*	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//initialize();
	}

	public void loadGameWorld (GameWorld world) {
		setGameWorld(world);
		System.out.println("load Game world " + world);

		initializeCoreSystems();

		myWorld.getEntities().forEach(entity -> {
                    if(entity.containsAttribute(Spacial.class)){
                            entity.getAttribute(Spacial.class).initShape();
                    }
            });
		System.out.println("load Game world " + myWorld);

	}

	public GameWorld getWorld () {
		System.out.println("get Game world " + myWorld);

		return myWorld;
	}
	public void setOnKeyPressed (KeyCode key) {
		myInputManager.keyboardButtonPressed(key);
	}
	public void setOnKeyReleased (KeyCode key) {
		myInputManager.keyboardButtonReleased(key);
	}
	public void setOnMouseAction (MouseEvent mouseEvent) {
		if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED))
			myInputManager.mouseButtonReleased(mouseEvent);
	}

	public void setOnMouseAction (MouseEvent mouseEvent, EntityInterface entityToAlter) {
		if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)){
			System.out.println("mouse");
			myInputManager.mouseButtonReleased(mouseEvent,entityToAlter);
		}
	}

	public void initializeCoreSystems(){
		myCleanUpManager = new CleanUpManager(myWorld);
		myAbilityManager = new AbilityManager();
		myPathingManager = new PathingManager(myWorld, myAbilityManager);
		myInputManager = new InputManager(myWorld, myAbilityManager,myPathingManager);
		myWinConditionManager = new WinConditionManager(myWorld);
		myCollisionManager = new CollisionManager(myWorld);
		myZoneManager = new ZoneManager(myWorld, myAbilityManager);
		myLifeDeathManager = new LifeDeathManager(myWorld);
		myAttributeRemovalManager= new AttributeRemovalManager(myWorld);
		myCarryableManager=new CarryableManager(myWorld);
		myStatisticsManager = new StatisticsManager();
	}

	public void initialize () {

	}
	private void setGameWorld (GameWorld world) {
		myWorld = world;
		System.out.println("Game world set");

	}
	public InputManagerInterface getInputManager () {
		return myInputManager;
	}
	public WinConditionManager getMyWinConditionManager () {
		return myWinConditionManager;
	}

    @Override
    public void storeGame (String filePath) {
        // TODO Auto-generated method stub

    }

    public AddPathInterface getPathingManager() {
		return myPathingManager;
	}

	@Override
	public BooleanProperty gameOver() {
		return myWinConditionManager.isGameOver();
	}
	
	@Override
	public StatisticsManager getStatisticsManager() {
		return myStatisticsManager;
	}

}