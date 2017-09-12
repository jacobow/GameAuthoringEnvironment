package gameengine.systems.coresystems.interfaces;

import gameengine.systems.abilities.AbilityInterface;

public interface AbilityManagerInterface {

	void addAbility(AbilityInterface toAdd, double[] components);

	void addAbilityOnce(AbilityInterface toAdd, double[] components);

	void addException(AbilityInterface toExcept);

	void removeAbility(AbilityInterface toRemove);

	void update(double elapsedTime);

}