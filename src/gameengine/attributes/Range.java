package gameengine.attributes;

import gameengine.attributes.interfaces.RangeInterface;

/**
 * 
 * @author walker
 * @author jacob
 *
 */
public class Range implements RangeInterface {
	private double myRange;
	
	public Range(){
		myRange = 0.0;
	}
	
	public Range(double startingrange){
		myRange = startingrange;
	}
	
	public double getRange() {
		return myRange;
	}
	
	public void setRange(double range) {
		myRange = range;
	}
	
	
}

