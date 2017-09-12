package gameplayer.display.gamechoiceandsaved;
import java.util.List;

import configuration.MenuLanguage;
import gamedata.data.GameData;
import gameengine.GameEngine;
import gameengine.interfaces.ExternalEngineInterface;
import gameplayer.display.DisplayInterface;
import gameplayer.display.levels.LevelChooser;
import gameplayer.display.levels.SetLevel;
import gameplayer.filereading.FindSavedGames;
import gameplayer.filereading.FindSavedGamesInterface;
import gameplayer.gamescores.HighScoreLists;
import gameplayer.profile.Profile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Sam Furlong
 *
 */
public class SavedOrNewScreen {
	private DisplayInterface display;
	private ExternalEngineInterface engine;
	private BorderPane global;
	private GameData gameInfo;
	private double PREFWIDTH = 150;
	private TilePane components;
	private VerticalButtonGroup myGroup;
	private ScrollPane myScroller;
	private Runnable returnToGameChooser;
	private LevelChooser lvlChoose;
	private transient SetLevel leveler;
	private Profile user;
	private String userName = "Default";
	private HighScoreLists myScore;
	private static final double PREF_WIDTH = 200;
	private static final String HIGH_SCORES = "High Scores";
	private static final String DOUBLE_DASH = "--";
	private static final String DOUBLE_SLASH = "//";
	public SavedOrNewScreen(Runnable returnToGameChooser, DisplayInterface display,
			GameData gameInfo, Profile user) {
		this.returnToGameChooser = returnToGameChooser;
		this.gameInfo = gameInfo;
		this.display = display;
		this.engine = new GameEngine();
		this.user = user;
		myGroup = new VerticalButtonGroup();
		global = new BorderPane();
		user.getMyName();
		user.getMyHighScores();
		gameInfo.getName();
		if (!user.getMyHighScores().containsKey(gameInfo.getName())) {
			user.getMyHighScores().put(gameInfo.getName(),new HighScoreLists(gameInfo.getName()));
		}
		setUpPanes();
		setGameChoice();
		setUpSaved();
		topBar();
		setUpHighScores();
	}

	private void setUpPanes() {
		setUpComponents();
		setUpGlobal();
		setUpScroller();
		addPaneHeightListeners();

	}

	private void setGameChoice() {
		GameLevelChoice myChoice = new GameLevelChoice(gameInfo);
		myChoice.getStartProperty().addListener(l -> {
			initLevelChooser();
		});
		components.getChildren().add(myChoice.getNode());
	}

	private void addPaneHeightListeners() {
		display.getScene().widthProperty().addListener((x, y, z) -> {
			myScroller.setPrefSize(display.getScene().getWidth(), display.getScene().getHeight());
			components.setPrefSize(display.getScene().getWidth(), display.getScene().getHeight());
			global.setPrefWidth(display.getScene().getWidth());
		});
	}

	private void setUpComponents() {
		components = new TilePane();
		components.setPrefColumns(3);
		components.setPrefSize(display.getScene().getWidth(), display.getScene().getHeight());

	}

	private void setUpScroller() {
		myScroller = new ScrollPane(global);
		myScroller.setPrefSize(display.getScene().getWidth(), display.getScene().getHeight());
		myScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		myScroller.setVbarPolicy(ScrollBarPolicy.NEVER);
	}

	private void setUpGlobal() {
		global.setPrefSize(display.getScene().getWidth(), display.getScene().getHeight());
		global.setCenter(components);
		global.setId("pane");
	}

	private void setUpHighScores() {
		VBox myBox = new VBox();
		Label title = new Label(HIGH_SCORES);
		myBox.getChildren().add(title);
		if (!user.getMyHighScores().isEmpty()) {
			user.getMyHighScores().get(gameInfo.getName()).getMyScores().forEach(e -> {
				myBox.getChildren().add(new Label (DOUBLE_DASH + e.getFirst() + DOUBLE_SLASH+ e.getLast()));
			});
		}
		components.getChildren().add(myBox);
	}

	private void setUpSaved() {
		Label myLab = new Label(MenuLanguage.getInstance().getValue("SavedGames"));
		FindSavedGamesInterface mySaved = new FindSavedGames();
		List<GameData> mySavedGames = mySaved.findSavedGames(gameInfo.getPackageName(), userName);
		for (GameData game : mySavedGames) {
			myGroup.addButton(game.getName(), PREF_WIDTH, e -> {
				setLevel(game);

			});
		}
		components.getChildren().add(new VBox(myLab, myGroup.getNode()));
	}

	private void topBar() {
		Button back = new Button(MenuLanguage.getInstance().getValue("Back"));
		back.setOnAction(e -> {
			returnToGameChooser.run();
		});
		back.setPrefWidth(PREF_WIDTH);
		back.setAlignment(Pos.TOP_CENTER);
		back.setAlignment(Pos.BOTTOM_CENTER);
		back.setAlignment(Pos.CENTER);
		global.setTop(back);
	}

	private void initLevelChooser() {
		global.getChildren().clear();
		lvlChoose = new LevelChooser(gameInfo);
		lvlChoose.getStartLevel().addListener(l -> {
			if (lvlChoose.getStartLevel().get()) {

				gameInfo.setCurrentLevel(lvlChoose.getSelectedLevel());
				setLevel(gameInfo);
			}
		});
		Button returnToSavedOrNew = new Button(MenuLanguage.getInstance().getValue("Back"));
		returnToSavedOrNew.setPrefWidth(PREF_WIDTH);
		returnToSavedOrNew.setOnAction(returnToThis());
		global.setTop(returnToSavedOrNew);
		global.setCenter(lvlChoose.getNode());
	}

	private EventHandler<ActionEvent> backToLevel() {
		return e -> {
			Button returnToSavedOrNew = new Button(MenuLanguage.getInstance().getValue("back"));
			returnToSavedOrNew.setOnAction(returnToThis());
			gameInfo.resetCurrentLevel();
			global.getChildren().clear();
			global.setCenter(lvlChoose.getNode());
			global.setTop(returnToSavedOrNew);
		};
	}

	private void setLevel(GameData game) {
		System.out.println(game.getCurrentLevel().getGameWorld());
		leveler = new SetLevel(game, display, gameBar(engine, gameInfo.getName()), backToLevel(), myScore);
		global.getChildren().clear();
		display.setEngine(engine);

		global.getChildren().clear();
		global.setCenter(leveler.getNode());
	}

	private Node gameBar(ExternalEngineInterface currentEngine, String gameName) {
		ToolBar myBar = new ToolBar();
		myBar.addButton("Home", PREFWIDTH, returnToThis());
		myBar.addButton(MenuLanguage.getInstance().getValue("Restart"), PREFWIDTH, e -> {
			gameInfo.resetCurrentLevel();
			leveler.stopMusic();
			setLevel(gameInfo);
		});
	

		myBar.addButton(MenuLanguage.getInstance().getValue("Save"), PREFWIDTH, e -> {
			gameInfo.getCurrentWorld().clearAllObserver();
			engine.getWorld().getMyPlayerEntities();
			gameInfo.saveGamePlayer();
		});

		myBar.addButton(MenuLanguage.getInstance().getValue("Pause"), PREFWIDTH, e -> {
			leveler.stopMusic();

			display.togglePause();
		});

		return myBar.getNode();

	}

	private EventHandler<ActionEvent> returnToThis() {
		EventHandler<ActionEvent> returnTo = e -> {
			global.getChildren().clear();
			components.getChildren().clear();
			setGameChoice();
			setUpSaved();
			topBar();
			setUpHighScores();
			global.setCenter(components);
		};
		return returnTo;

	}
/**
 * 
 * @return
 * returns the node for the saved or new screen
 */
	
	public Node getNode() {
		return myScroller;

	}

}