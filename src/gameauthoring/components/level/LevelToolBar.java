package gameauthoring.components.level;

import java.io.File;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import configuration.MenuLanguage;
import gameauthoring.AuthoringFactory;
import gameauthoring.AuthoringPane;
import gameauthoring.components.selectors.AuthoringFileChooser;
import gameauthoring.components.selectors.systemsselectors.SystemsSelector;
import gameauthoring.components.selectors.systemsselectors.WinConditionSelector;
import gameauthoring.components.selectors.systemsselectors.ZoneHandlerSelector;
import gamedata.data.GameData;
import gameengine.interfaces.ExternalEngineInterface;

/**
 * Creates the toolbar for the level view class
 * @author Blake Becerra, Michael Seaberg
 */
public class LevelToolBar extends AuthoringPane{
	private static final String BUTTON_ICON_FILEPATH = "resources/images/IconImages/";
	private ExternalEngineInterface myEngine;
	private Map<String, AuthoringPlayScreen> myPlayScreenMap;
	private TabPane myTabPane;
	private GameData myGameData;

	public LevelToolBar(String title, ExternalEngineInterface engine, Map<String, AuthoringPlayScreen> screens, TabPane tabPane, GameData data) {
		super(title);
		myEngine = engine;
		myPlayScreenMap = screens;
		myTabPane = tabPane;
		myGameData = data;
		makeButtons();
	}
	
	/**
	 * Uses Authoring Factory to create buttons to set game parameters
	 */
	private void makeButtons(){
		addToToolbarHelper("SetBackgroundImage",e->setImageBackground(),"backgroundIcon.png");
		addToToolbarHelper("SetMusic",e ->setMusic(),"musicIcon.png");
		addToToolbarHelper("SetSystems",e->{
			SystemsSelector mySystemSelector = new SystemsSelector(myEngine.getWorld());
			mySystemSelector.initialize();
		},"systemsIcon.png");
		addToToolbarHelper("SetSystems",e->{
			ZoneHandlerSelector	myZoneSelector = new ZoneHandlerSelector(myEngine.getWorld());
			myZoneSelector.initialize();
		},"zoneIcon.png");
		addToToolbarHelper("SetWinConditions",e->{
			WinConditionSelector myWinConditionSelector = new WinConditionSelector(myEngine.getWorld());
			myWinConditionSelector.initialize();
		},"winIcon.png");

		this.addButtonToToolbar(AuthoringFactory.makeToggleButton(
				MenuLanguage.getInstance().getValue("CreatePath"),()->myPlayScreenMap.get(myTabPane.getSelectionModel().getSelectedItem().getText()).setUpPaths(),
				()->myPlayScreenMap.get(myTabPane.getSelectionModel().getSelectedItem().getText()).submitPath(),
				new Image(getClass().getClassLoader().getResourceAsStream(BUTTON_ICON_FILEPATH + "pathIcon.png"))));
	}
	
	/**
	 * Sets game background within the authoring environment
	 */
	private void setImageBackground() {
		AuthoringFileChooser imageChooser = new AuthoringFileChooser();
		File image = imageChooser.chooseImage("Background");
		if (image != null) {
			myGameData.getLevelMap().get((myTabPane.getSelectionModel().getSelectedItem().getText())).setBackground("resources/"+image.getName());
			myPlayScreenMap.get(myTabPane.getSelectionModel().getSelectedItem().getText()).setBackground("resources/"+image.getName());
		}
	}
	
	/**
	 * Helper method to add a button to the Authoring Pane toolbar
	 * @param name
	 * @param event
	 * @param imgPath
	 */
	private void addToToolbarHelper(String name, EventHandler<ActionEvent> event, String imgPath){
		this.addButtonToToolbar(AuthoringFactory.makeButton(MenuLanguage.getInstance().getValue(name),event,
				new Image(getClass().getClassLoader().getResourceAsStream(BUTTON_ICON_FILEPATH + imgPath))));
		
	}
	
	/**
	 * Sets game music within the authoring environment
	 */
	private void setMusic(){
		AuthoringFileChooser imageChooser = new AuthoringFileChooser();
		File image = imageChooser.chooseMusic();
		if (image != null) {
			myGameData.getLevel(myTabPane.getSelectionModel().getSelectedItem().getText()).setMusic(image.getAbsolutePath());
		}
	}
}
