package gameengine.attributes.interfaces;

public interface CarryableInterface extends AttributeInterface{

	/**
	 * Currently unimplemented 
	 * @return
	 */
    boolean beingCarried();
    
    void assignCarryable(boolean carryable);

    boolean isDropped ();

    void assignDropped (boolean dropped);

}