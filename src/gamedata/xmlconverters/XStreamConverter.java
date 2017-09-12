package gamedata.xmlconverters;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.input.Controller;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.NamedCollectionConverter;
import com.thoughtworks.xstream.converters.extended.NamedMapConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import gameengine.GameWorld;
import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.AbilityInterface;
import gameengine.systems.collision_handlers.CollisionHandlerInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import gameengine.systems.wincondition_handlers.WinConditionInterface;
import gameengine.systems.zone_handlers.ZoneHandlerInterface;
import gameengine.utilities.HandHeld;
import gameengine.utilities.LiveList;
import gameengine.utilities.LiveMap;
import gameengine.utilities.LiveSet;
import javafx.scene.input.KeyCode;

public class XStreamConverter {

    private XStream myXStream;
    
    public XStreamConverter(){
        myXStream = new XStream(new DomDriver());        
    }
    
    public XStream registerXStreamConverters(){
        registerCollectionConverter("myEntities", LiveSet.class, EntityInterface.class);
        registerCollectionConverter("myFakeEntities", LiveSet.class, EntityInterface.class);
        registerMapConverter("myEntitySearchMap", "Name", String.class, "Entity", EntityInterface.class);
        registerCollectionConverter("myBaseEntities", LiveSet.class, EntityInterface.class);
        registerCollectionConverter("myPlayerEntities", LiveSet.class, EntityInterface.class);
        registerCollectionConverter("myCollisionSubsystems", LiveSet.class, CollisionHandlerInterface.class);
        registerCollectionConverter("myZoneSubsystems", LiveSet.class, ZoneHandlerInterface.class);
        registerMapConverter("myKeyToAbilityMap", "KeyCode", KeyCode.class, "Abilities", AbilityInterface.class);
        registerMapConverter("myControllerToIndexMap", "Controller", Controller.class, "Integer", Integer.class);
        registerMapConverter("myControls", "String", String.class, "Abilities", AbilityInterface.class);
        registerCollectionConverter("myControllerToAbilityMapList", LiveList.class, HandHeld.class);
        registerCollectionConverter("myWinConditions", LiveSet.class, WinConditionInterface.class);
        registerCollectionConverter("myStatistics", LiveSet.class, StatisticsInterface.class);
        
        
        registerCustomPropertyConverters();
        return myXStream;            
    }
    
    private void registerCustomPropertyConverters() {
        Map<String, AbilityInterface> instance = new HashMap<String, AbilityInterface>();
        myXStream.registerConverter(new BooleanPropertyConverter(myXStream.getMapper()));
        myXStream.registerConverter(new DoublePropertyConverter(myXStream.getMapper()));
        myXStream.registerConverter(new ObjectPropertyConverter(myXStream.getMapper()));
    }
    
    private void registerCollectionConverter(String aliasedName, Class collectionType, Class setType){
        myXStream.registerLocalConverter(GameWorld.class, aliasedName,
                                         new NamedCollectionConverter(collectionType, 
                                                                      myXStream.getMapper(), 
                                                                      aliasedName, 
                                                                      setType));
    }
    
    private void registerMapConverter(String aliasedName, String keyName, Class keyClass, 
                                      String valueName, Class valueClass){
        myXStream.registerLocalConverter(GameWorld.class, aliasedName,
                                         new NamedMapConverter(LiveMap.class, 
                                                                      myXStream.getMapper(),
                                                                      aliasedName,
                                                                      keyName, keyClass, 
                                                                      valueName, valueClass));
    }
}
