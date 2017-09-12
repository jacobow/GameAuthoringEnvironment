package usecases;

import java.util.HashMap;
import java.util.Map;

import gameengine.entities.EntityInterface;
import gameengine.interfaces.ExternalEngineInterface;
import gameengine.systems.abilities.AbilityInterface;
import javafx.scene.input.KeyCode;

public class Collisions {
	public void populateAbilityToKeyOrganizedDoubleMap(ExternalEngineInterface engine){
		engine.getInputManager();
		Map<EntityInterface, Map<AbilityInterface,KeyCode>> samMap = new HashMap<EntityInterface, Map<AbilityInterface,KeyCode>>();
		
		for(EntityInterface entity : engine.getWorld().getEntities()){
			Map<AbilityInterface,KeyCode> currAbToKeyMap = new HashMap<AbilityInterface,KeyCode>();
			for(KeyCode currentKey : engine.getInputManager().getKeyToAbilityMap().keySet()){
				AbilityInterface currAbility = engine.getInputManager().getKeyToAbilityMap().get(currentKey);
				if(entity.getAbilityMap().containsKey(currAbility.getClass())){
					currAbToKeyMap.put(currAbility, currentKey);
				}
			}
		}
		
	}
}
