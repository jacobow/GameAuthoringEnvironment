package gameauthoring.components.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import configuration.MenuLanguage;
import gameauthoring.AuthoringPane;
import gameauthoring.components.entitycreation.ParentEntityCreator;
import gameengine.GameWorld;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntitySetSubject;
import gameengine.interfaces.ExternalEngineInterface;
import gameengine.utilities.LiveObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

/**
 * ObjectsList class stores all the created entities available for placement in levels. The main feature of this.getBox() class is the
 * EntityCreator, which allows any entity with any set of attributes to be added to a game.
 * @author michaelseaberg
 *
 */
public class ObjectsList extends AuthoringPane implements LiveObserver{
	private final static String TITLE = "Entities";
	private static final Integer TOTAL_ENTITY_COLUMN = 4;
	private ExternalEngineInterface myEngine;
	private GameWorld myWorld;
	private transient GridPane myContentPane;
	private transient EntityThumbnail mySelectedThumbnail;
	private Set<GameWorld> myWorlds;
	private Map<EntityInterface,EntityThumbnail> myEntityThumbnailMap;
	private EntityInterface mySelectedEntity;

	public ObjectsList(ExternalEngineInterface currentEngine){
		super(TITLE);
		myEngine = currentEngine;
		myContentPane = new GridPane();
		myWorlds = new HashSet<GameWorld>();
		myEntityThumbnailMap = new HashMap<EntityInterface,EntityThumbnail>() ;
		this.getBox().getChildren().add(myContentPane);
		setWorld(myEngine.getWorld());
		initialize();
	}
	
	/**
	 * Initializes Menu's and JavaFX parameters
	 */
	private void initialize() {
		myContentPane.setHgap(10);
		myContentPane.setVgap(10);
		myContentPane.setPadding(new Insets(10, 10, 10, 10));
		Menu createMenu = new Menu(MenuLanguage.getInstance().getValue("CreateNew"));
		MenuItem entityMenuItem = new MenuItem(MenuLanguage.getInstance().getValue("CreateNewEntity"));
		createMenu.getItems().add(entityMenuItem);
		entityMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				createNewEntity();
			}
		});
		Menu editMenu = new Menu(MenuLanguage.getInstance().getValue("Edit"));
		MenuItem editItem = new MenuItem(MenuLanguage.getInstance().getValue("EditSelected"));
		editItem.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				updateCurrentEntity();
			}
			
		});
		editMenu.getItems().addAll(editItem);
		this.addMenuToBar(createMenu);
		this.addMenuToBar(editMenu);
	}

	/**
	 * SetWorld method used for telling the objects list what the current world is-entities in the list are
	 * updated as such using the reloadObjectsList method.
	 * @param world
	 */
	public void setWorld(EntitySetSubject world){
		myWorld = (GameWorld) world;
		reloadObjectsList();
		if(!myWorlds.contains(world)){
			myWorlds.add((GameWorld) world);
			world.getEntities().registerObserver(this);
			amend();
		}
	}

	/**
	 * Method used to reload the objectsList to show entities of different worlds.
	 */
	private void reloadObjectsList() {
		myContentPane.getChildren().clear();
		myEntityThumbnailMap.clear();
		myWorld.getEntities().forEach(entity -> addEntityToList(entity));
	}

	/**
	 * Adds entity (thumbnail) to the objects list view given an input entity
	 * @param elementAdded
	 */
	protected void addEntityToList(EntityInterface elementAdded) {
		EntityThumbnail newThumb = new EntityThumbnail(elementAdded);
		if(myContentPane.getChildren().isEmpty())
			mySelectedThumbnail = newThumb;
		
		myEntityThumbnailMap.put(elementAdded, newThumb);
		addThumbnailToPane(newThumb.getMyThumbnail());
	}
	
	/**
	 * Helper method for adding a thumbnail to the objects pane
	 * @param myThumbnail
	 */
	private void addThumbnailToPane(Node myThumbnail) {
		if(myContentPane.getChildren().isEmpty()){
			myContentPane.add(myThumbnail, 0,0);
			return;
		}
		Node lastNode = myContentPane.getChildren().get(myContentPane.getChildren().size()-1);
		if(GridPane.getColumnIndex(lastNode) == TOTAL_ENTITY_COLUMN){
			myContentPane.add(myThumbnail, 0, GridPane.getRowIndex(lastNode)+1);
		}
		else{
			myContentPane.add(myThumbnail, GridPane.getColumnIndex(lastNode)+1, GridPane.getRowIndex(lastNode));
		}
	}

	/**
	 * Method used to call the entity creator from the edit menu
	 */
	private void createNewEntity() {
		ParentEntityCreator createEntity = new ParentEntityCreator(myWorld, null);
		createEntity.initialize();
	}

	@Override
	public void amend() {
		reloadObjectsList();
	}
	
	/**
	 * Method used to notify the objects list that the selected entity has changed.
	 * Currently only performs css change, but was intented for further use.
	 * @param entity
	 */
	public void updateInfo(EntityInterface entity) {
		mySelectedEntity = entity;
		mySelectedThumbnail.getMyThumbnail().setId("entityThumb");
		mySelectedThumbnail = myEntityThumbnailMap.get(entity);
		mySelectedThumbnail.getMyThumbnail().setId("selectedThumb");
	}
	
	/**
	 * Calls the entity creator for an existing entity in order to modify properties that already exist.
	 */
	private void updateCurrentEntity(){
		ParentEntityCreator createEntity = new ParentEntityCreator(myWorld, mySelectedEntity);
		createEntity.initialize();
	}
	
	
}