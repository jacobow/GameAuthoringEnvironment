package gameengine.attributes.interfaces;

public interface RespawnableInterface extends AttributeInterface{

	/**
	 * Respawnable allows the entity to avoid being
	 * cleaned up if it has multiple lives.
	 *
	 * @author Jacob Warner
	 */

	/**
	 * Takes away a life
	 */
	public void loseLife();

	/**
	 * Gets number of lives
	 */
	public double lifeCount();

	/**
	 * Sets total lives remaining
	 */
	public void setLives(double newLives);

	public void setRespawnX(double x);

	public void setRespawnY(double y);

	public double getRespawnX();

	public double getRespawnY();

}
