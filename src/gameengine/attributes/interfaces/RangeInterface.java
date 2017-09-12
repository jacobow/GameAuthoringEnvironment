package gameengine.attributes.interfaces;

public interface RangeInterface extends AttributeInterface {
	/**
	 *
	 * @author Walker Eacho
	 * @author Jacob Warner
	 */

	/**
	 * Sets the range 
	 * @param new range of the entity
	 */
	public void setRange(double newRange);
	
	/**
	 * 
	 * @return Range of the entity
	 */
	public double getRange();

	
}
