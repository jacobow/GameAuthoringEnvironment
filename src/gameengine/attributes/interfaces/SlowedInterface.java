package gameengine.attributes.interfaces;

public interface SlowedInterface extends AttributeInterface{

	/**
	 * 
	 * @return total duration of the slow
	 */
    double getTotalTime ();

    /**
     * 
     * @return speed modifier of the slow
     */
    double getSpeedModifier ();

    /**
     * 
     * @return remaining duration of the slow
     */
    double getTimeRemaining ();

    /**
     * 
     * @param speedModifier The new modifier of the slow
     */
    void setSpeedModifier (double speedModifier);

    /**
     * 
     * @param time the total duration of the slow
     */
    void setTotalTime (double time);

    /**
     * 
     * @param Decreases the remaining time by this amount
     */
    void decreaseRemainingTime (double time);

    void setTimeRemaining (double time);
}
