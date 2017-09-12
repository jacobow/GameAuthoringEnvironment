package gameauthoring.components.level;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import configuration.MenuLanguage;
import gameauthoring.AuthoringPane;
import gamedata.data.GameData;
import gamedata.fileIO.ChooseFileName;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.ExternalEngineInterface;
import gameplayer.display.DisplayInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * Purpose: This class is the base class for displaying the game field. All entities for each level are shown here
 * and may be moved around to suit a users needs
 * Dependencies: Authoring Pane, to provide the general JAVAFX Format
 * Example Use: used to visually create a game level
 * @author michaelseaberg, Blake Becerra
 *
 */
public class LevelView extends AuthoringPane {
	private static final String TITLE = "Level Editor";
	private static Dimension CONTENT_DIMENSIONS;
	private final double TAB_HEIGHT = 35.0;
	private final int INSET_SIZE = 9;
	private TabPane myTabPane;
	private Tab myNewLevelButton;
	private ExternalEngineInterface myEngine;
	private DisplayInterface myDisplay;
	private GameData myGameData;
	private Map<String, AuthoringPlayScreen> myPlayScreenMap;
	private SimpleBooleanProperty myWorldBoolean;
	private LevelToolBar myLevelButtons;

	public LevelView(String title, ExternalEngineInterface engine, DisplayInterface display, double width, double height, GameData data) {
		super(TITLE);
		myGameData = data;
		myEngine = engine;
		myDisplay = display;
		myPlayScreenMap = new HashMap<String, AuthoringPlayScreen>();
		myWorldBoolean = new SimpleBooleanProperty();
		CONTENT_DIMENSIONS = new Dimension((int)width,(int)height);
		initialize();
	}
	private void initialize() {
		myTabPane = new TabPane();
		myTabPane.setPadding(new Insets(INSET_SIZE));
		if(!myGameData.getLevelMap().isEmpty()){
			load();
		}
		createNewLevelButton();
		myTabPane.setTabMaxHeight(TAB_HEIGHT);
		myTabPane.getSelectionModel().selectedItemProperty().addListener((x,y,z)-> {
			if(z != null && myGameData.getLevelMap().get(z.getText()) != null){
				myEngine.loadGameWorld(myGameData.getLevelMap().get(myTabPane.getSelectionModel().getSelectedItem().getText()).getGameWorld());
				myWorldBoolean.set(!myWorldBoolean.get());
			}
		});
		myTabPane.getTabs().addAll(myNewLevelButton);
		myTabPane.getStyleClass().add("tab-pane");
		myLevelButtons = new LevelToolBar("", myEngine, myPlayScreenMap, myTabPane, myGameData);
		getBox().getChildren().add(myLevelButtons.getMyToolBar());
		getBox().getChildren().add(myTabPane);
	}

	private void load(){
		for(String level : myGameData.getLevelMap().keySet()){
			loadLevel(level);
			myPlayScreenMap.get(myTabPane.getSelectionModel().getSelectedItem().getText()).setBackground(myGameData.getLevelMap().get(level).getBackground());
		}
		createNewLevelButton();
	}
	private void loadLevel(String name){
		Tab loadTab = new Tab(name);
		loadTab.setContent(makePane(name));
		loadTab.setOnClosed(e->removeWorld(name));
		myTabPane.getTabs().add(loadTab);
	}
	private void createNewLevelButton(){
		myNewLevelButton = new Tab(MenuLanguage.getInstance().getValue("NewLevel"));
		myNewLevelButton.setContent(new Pane());
		myNewLevelButton.getStyleClass().add("tab");
		myNewLevelButton.setOnSelectionChanged(e -> addNewLevel(getLeveName()));
	}
	/**
	 * Opens dialog box to get the name for a level
	 * @return String
	 */
	public String getLeveName(){
		ChooseFileName cfn = new ChooseFileName(
				MenuLanguage.getInstance().getValue("LevelName"),
				MenuLanguage.getInstance().getValue("LevelName"),
				MenuLanguage.getInstance().getValue("SelectNameOfLevel"));
		String name =  cfn.getFileName();
		myGameData.addLevel(name);
		return name;
	}
	/**
	 * Creates the new pane, data, and world for a new level
	 * @param name
	 */
	public void addNewLevel(String name) {
//		myGameData.addLevel(name);
		Tab lastTab = myTabPane.getTabs().get(myTabPane.getTabs().size()-1);
		lastTab.setText(name);
		myTabPane.getSelectionModel().clearSelection();
		myTabPane.getSelectionModel().select(lastTab);
		lastTab.setContent(makePane(name));
		lastTab.setOnClosed(e->removeWorld(name));
		lastTab.setOnSelectionChanged(null);
		lastTab.setId("regularTab");
		createNewLevelButton();
		myTabPane.getTabs().add(myNewLevelButton);
	}
	private void removeWorld(String name){
		myGameData.removeLevel(name);
	}
	private Node makePane(String name) {
		myEngine.loadGameWorld(myGameData.getLevelMap().get(name).getGameWorld());
		AuthoringPlayScreen currentPane = new AuthoringPlayScreen(myEngine, myDisplay, CONTENT_DIMENSIONS.getWidth(), CONTENT_DIMENSIONS.getHeight(), myGameData.getLevelMap().get(name).getGameWorld());
		myPlayScreenMap.put(name, currentPane);
		currentPane.setScrollPolicyAlways();
		return currentPane.getNode();
	}
	/**
	 * Gets the selected entity on the screen
	 * @return EntityInterface
	 */
	public EntityInterface getSelectedEntity(){
		return myPlayScreenMap.get(myTabPane.getSelectionModel().getSelectedItem().getText()).getSelectedEntity();
	}
	/**
	 * Gets the boolean to listen for the selected entity changing
	 * @return BooleanProperty
	 */
	public BooleanProperty getEntityBoolean(){
		return myPlayScreenMap.get(myTabPane.getSelectionModel().getSelectedItem().getText()).getEntityBoolean();
	}
	/**
	 * Gets the boolean for the the selected world changing
	 * @return BooleanProperty
	 */
	public BooleanProperty getWorldSelectionBoolean(){
		return myWorldBoolean;
	}
}