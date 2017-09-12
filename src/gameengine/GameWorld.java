package gameengine;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lwjgl.input.Controller;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.CollisionSubject;
import gameengine.interfaces.EntityCreationInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.interfaces.StatisticsSubject;
import gameengine.interfaces.WinConditionSubject;
import gameengine.interfaces.WorldControllerInterface;
import gameengine.interfaces.WorldInterface;
import gameengine.interfaces.ZoneSubject;
import gameengine.systems.abilities.AbilityInterface;
import gameengine.systems.abilities.NullAbility;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import gameengine.systems.wincondition_handlers.WinConditionInterface;
import gameengine.systems.zone_handlers.ZoneHandlerInterface;
import gameengine.utilities.HandHeld;
import gameengine.utilities.LiveList;
import gameengine.utilities.LiveMap;
import gameengine.utilities.LiveSet;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;

/**
 * @author jacob
 * @author walker
 * @author vincent
 * @author david
 */

public class GameWorld implements WorldInterface, EntityCreationInterface, EntitySetSubject, CollisionSubject, ZoneSubject, StatisticsSubject, WinConditionSubject, WorldControllerInterface{

        @XStreamAlias("myEntities")
	private LiveSet<EntityInterface> myEntities;
        @XStreamAlias("myFakeEntities")
	private LiveSet<EntityInterface> myFakeEntities;
        @XStreamAlias("myEntitySearchMap")
	private LiveMap<String, EntityInterface> myEntitySearchMap;
        @XStreamAlias("myBaseEntities")
	private LiveSet<EntityInterface> myBaseEntities;
        @XStreamAlias("myPlayerEntities")
        private LiveSet<EntityInterface> myPlayerEntities;
        @XStreamAlias("myCollisionSubsystems")
	private LiveSet<CollisionHandlerInterface> myCollisionSubsystems;
        @XStreamAlias("myZoneSubsystems")
	private LiveSet<ZoneHandlerInterface> myZoneSubsystems;
        @XStreamAlias("myKeyToAbilityMap")
	private LiveMap<KeyCode, AbilityInterface> myKeyToAbilityMap;
        @XStreamAlias("myControllerToIndexMap")
	private LiveMap<Controller,Integer> myControllerToIndexMap;
        @XStreamAlias("myControllerToAbilityMapList")
        private LiveList<HandHeld> myControllerToAbilityMapList;
        @XStreamAlias("myWinconditions")
	private LiveSet<WinConditionInterface> myWinConditions;
        @XStreamAlias("myStatistics")
	private LiveSet<StatisticsInterface> myStatistics;

	private AbilityInterface myOnClickAbility;
	private BooleanProperty myOnClickNotifier;
	private double mapHeight;
	private double mapWidth;

	public GameWorld () {

		myEntities= new LiveSet<EntityInterface>();
		myFakeEntities = new LiveSet<EntityInterface>();

		myEntitySearchMap = new LiveMap<String, EntityInterface>();
		myBaseEntities=new LiveSet<EntityInterface>();
		myPlayerEntities=new LiveSet<EntityInterface>();
		myCollisionSubsystems = new LiveSet<CollisionHandlerInterface>();
		myZoneSubsystems = new LiveSet<ZoneHandlerInterface>();
		myStatistics = new LiveSet<StatisticsInterface>();
		myWinConditions = new LiveSet<WinConditionInterface>();
		myKeyToAbilityMap= new LiveMap<KeyCode, AbilityInterface>();
		myControllerToIndexMap = new LiveMap<Controller,Integer>();
		myControllerToAbilityMapList= new LiveList<HandHeld>();
		myOnClickNotifier = new SimpleBooleanProperty();
		myOnClickNotifier.set(false);
		myOnClickAbility= new NullAbility(null);
		mapHeight=0;
		mapWidth=0;
	}

	public void clearAllObserver(){
		myEntities.clearObservers();
		myFakeEntities.clearObservers();
		myEntitySearchMap.clearObservers();
		myBaseEntities.clearObservers();
		myPlayerEntities.clearObservers();
		myCollisionSubsystems.clearObservers();
		myZoneSubsystems.clearObservers();
		myKeyToAbilityMap.clearObservers();
		myControllerToIndexMap.clearObservers();
		myControllerToAbilityMapList.clearObservers();
		myWinConditions.clearObservers();
		//myStatistics.clearObservers();
	}

	public void addEntity(EntityInterface entity){
		System.out.println("Entity Added");
		boolean check = myEntities.add(entity);
		System.out.println("We now have " +myEntities.size()+" entities. - World\t"+check);
	}

	public void addFakeEntity(EntityInterface entity){
		myFakeEntities.add(entity);
	}

	public void addCollisionSubsystems(CollisionHandlerInterface system){
		myCollisionSubsystems.add(system);
	}

	public void addZoneHandler(ZoneHandlerInterface zoneHandler) {
		myZoneSubsystems.add(zoneHandler);
	}

	/**
	 * It's back.
	 */
	public void addAbility(KeyCode key, EntityInterface entity, AbilityInterface ability){
		entity.addAbility(ability, key);
		if(key!=null) myKeyToAbilityMap.put(key, ability);
	}

	/**
	 * @deprecated lol use the three term one again... Using this method will result in lack of functionality
	 * @param key
	 * @param ability
	 */
	public void addAbility(KeyCode key, AbilityInterface ability){
		myKeyToAbilityMap.put(key, ability);
	}


	public void setMapSize(double width, double height){
		mapWidth=width;
		mapHeight=height;
	}

	@Override
	public void addWinCondition(WinConditionInterface winCondition) {
		System.out.println("Win condition added = World");
		myWinConditions.add(winCondition);
	}

	@Override
	public LiveSet<WinConditionInterface> getWinConditions() {
		return myWinConditions;
	}

	public LiveSet<EntityInterface> getMyBaseEntities() {
		return myBaseEntities;
	}

	public void addMyBaseEntities(EntityInterface baseEntity) {
		this.myBaseEntities.add(baseEntity);
	}


	public LiveSet<EntityInterface> getEntities(){
		return myEntities;
	}

	public LiveSet<EntityInterface> getFakeEntities(){
		return myFakeEntities;
	}

	public LiveSet<CollisionHandlerInterface> getCollisionSubsystems(){
		return myCollisionSubsystems;
	}

	/**
	 * @deprecated key to entity map is unused and useless
	 */
	public LiveMap<KeyCode,List<EntityInterface>> getKeyToEntityMap(){
		return null;
	}

	public LiveMap<KeyCode,AbilityInterface> getKeyToAbilityMap(){
		return myKeyToAbilityMap;
	}

	public double getMapHeight(){
		return mapHeight;
	}

	public double getMapWidth(){
		return mapWidth;
	}

	public LiveSet<ZoneHandlerInterface> getZoneSubsystems() {
		return myZoneSubsystems;
	}

	public LiveSet<StatisticsInterface> getStatistics() {
		return myStatistics;
	}

	public void addStatistics(StatisticsInterface statistics) {
		this.myStatistics.add(statistics);
	}

	public EntityInterface getEntity(String ID) {
		return myEntitySearchMap.get(ID);
	}

	public Set<EntityInterface> getMyPlayerEntities() {
		return myPlayerEntities;
	}

	public LiveList<HandHeld> getControllerToAbilityMap(){
		return myControllerToAbilityMapList;
	}

	public LiveMap<Controller,Integer> getControllerToIndexMap(){
		return myControllerToIndexMap;
	}

	public void addControllerToAbilityMap(HandHeld map){
		if(map.size()>0) myControllerToAbilityMapList.add(map);
	}

	public void addController(Controller controller, int index){
		myControllerToIndexMap.put(controller, index);
	}

	public void setMouseClickAbility(AbilityInterface newAbility){
		myOnClickAbility = newAbility;
		myOnClickNotifier.set(!mouseAbilityNotifier().get());
	}
	public AbilityInterface getOnClickAbility(){
		return myOnClickAbility;
	}
	public BooleanProperty mouseAbilityNotifier(){
		return myOnClickNotifier;
	}



}
