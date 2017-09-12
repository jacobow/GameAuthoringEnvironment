package gameplayer.display.gameplay.playscreen;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gameengine.attributes.Spacial;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.ExternalEngineInterface;
import gameengine.utilities.LiveObserver;
import gameplayer.display.DisplayInterface;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
/**
 *
 * @author SamFurlong
 *
 */
public class PlayScreen implements LiveObserver{
	private Pane gamePane;
	private double width;
	private double height;
	private ExternalEngineInterface currentChoice;
	private EntityInterface user;
	private StackPane myStacker;
	private Map<EntityInterface, InfoToUpdateOnEntityMoved> entityInfoMap;
	private ScrollPane myScroller;
	private Set<EntityInterface> oldEntities;
	private Set<EntityInterface> newEntities;
	private final static String CLEAR_PANE = "clearPane";
	private static final String BACKGROUND_CSS = "-fx-background-image: url(\"";
	private static final String SLASH = "\")";
	private static double INCREASE_FACTOR =1.25;
	public PlayScreen(ExternalEngineInterface currentChoice, DisplayInterface display,  double width, double height){
		oldEntities = new HashSet<EntityInterface>();
		newEntities = new HashSet<EntityInterface>();
		this.currentChoice = currentChoice;
		this.width = width;
		entityInfoMap = new HashMap<EntityInterface, InfoToUpdateOnEntityMoved>();
		this.height = height;
		gamePane = new Pane();
		myStacker = new StackPane();
		gamePane.setId(CLEAR_PANE);
		gamePane.setMinWidth(width);
		gamePane.setMinHeight(height);
		myStacker.getChildren().add(gamePane);
		pseudoAmend();
		createScroller();
		currentChoice.getWorld().getEntities().registerObserver(this);
	}
	private void pseudoAmend(){
		currentChoice.getWorld().getEntities().forEach(entity->{
			if(oldEntities.contains(entity)) oldEntities.remove(entity);
			else newEntities.add(entity);
		});

		oldEntities.forEach(toRemove -> removeEntityFromDisplay(toRemove));
		newEntities.forEach(toAdd -> addEntityToDisplay(toAdd));
		oldEntities = new HashSet<EntityInterface>(currentChoice.getWorld().getEntities());
		newEntities.clear();
	}

	private void addEntityToDisplay(EntityInterface entity) {
		InfoToUpdateOnEntityMoved  myMoveInfo = new InfoToUpdateOnEntityMoved(entity);
		entityInfoMap.put(entity, myMoveInfo);
		gamePane.getChildren().add(myMoveInfo.getImageView());
	}
	private double findWidth(){
		double width = 0.0;
		for(EntityInterface entity: currentChoice.getWorld().getEntities()){
			Shape myShape = entity.getAttribute(Spacial.class).retrieveShape();
			if(myShape.getBoundsInLocal().getWidth()>width){
				width = myShape.getBoundsInLocal().getWidth();
			}
		}
		return width;

	}
	private double findHeight(){
		double height = 0.0;
		for(EntityInterface entity: currentChoice.getWorld().getEntities()){
			Shape myShape = entity.getAttribute(Spacial.class).retrieveShape();
			if(myShape.getBoundsInLocal().getHeight()>height){
				height = myShape.getBoundsInLocal().getHeight();
			}
		}
		return height;

	}
	private void removeEntityFromDisplay(EntityInterface entity){
		if(!entityInfoMap.containsKey(entity)){
			return;
		}
		gamePane.getChildren().remove(entityInfoMap.get(entity).getImageView());
		ImageView trash =entityInfoMap.get(entity).getImageView();
		entityInfoMap.remove(entity);
		entity  = null;
		trash = null;
	}

	private void createScroller(){
		myScroller = new ScrollPane(myStacker);
		myScroller.setId(CLEAR_PANE);
		myScroller.setOnMouseReleased(i->mouseRelease(i,user));
		myScroller.setPrefSize(width, height);
		myScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		myScroller.setVbarPolicy(ScrollBarPolicy.NEVER);
	}
	/**
	 * feed the engine inpu
	 * @param i
	 * @param user
	 */
	public void mouseRelease(MouseEvent i, EntityInterface user){
		currentChoice.setOnMouseAction(i,user);
	}
	/**
	 *
	 * @param background
	 */
	public void setBackground(String background){
		gamePane.setStyle(BACKGROUND_CSS+background +SLASH);
	}
	/**
	 * returns the engine currently in use
	 * @return
	 */
	protected ExternalEngineInterface getEngine(){
		return this.currentChoice;
	}
	/**
	 * returns a map of entities to all of their elements on the screen that will be updated at each time step
	 * @return
	 */
	protected Map<EntityInterface, InfoToUpdateOnEntityMoved> getEntityInfoMap(){
		return entityInfoMap;
	}
	/**
	 * returns the pane that displays the entities
	 * @return
	 */
	public Pane getGamePane(){
		return gamePane;
	}
	/**
	 * returns the stackpane used in payscreen
	 * @return
	 */
	protected Pane getStacker(){
		return myStacker;
	}
	/*
	 * returns the scroll pane used in playscreen
	 */
	protected ScrollPane getScroller() {

		return myScroller;
	}
	/**
	 * returns the width of the screen
	 * @return
	 */
	protected Double getWidth(){
		return this.width;
	}
	/**
	 * returns the height of the screen
	 * @return
	 */
	protected Double getHeight(){
		return this.height;
	}
	/**
	 * returns the node to be dislayed
	 * @return
	 */
	public Node getNode() {
		return myScroller;
	}
	/*
	 * sets the scroll policy to alawys
	 */
	public void setScrollPolicyAlways(){
		myScroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		myScroller.setHbarPolicy(ScrollBarPolicy.ALWAYS);
	}
	/**
	 * increases gamepanes height
	 */
	public void increasePaneHeight(){
		gamePane.setMinHeight(gamePane.getMinHeight()*INCREASE_FACTOR);
	}
/**
 * increased the panes width
 */
	public void increasePaneWidth(){
		gamePane.setMinWidth(gamePane.getMinWidth()*INCREASE_FACTOR);
	}
/**
 * sets heigh of gamepane
 * @param value
 */
	public void setMinHeight(Double value){
		gamePane.setMinHeight(value);
	}
/**
 * sets width of gamepane
 * @param value
 */
	public void setMinWidth(Double value){
		gamePane.setMinWidth(value);
	}
	@Override
	public void amend() {
		currentChoice.getWorld().getEntities().forEach(entity->{
			if(oldEntities.contains(entity)) oldEntities.remove(entity);
			else newEntities.add(entity);
		});
		oldEntities.forEach(toRemove -> removeEntityFromDisplay(toRemove));
		newEntities.forEach(toAdd -> addEntityToDisplay(toAdd));

		oldEntities = new HashSet<EntityInterface>(currentChoice.getWorld().getEntities());
		newEntities.clear();
	}
}



