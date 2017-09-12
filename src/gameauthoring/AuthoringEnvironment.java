package gameauthoring;

import java.awt.Dimension;

import configuration.MenuLanguage;
import application.MainController;
import gameauthoring.components.level.LevelView;
import gameauthoring.components.objects.ObjectsList;
import gameauthoring.components.properties.AbilitiesViewer;
import gameauthoring.components.properties.AttributesViewer;
import gameauthoring.components.properties.Viewer;
import gameauthoring.presets.PresetManager;
import gamedata.data.GameData;
import gameengine.GameEngine;
import gameengine.GameWorld;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.ExternalEngineInterface;
import gameplayer.display.DisplayInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * @author Blake Becerra
 * @author Michael Seaberg 
 * @author Larissa Cox
 * @author Sam Furlong
 * @author Walker Eacho
 * Controller for the authoring environment
 */
public class AuthoringEnvironment implements DisplayInterface, EnvironmentInterface, AuthoringToPlayerInterface{
	private Pane myWorkspace;
	private Scene myScene;
	private Dimension AUTHORING_ENVIRONMENT_DEFAULT_SIZE;
	private final Dimension AUTHORING_ENVIRONMENT_MIN_SIZE = new Dimension(800,500);
	private final int COL_DIMENSION_ONE = 34;
	private final int COL_DIMENSION_TWO = 16;
	private final int ROW_DIMENSION_ONE = 55;
	private final int ROW_DIMENSION_TWO = 100-ROW_DIMENSION_ONE;
	private AttributesViewer myPropertiesViewer;
	private ObjectsList myObjectsList;
	private AbilitiesViewer myCommandsList;
	private LevelView myLevelView;
	private ExternalEngineInterface myEngine;
	private EventHandler<ActionEvent> myBackEvent;
	private EventHandler<ActionEvent> myPlayerEvent;
	private GameData myGameData;
	private EntityInterface mySelectedEntity;
	
	public AuthoringEnvironment(double width, double height, EventHandler<ActionEvent> back, EventHandler<ActionEvent> player) {
		AUTHORING_ENVIRONMENT_DEFAULT_SIZE = new Dimension((int) width, (int) height);
		myWorkspace = new VBox();
		myScene = new Scene(myWorkspace,AUTHORING_ENVIRONMENT_DEFAULT_SIZE.getWidth(),AUTHORING_ENVIRONMENT_DEFAULT_SIZE.getHeight());
		myBackEvent = back;
		myPlayerEvent = player;
		myEngine = new GameEngine();
	}
	@Override
	public Scene getScene() {
		return myScene;
	}
	@Override
	public void initialize(GameData data) {
		myGameData = data;
		//Create GridPane to layout sections of the workspace and add Row and Column constraints
		GridPane layoutArea = new GridPane();
		layoutArea.setMinSize(AUTHORING_ENVIRONMENT_MIN_SIZE.getWidth(),AUTHORING_ENVIRONMENT_MIN_SIZE.getHeight());
		layoutArea.setPrefSize(AUTHORING_ENVIRONMENT_DEFAULT_SIZE.getWidth(),AUTHORING_ENVIRONMENT_DEFAULT_SIZE.getHeight());
		layoutArea.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        layoutArea.getColumnConstraints().addAll(getNewColumn(COL_DIMENSION_ONE),getNewColumn(COL_DIMENSION_ONE),
        		getNewColumn(COL_DIMENSION_TWO),getNewColumn(COL_DIMENSION_TWO));
        layoutArea.getRowConstraints().addAll(getNewRow(ROW_DIMENSION_ONE),getNewRow(ROW_DIMENSION_TWO));       
        //Instantiate each section of the workspace and add these sections to the gridPane
        layoutArea.add(createPropertiesViewer().getBox(), 3,1,1,1);
        layoutArea.add(createCommandsList().getBox(), 2,1,1,1);
        layoutArea.add(createLevelView().getBox(), 0,0,2,2);
		layoutArea.add(createObjectsList().getBox(), 2,0,2,1);
		//Add Everything to the workspace
		MenuBar topMenuBar = createMenuBar();
		setEntityListener();
		setWorldListener();
		myWorkspace.getChildren().addAll(topMenuBar,layoutArea);
		myScene.getStylesheets().add("defaultstyle.css");
	}
	/**
	 * Creates the menuBar for the authoring environment. Contains many menus that were not implemented.
	 * @return
	 */
	private MenuBar createMenuBar() {
		MenuBar myMenuBar = new MenuBar();
		MenuItem newGame = new MenuItem(MenuLanguage.getInstance().getValue("Game"));
		newGame.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
			}
		});
		MenuItem newLevel = new MenuItem(MenuLanguage.getInstance().getValue("Level"));
		newLevel.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				myLevelView.addNewLevel(myLevelView.getLeveName());
			}
		});
		MenuItem mainMenu = new MenuItem(MenuLanguage.getInstance().getValue("MainMenu"));
		mainMenu.setOnAction(myBackEvent);
		MenuItem goToPlayer = new MenuItem(MenuLanguage.getInstance().getValue("GoToPlayer"));
		goToPlayer.setOnAction(myPlayerEvent);
		MenuItem openGame =  new MenuItem(MenuLanguage.getInstance().getValue("Open"));
		MenuItem propertiesButton = new MenuItem(MenuLanguage.getInstance().getValue("Properties"));
		Menu newMenu = new Menu(MenuLanguage.getInstance().getValue("New..."));
		Menu fileMenu = new Menu(MenuLanguage.getInstance().getValue("File"));
		fileMenu.setId("topMenuMenu");
		MenuItem saveMenu = new MenuItem(MenuLanguage.getInstance().getValue("Save"));
		saveMenu.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				myEngine.getWorld();
				myGameData.saveGameAuthoring();
			}
		});
		newMenu.getItems().addAll(newGame,newLevel);
		fileMenu.getItems().addAll(mainMenu, goToPlayer, newMenu,openGame,saveMenu, new SeparatorMenuItem(),propertiesButton);
		Menu editMenu = new Menu("Edit");
		editMenu.setId("topMenuMenu");
		MenuItem aboutButton = new MenuItem(MenuLanguage.getInstance().getValue("About"));
		MenuItem helpFile = new MenuItem(MenuLanguage.getInstance().getValue("Help"));
		MenuItem presets = new MenuItem(MenuLanguage.getInstance().getValue("Presets"));
		presets.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				PresetManager presetManager = new PresetManager(myEngine.getWorld());			
				presetManager.initializeView();
			}
		});
		Menu helpMenu = new Menu("Help");
		helpMenu.setId("topMenuMenu");
		helpMenu.getItems().addAll(aboutButton,presets, helpFile);
		myMenuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
		myMenuBar.setId("topMenuLabel");
		return myMenuBar;
	}
	/**
	 * Helper method to create a new column constraint to use in a gridpane
	 * @param percentage
	 * @return
	 */
	private ColumnConstraints getNewColumn(int percentage){
		ColumnConstraints newConstraint = new ColumnConstraints(percentage);
		newConstraint.setPercentWidth(percentage);
		newConstraint.setHgrow(Priority.NEVER);
		return newConstraint;
	}
	/**
	 * Helper method to create a new row constraint to use in a gridpane
	 * @param percentage
	 * @return
	 */
	private RowConstraints getNewRow(int percentage){
		RowConstraints newConstraint = new RowConstraints(percentage);
		newConstraint.setPercentHeight(percentage);
		newConstraint.setVgrow(Priority.ALWAYS);
		return newConstraint;
	}
	private Viewer createPropertiesViewer() {
		myPropertiesViewer = new AttributesViewer();
		return myPropertiesViewer;
	}
	private ObjectsList createObjectsList() {
		myObjectsList = new ObjectsList(myEngine);
		return myObjectsList;
	}
	private AbilitiesViewer createCommandsList() {
		myCommandsList = new AbilitiesViewer();
		return myCommandsList;
	}
	private LevelView createLevelView() {
		Double levelViewWidth = AUTHORING_ENVIRONMENT_DEFAULT_SIZE.getWidth()*(COL_DIMENSION_ONE*2);
		Double levelViewHeight = AUTHORING_ENVIRONMENT_DEFAULT_SIZE.getHeight();
		myLevelView = new LevelView("", myEngine, this, levelViewWidth,levelViewHeight, myGameData);
		myScene.setOnKeyPressed(k->{
			myEngine.setOnKeyPressed(k.getCode());
		});
		myScene.setOnKeyReleased(k->{
			myEngine.setOnKeyReleased(k.getCode());
		});
		return myLevelView;
	}
	private void setEntityListener(){
		myLevelView.getEntityBoolean().addListener(e->{
			mySelectedEntity = myLevelView.getSelectedEntity();
			myPropertiesViewer.updateInfo(mySelectedEntity);
			myCommandsList.updateInfo(mySelectedEntity);
			myObjectsList.updateInfo(mySelectedEntity);
		});
	}
	private void setWorldListener(){
		myLevelView.getWorldSelectionBoolean().addListener(e->{
			myObjectsList.setWorld(myEngine.getWorld());
			myScene.setOnKeyPressed(k->{
				myEngine.setOnKeyPressed(k.getCode());
			});
			myScene.setOnKeyReleased(k->{
				myEngine.setOnKeyReleased(k.getCode());
			});
		});
	}
	@Override
	public void step(double elapsedTime) {
		myEngine.update(elapsedTime);
	}
	@Override
	public void setEngine(ExternalEngineInterface currentChoice) {
		myEngine = currentChoice;
	}
	@Override
	public void togglePause() {
	}
	@Override
	public void setScene(Scene newScene) {
	}
	@Override
	public Stage getStage() {
		return null;
	}
	@Override
	public Scene init(EventHandler<?> backEvent, MainController MainController, Stage s) {
		return myScene;
	}
	@Override
	public GameWorld getWorld() {
		return null;
	}
	@Override
	public void setWorld(GameWorld newWorld) {
	}
}