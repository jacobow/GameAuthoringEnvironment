package gameengine.attributes.interfaces;

/**
 * This attribute allows an entity to do damage to another's health
 * when they collide. Typically used for weapons, but currently assigned to 
 * players
 * 
 * @param damage How much damage is done per impact
 */
public interface DamageInterface extends AttributeInterface{
    

    /**
     * Returns how much damage that entity does
     */
	double getDamageDone();
	 
	/**
     * Increase the damage of the entity
     * @param damage: CHANGE in damage
     */
    void increaseDamage(double damage);
    
    /**
     * Decrease the damage of the entity
     * @param damage: CHANGE in damage
     */
    void decreaseDamage(double damage);
    
    /**
     * Set the damage of the entity
     * @param damage: The new damage
     */
    void setDamageDone(double damage);

    double getTotalTime ();

    void setTotalTime (double time);

    void decreaseRemainingTime (double time);

    void assignTimeRemaining (double time);

    double retrieveTimeRemaining ();
}
