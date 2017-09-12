package gameengine.attributes.interfaces;

public interface PoisonedInterface extends AttributeInterface{
    
	/**
	 * 
	 * @param damage Damage of the poisoned
	 */
	public void setDamage(double damage);
    
	/**
	 * 
	 * @param time Total duration
	 */
	public void setTotalTime(double time);
    
	/**
	 * 
	 * @param time Amount to decrease remaining time by
	 */
	public void decreaseRemainingTime (double time);

	/**
	 * 
	 * @return damage of the poison
	 */
    public double getDamage();
    
    /**
     * 
     * @return Remaining duration
     */
    public double getTimeRemaining();
    
    /**
     * 
     * @return total duration
     */
    public double getTotalTime();

    void setTimeRemaining (double time);
}
