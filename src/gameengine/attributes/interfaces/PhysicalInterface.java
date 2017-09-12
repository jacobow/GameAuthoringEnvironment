package gameengine.attributes.interfaces;

public interface PhysicalInterface extends AttributeInterface {

	/**
	 * Physical entities are detected by the CollisionManager
	 * when they collide with other Physical entities
	 *
	 * @author Jacob Warner
	 */

	/**
	 * Sets where the entity's x was before collision
	 * @param x
	 */
	void assignPreCollisionX(double x);

	/**
	 * Sets where the entity's y was before collision
	 * @param y
	 */
	void assignPreCollisionY(double y);

	/**
	 * Sets where the entity's orienation was before collision
	 * @param orientation (Degrees)
	 */
	void assignPreCollisionOrientation(double orientation);

	/**
	 *
	 * @return pre collision x
	 */
	double retrievePreCollisionX();

	/**
	 *
	 * @return pre collision y
	 */
	double retrievePreCollisionY();

	/**
	 *
	 * @return pre collision orientation: degrees
	 */
	double retrievePreCollisionOrientation();
}
