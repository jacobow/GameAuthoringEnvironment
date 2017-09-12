package gameengine.attributes.interfaces;

public interface VisibleInterface extends AttributeInterface {

	/**
	 * 
	 * @return if the object is currently visible
	 */
	boolean isVisible();

	/**
	 * Set if visible
	 * @param visible
	 */
	void assignVisible(boolean physical);

}
