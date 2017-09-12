package gameengine.systems.abilities;

public interface AbilityInterface {
	
	default double getCooldown(){
		return 0;
	}
	
	default void setCooldown(double newCooldown){
	}
	
	default void setSecondaryCooldown(double newSecondaryCooldown){	
	}
	abstract void activate(double timeElapsed, double[] extraInputs);

	default void useCooldown(){}

	default void setSizeOfWaves(double newMax){}
}
