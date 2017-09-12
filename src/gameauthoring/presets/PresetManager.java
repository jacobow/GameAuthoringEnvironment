package gameauthoring.presets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import gameauthoring.presets.interfaces.GeneralPresetViewInterface;
import gameauthoring.presets.interfaces.PresetManagerInterface;
import gameengine.GameWorld;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author DavidYoon
 *
 */
public class PresetManager implements PresetManagerInterface{

	public static final String SYSTEM_FILEPATH = System.getProperty("user.dir") + File.separator + "src" + File.separator;
	public static final String GENRE_PRESET_FILEPATH = "resources" + File.separator + "images"+ File.separator +"genrePreset" + File.separator;
	public static final String RESOURCES_FILEPATH = "resources" + File.separator;
	private static final double VIEWHEIGHT = 710;
	private static final double VIEWWIDTH = 860;

	private static final String PRESET_TITLE = "Preset Manager";
	private BorderPane mainPane;
	private List<GeneralPresetViewInterface> listOfPanes;
	private Stage presetStage;
	private Scene introScene;
	private GenrePresetView genrePresetView;
	private CharacterPresetView characterPresetView;
	private Properties resources = new Properties();
	private ButtonManager buttonManager;
	private IntegerProperty index;
	private GameWorld world;
	
	public PresetManager(GameWorld world){
		this.world = world;
		initializePresetViews();
		initializeView();
	}


	private void initializePresetViews() {
		listOfPanes = new ArrayList<GeneralPresetViewInterface>();
		
		//use factory?
		genrePresetView = new GenrePresetView(world);
		listOfPanes.add(genrePresetView);
		characterPresetView = new CharacterPresetView(world);
		listOfPanes.add(characterPresetView);

	}

	public void initializeView() {
		mainPane = new BorderPane();
		mainPane.getStylesheets().add("defaultstyle.css");
		mainPane.getStyleClass().add("pane");
		buttonManager = new ButtonManager(resources);
		index = buttonManager.getPresetIndex();
		index.addListener((x,y,z)->{ updateView();});

		mainPane.setBottom(buttonManager.makeControlPanel());
		updateView();

		presetStage = new Stage();
		introScene = new Scene(mainPane, VIEWWIDTH, VIEWHEIGHT);
		introScene.getStylesheets().add("defaultstyle.css");
		presetStage.setTitle(PRESET_TITLE);
		presetStage.setScene(introScene);
		presetStage.showAndWait();
	}

	@Override
	public void returnToMainAuthoringScreen() {
		presetStage.close();
	}


	@Override
	public void updateView() {
		if (listOfPanes.size()<=index.get()) returnToMainAuthoringScreen();
		else {
			GeneralPresetViewInterface presetView = listOfPanes.get(index.get());
			mainPane.setCenter(presetView.getCenterPane());
		}
	}
}
