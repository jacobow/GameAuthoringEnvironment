package gameplayer.display.gameplay.playscreen;


import gameengine.attributes.Spacial;
import gameengine.attributes.Zone;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.EntityInterface;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
/**
 *
 * @author SamFurlong
 *
 */
public class  InfoToUpdateOnEntityMoved{
	private PlayerStats myStats;
	private transient ImageView myView;
	private transient ScrollPane myScroller;
	private Double width;
	private Double height;
	private EntityInterface myEntity;
	private boolean hasCamera;

	public InfoToUpdateOnEntityMoved(EntityInterface myEntity){
		width = 0.0;
		height = 0.0;
		myStats = new PlayerStats();
		hasCamera = false;
		this.setMyEntity(myEntity);
		initializeEntityVis();
	}

	private void initializeEntityVis(){
		if(myEntity.containsAttribute(Spacial.class)){
			this.width = width;
			this.height = height;
			SpacialInterface mySpacial = myEntity.getAttribute(Spacial.class);
			myView = new ImageView(mySpacial.retrieveDisplay());
			if(myEntity.containsAttribute(Zone.class)){
				DropShadow zoneShadow = new DropShadow();
				zoneShadow.setColor(Color.BLUE);
				zoneShadow.setRadius(myEntity.getAttribute(Zone.class).getRadius());
				myView.setEffect(zoneShadow);
			}
			myView.relocate(mySpacial.retrieveX().get(),mySpacial.retrieveY().get());
			myView.setRotate(mySpacial.retrieveOrientation().get());
			createListeners(mySpacial);
		}
	}
	private void createListeners(SpacialInterface mySpacial){
		mySpacial.retrieveX().addListener((x,y,z)->{
			update(mySpacial.retrieveX().get(), mySpacial.retrieveY().get(), mySpacial.retrieveOrientation().get());
		});
		mySpacial.retrieveY().addListener((x,y,z)->{
			update(mySpacial.retrieveX().get(), mySpacial.retrieveY().get(), mySpacial.retrieveOrientation().get());
		});
		mySpacial.retrieveOrientation().addListener((x,y,z)->{
			update(mySpacial.retrieveX().get(), mySpacial.retrieveY().get(), mySpacial.retrieveOrientation().get());

		});

	}

	public void setStats(PlayerStats myStats){
		this.myStats = myStats;
		myStats.getNode().setTranslateX(myEntity.getAttribute(Spacial.class).retrieveX().get());
		myStats.getNode().setTranslateX(myEntity.getAttribute(Spacial.class).retrieveY().get());

	}
	public void setImageView(ImageView myView){
		this.myView = myView;
	}
	public ImageView getImageView(){
		return myView;
	}
	public void setScroller(ScrollPane myScroller, Double splitWidth, Double splitHeight){
		this.myScroller = myScroller;
		this.width = splitWidth;
		this.height = splitHeight;
		hasCamera = true;
	}
	private void update(double x, double y, double rotate){
		myStats.getNode().setTranslateX(x);
		myStats.getNode().setTranslateY(y);
		myView.relocate(x, y);
		myView.setRotate(rotate);
		if(hasCamera){
		double xposCof = x/width;
		myScroller.setHvalue(xposCof);;
		System.out.println(xposCof);
		double yposCof = y/height;
		myScroller.setVvalue(yposCof);
		}
	}
	/**
	 *
	 * @return
	 * returns the enity associated with this object
	 */
	public EntityInterface getMyEntity() {
		return myEntity;
	}
	/**
	 *
	 * @param myEntity
	 * sets the entity associated with this object
	 */
	public void setMyEntity(EntityInterface myEntity) {
		this.myEntity = myEntity;
	}
	/**
	 *
	 * @return
	 * returns the stats abject
	 */
	public PlayerStats getStats(){
		return this.myStats;
	}
	/**
	 *
	 * @return
	 * returns the node of the stats object to be displayed
	 */
	public  Node getStatsNode(){
		return myStats.getNode();
	}
}
