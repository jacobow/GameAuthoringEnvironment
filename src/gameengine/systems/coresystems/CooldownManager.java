package gameengine.systems.coresystems;

import java.util.Iterator;

import gameengine.systems.abilities.AbilityInterface;
import gameengine.utilities.LiveMap;
import gameengine.utilities.LiveObserver;

public class CooldownManager {
	private LiveMap<AbilityInterface,Double> abilitiesOnCooldown;
	
	CooldownManager(){
		abilitiesOnCooldown = new LiveMap<AbilityInterface,Double>();
	}
	
	void addAbility(AbilityInterface toAdd, double cd){
		abilitiesOnCooldown.put(toAdd, cd);
	}
	
	void registerListener(LiveObserver o){
		abilitiesOnCooldown.registerObserver(o);
	}
	
	LiveMap<AbilityInterface, Double> getCooldowns(){
		return abilitiesOnCooldown;
	}
	
	void update(double elapsedTime){
		Iterator<AbilityInterface> myIter = abilitiesOnCooldown.keySet().iterator();
		while(myIter.hasNext()){
			AbilityInterface currentAbility = myIter.next();
			abilitiesOnCooldown.put(currentAbility,
					abilitiesOnCooldown.get(currentAbility)-elapsedTime);
			if(abilitiesOnCooldown.get(currentAbility)<=0) 
				abilitiesOnCooldown.remove(currentAbility);
		}
	}
	
	
}
