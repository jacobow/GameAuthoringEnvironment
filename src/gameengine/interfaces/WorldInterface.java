package gameengine.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.AbilityInterface;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import gameengine.systems.wincondition_handlers.WinConditionInterface;
import gameengine.utilities.LiveMap;
import gameengine.utilities.LiveSet;
import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;

/**
 * 
 * @author walker
 *
 */
public interface WorldInterface {

	/**
	 *
	 * @return Set of all entities in play
	 */
	public LiveSet<EntityInterface> getEntities();

    /**
     *
     * @return Set of collision subsystems in play
     */
    public LiveSet<CollisionHandlerInterface> getCollisionSubsystems();

    /**
	 * @deprecated the key to entity map is useless and thus unused. It will forever on return null.
     * @return null
     */
    public LiveMap<KeyCode,List<EntityInterface>> getKeyToEntityMap();

    /**
     * Returns the map of keys to the ability they activate
     * @return
     */
    public LiveMap<KeyCode,AbilityInterface> getKeyToAbilityMap();

    /**
     *
     * @return what the engine thinks the height of the map is
     */
    public double getMapHeight();

    /**
     *
     * @return what the engine thinks the width of the map is
     */
    public double getMapWidth();


    /**
     * Add a new entity
     * Use case:
     * List attList = new ArrayList<AttributeInterface>();
     * attList.add(new Attribute) <-- repeat for all the attributes
     * addEntity(new Entity("ID", attList);
     *
     * @param entity
     */
    public void addEntity(EntityInterface entity);

    /**
     * For maze: We need a BaseHandler and a SolidHandler
     * @param system
     */
    public void addCollisionSubsystems(CollisionHandlerInterface system);

    /**
     * Ex:
     * addAbility(KeyCode.W, theEntityIAlreadyMade, new MoveForward());
     * @param key: The keycode you're referring to
     * @param entity: The entity it is controlling
     * @param ability: The ability it activates
     */
    public void addAbility(KeyCode key, EntityInterface entity, AbilityInterface ability);

    /**
     * Sets the size of the WHOLE map
     * @param width
     * @param height
     */
    public void setMapSize(double width, double height);

    /**
     * For maze: either EntiretyWinCondition or CollisionWinCondition
     * @param winCondition
     */
    public void addWinCondition(WinConditionInterface winCondition);

    /**
     *
     * @return
     */
	public LiveSet<WinConditionInterface> getWinConditions();

	/**
	 *
	 * @return
	 */
	public LiveSet<EntityInterface> getMyBaseEntities();

	/**
	 *
	 * @param baseEntity
	 */
	public void addMyBaseEntities(EntityInterface baseEntity);

	public LiveSet<StatisticsInterface> getStatistics();

	public void addStatistics(StatisticsInterface statistics);

	public EntityInterface getEntity(String ID);

	public void setMouseClickAbility(AbilityInterface newAbility);

}