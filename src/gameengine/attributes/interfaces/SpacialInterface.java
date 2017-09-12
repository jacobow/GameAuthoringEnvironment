package gameengine.attributes.interfaces;
import gameengine.utilities.BoundingBox;
import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

public interface SpacialInterface extends AttributeInterface{
	/**
	 * Set absolute X value
	 * @param newX
	 */
	abstract void setX(double newX);
	
	/**
	 * Returns the x value of the entity
	 * @return x
	 */
	abstract DoubleProperty retrieveX();
	
	/**
	 * Set absolute Y value
	 * @param newY
	 */
	abstract void assignY(double newY);
	
	/**
	 * Returns the y value of the entity
	 * @return y
	 */
	abstract DoubleProperty retrieveY();
	
	/**
	 * Set absolute Orientation value
	 * @param newOrientation (degrees)
	 */
	abstract void setOrientation(double newOrientation);
	
	/**
	 * Returns the orientation value of the entity
	 * @return orientation (degrees)
	 */
	abstract DoubleProperty retrieveOrientation();
	
	/**
	 * Sets the shape to the image specified by the file path
	 * 
	 * @param filePath Image filepath. Preferably .PNG
	 * @param style The bounding box style you want. PERFECT | EXTERNAL | SQUARE 
	 */
	abstract void assignShapeFromFilePath(String filePath, BoundingBox style );

	/**
	 * 
	 * @return the string representation of the image's file path 
	 */
	abstract String retrieveImageFile();

	/**
	 * Defaults to PERFECT_BOUNDS. To use non-perfect bounds, use setShapeFromImage(...)
	 * 
	 * @param image the JavaFX Image you want to create the shape from
	 */
	abstract void assignShapeFromImage(Image image);
	
	/**
	 * 
	 * @return Display of the spacial (imageview or shape)
	 */
	abstract Image retrieveDisplay();

	/**
	 * Returns the shape of the entity
	 * @return JavaFX Shape for collisions
	 */
	abstract Shape retrieveShape();
	
	/**
	 * Set the absolute speed of the object
	 * @param newSpeed the new, unmodified, speed of the object
	 */
	abstract void setSpeed(double newSpeed);
	
	/**
	 * Sets the speed modifier of the object. 
	 * @param newModifier The factor the speed will be multiplied by when received
	 */
	abstract void assignSpeedModifier(double newModifier);
	
	/**
	 * 
	 * @return speed of the entity
	 */
	abstract double getVelocity();
	
	/**
	 * Focused determines things like SplitScreen, what entities the camera follows 
	 * if any, etc. 
	 * @param focused Whether this entity should be focused or not
	 */
	abstract void assignFocused(boolean focused);
	
	/**
	 * Focused determines things like SplitScreen, what entities the camera follows 
	 * if any, etc.  
	 * @return focus status
	 */
	abstract boolean isFocused();
	
	

}
