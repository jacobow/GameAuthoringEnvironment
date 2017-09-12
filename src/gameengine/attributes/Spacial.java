package gameengine.attributes;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.utilities.BoundingBox;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

/**
 * 
 * @author walker
 *
 */
public class Spacial implements SpacialInterface{
	private DoubleProperty myX;
	private DoubleProperty myY;
	private DoubleProperty myOrientation;
	private double mySpeed;
	private double mySpeedModifier;
	private String myImageFilePath;
	private transient Image myImage;
	private transient Shape myShape;
	private boolean amIFocused =false;

	public Spacial(){
		init();
		setX(0.0);
		assignY(0.0);
		setOrientation(0.0);
		myShape = null;
		myImageFilePath = "";
		myImage = null;
		mySpeed = 0.0;
	}

	public Spacial(SpacialInterface copy){
		init();
		myX.set(copy.retrieveX().get());
		myY.set(copy.retrieveY().get());
		myOrientation.set(copy.retrieveOrientation().get());
		myShape = Shape.union(copy.retrieveShape(), copy.retrieveShape());
		myImageFilePath = copy.retrieveImageFile();
		myImage = new Image(this.getClass().getClassLoader().getResourceAsStream(myImageFilePath));
//		myImageView = new ImageView(copy.getDisplay());
		mySpeed = copy.getVelocity();
	}

	public Spacial(double startingX, double startingY, double startingOrientation, double initialVelocity, boolean focused, String filePath) {
		init();
		amIFocused = focused;
		myImageFilePath = filePath;
		myImage = new Image(this.getClass().getClassLoader().getResourceAsStream(myImageFilePath));
		myShape = BoundingBox.PERFECT_BOUNDS.getBounds(myImage);
		setInitialValues(startingX, startingY, startingOrientation,initialVelocity);
	}

	public Spacial(double startingX, double startingY, double startingOrientation, double initialVelocity, String filePath) {
		init();
		myImageFilePath=filePath;
		System.out.println("My Spacial Image FilePath is" + myImageFilePath);
		myImage = new Image(this.getClass().getClassLoader().getResourceAsStream(myImageFilePath));
		myShape = BoundingBox.PERFECT_BOUNDS.getBounds(myImage);
		setInitialValues(startingX, startingY, startingOrientation,initialVelocity);
	}

	private void init(){
		myX = new SimpleDoubleProperty();
		myY = new SimpleDoubleProperty();
		myOrientation = new SimpleDoubleProperty();
		mySpeedModifier=1.0;
	}

	private void setInitialValues(double startingX, double startingY, double startingOrientation,double initialVelocity) {
		setX(startingX);
		assignY(startingY);
		setOrientation(startingOrientation);
		mySpeed = initialVelocity;
	}

	private void relocateAll(){
		myShape.relocate(myX.get(),myY.get());
	}

	//////////////////
	// API          //
	//////////////////

	public void setX(double newX) {
		try{
			myX.set(newX);
		}catch(NullPointerException serialnpe){
			myX = new SimpleDoubleProperty();
			myX.set(newX);
		}
		relocateAll();
	}
	
	
	
	public DoubleProperty retrieveX() {
		return myX;
	}
	
	

	public void assignY(double newY) {
		try{
			myY.set(newY);
		}catch(NullPointerException serialnpe){
			myY = new SimpleDoubleProperty();
			myY.set(newY);
		}
		relocateAll();
	}

	public DoubleProperty retrieveY() {
		return myY;
	}

	public void setOrientation(double newOrientation) {
		try{
			myOrientation.set(newOrientation);
		}catch(NullPointerException serialnpe){
			myOrientation = new SimpleDoubleProperty();
			myOrientation.set(newOrientation);
		}
	}

	public DoubleProperty retrieveOrientation() {
		return myOrientation;
	}

	public void assignShapeFromFilePath(String filePath, BoundingBox style){
		myImageFilePath = filePath;
		myImage = new Image(this.getClass().getClassLoader().getResourceAsStream(myImageFilePath));
		myShape = style.getBounds(myImage);
	}

	public String retrieveImageFile(){
		return myImageFilePath;
	}

	public void assignShapeFromImage(Image image){
		myImage = image;
		myShape = BoundingBox.PERFECT_BOUNDS.getBounds(myImage);
	}

	public void initShape() {
		assignShapeFromFilePath(myImageFilePath,BoundingBox.PERFECT_BOUNDS);
		myShape.relocate(retrieveX().get(), retrieveY().get());
	}

	public Image retrieveDisplay(){
		return myImage;
	}

	public Shape retrieveShape() {
		return myShape;
	}
	public void setSpeed(double newSpeed){
		mySpeed = newSpeed;
	}

	public void assignSpeedModifier(double newModifier) {
		mySpeedModifier=newModifier;
	}

	public double getVelocity() {
		return mySpeedModifier*mySpeed;
	}

	public void assignFocused(boolean focused){
		amIFocused = focused;
	}

	public boolean isFocused(){
		return amIFocused;
	}





}