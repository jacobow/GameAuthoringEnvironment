package gameengine.attributes.interfaces;

public interface SlowInterface extends AttributeInterface{
	
	/**
	 * 
	 * @return the total time that the slow applies for
	 */
    double getTotalTime ();

    /**
     * 
     * @return the speed factor
     */
    double getSpeedModifier ();

    /**
     * 
     * @param speedModifier the new speed factor
     */
    void setSpeedModifier (double speedModifier);

    /**
     * 
     * @param time the duration of the slow 
     */
    void setTotalTime (double time);
}
