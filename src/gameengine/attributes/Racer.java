package gameengine.attributes;

import gameengine.attributes.interfaces.RacerInterface;

/**
 * 
 * @author jacob
 *
 */
public class Racer implements RacerInterface {

	public boolean myStart;
	public boolean myFinish;
	public boolean myFinishing;
	public int myLapCount;

	public Racer() {
		myStart = false;
		myFinish = false;
		myFinishing = false;
		myLapCount = 0;
	}

	public void flipStart() {
		myStart = !myStart;
	}

	public void flipFinish() {
		myFinish = !myFinish;
	}

	public void flipFinishing() {
		myFinishing = !myFinishing;
	}

	public boolean isStart() {
		return myStart;
	}

	public boolean isFinish() {
		return myFinish;
	}

	public boolean isFinishing() {
		return myFinishing;
	}

	public void increaseLapCount() {
		myLapCount++;
	}

	public void decreaseLapCount() {
		myLapCount--;
	}

	public int retrieveLapCount() {
		return myLapCount;
	}
}
