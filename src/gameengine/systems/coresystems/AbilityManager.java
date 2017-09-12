package gameengine.systems.coresystems;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import gameengine.systems.abilities.AbilityInterface;
import gameengine.systems.coresystems.interfaces.AbilityManagerInterface;
import gameengine.utilities.LiveObserver;
import javafx.collections.MapChangeListener.Change;

public class AbilityManager implements AbilityManagerInterface {
	private Map<AbilityInterface, double[]> activeAbilities;
	private Set<AbilityInterface> exceptionsSet;
	private Set<AbilityInterface> oldExceptions;
	private Set<AbilityInterface> newExceptions;
	private Map<AbilityInterface, double[]> oneTimeAbilities;
	private CooldownManager myCooldownManager;

	public AbilityManager(){
		myCooldownManager = new CooldownManager();
		activeAbilities = new LinkedHashMap<AbilityInterface, double[]>();
		oneTimeAbilities=new LinkedHashMap<AbilityInterface, double[]>();
		exceptionsSet = new HashSet<AbilityInterface>();
	}

	@Override
	public void addAbility(AbilityInterface toAdd, double[] components){
		activeAbilities.put(toAdd, components);
	}

	
	@Override
	public void addAbilityOnce(AbilityInterface toAdd, double[] components) {
		oneTimeAbilities.put(toAdd, components);
	}


	@Override
	public void addException(AbilityInterface toExcept){
		exceptionsSet.add(toExcept);
	}


	@Override
	public void removeAbility(AbilityInterface toRemove){
		if(activeAbilities.containsKey(toRemove)) activeAbilities.remove(toRemove);
	}
	
	@Override
	public void update(double elapsedTime){
		updateSet(activeAbilities, elapsedTime);
		updateSet(oneTimeAbilities, elapsedTime);
		if(!oneTimeAbilities.isEmpty()) oneTimeAbilities.clear();
		myCooldownManager.update(elapsedTime);
	}

	private void updateSet(Map<AbilityInterface,double[]> currentAbilities, double elapsedTime) {
		Iterator<AbilityInterface> abilityIterator = currentAbilities.keySet().iterator();
		while(abilityIterator.hasNext()){
			AbilityInterface currAbility = abilityIterator.next();
			if(!myCooldownManager.getCooldowns().containsKey(currAbility)){
				currAbility.activate(elapsedTime, currentAbilities.get(currAbility));
				if(currAbility.getCooldown()>0){
					currAbility.useCooldown();
					System.out.println("Cooldown = " + currAbility.getCooldown());
					myCooldownManager.addAbility(currAbility, currAbility.getCooldown());
				}
			}
		}
	}

}