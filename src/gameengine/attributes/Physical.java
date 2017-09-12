package gameengine.attributes;

import gameengine.attributes.interfaces.PhysicalInterface;

/**
 * 
 * @author jacob
 *
 */
public class Physical implements PhysicalInterface {

	private double preCollisionX;
	private double preCollisionY;
	private double preCollisionOrientation;

	@Override
	public void assignPreCollisionX(double x) {
		preCollisionX = x;
	}

	@Override
	public void assignPreCollisionY(double y) {
		preCollisionY = y;
	}

	@Override
	public double retrievePreCollisionX() {
		return preCollisionX;
	}

	@Override
	public double retrievePreCollisionY() {
		return preCollisionY;
	}

	@Override
	public void assignPreCollisionOrientation (double orientation) {
		preCollisionOrientation=orientation;

	}

	@Override
	public double retrievePreCollisionOrientation () {
		return preCollisionOrientation;
	}
}
