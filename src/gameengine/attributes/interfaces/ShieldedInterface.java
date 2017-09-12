package gameengine.attributes.interfaces;

public interface ShieldedInterface extends AttributeInterface{

	/**
	 * 
	 * @return Remaining time in the shield
	 */
    double getTimeRemaining ();

    /**
     * 
     * @return Total time the shield started with
     */
    double getTotalTime ();

    /**
     * 
     * @param time Total time the shield starts with
     */
    void setTotalTime (double time);

    /**
     * Decreases the remaining time by 
     * @param time the amount of time to decrease time remaining by
     */
    void decreaseRemainingTime (double time);
    
    /**
     * 
     * @return Current remaining health
     */
    double getCurrentHealth ();

    /**
     * 
     * @param health to decrease the remaining health by
     */
    void decreaseCurrentHealth (double health);

    void setTimeRemaining (double time);

    double getTotalShieldHealth ();

    void setTotalShieldHealth (double health);

    void setCurrentShieldHealth (double health);
}
