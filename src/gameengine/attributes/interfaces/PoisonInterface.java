package gameengine.attributes.interfaces;

public interface PoisonInterface extends AttributeInterface {
    
	/**
	 * 
	 * @param damage Damage of the poison
	 */
	public void setDamage(double damage);
	
	/**
	 * 
	 * @param time Duration of the poison
	 */
    public void setTotalTime(double time);

    /**
     * 
     * @return Poison damage
     */
    public double getDamage();
    
    /**
     * 
     * @return Poison duration
     */
    public double getTotalTime();
}