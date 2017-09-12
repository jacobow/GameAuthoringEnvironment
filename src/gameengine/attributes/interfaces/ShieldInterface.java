package gameengine.attributes.interfaces;

public interface ShieldInterface extends AttributeInterface{

	/**
	 * 
	 * @return Time for the shield
	 */
    double getTotalTime ();

    /**
     * 
     * @param time Time for the shield
     */
    void setTotalTime (double time);

    /**
     * 
     * @return How much health the shield absorbes
     */
    double getTotalHealth ();

    /**
     * 
     * @param health Total health the shield should absorb
     */
    void setTotalHealth (double health);
}
