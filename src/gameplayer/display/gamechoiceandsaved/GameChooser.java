package gameplayer.display.gamechoiceandsaved;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import application.MainController;
import configuration.MenuLanguage;
import gamedata.data.GameData;
import gameengine.interfaces.ExternalEngineInterface;
import gameplayer.display.DisplayInterface;
import gameplayer.profile.AuthUser;
import gameplayer.profile.Profile;
import gameplayer.profile.ProfileMaster;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class GameChooser{
	private ExternalEngineInterface currentChoice;
	private Pane myGlobal;
	private BorderPane myBorder;
	private ToolBar topBar;
	private TilePane myChooseScreen;
	private ScrollPane myScroller;
	private Profile myCurrentProfile;
	private BooleanProperty paused = new SimpleBooleanProperty();
	private DisplayInterface display;
	private Map<String, SavedOrNewScreen> splashMap = new HashMap<String, SavedOrNewScreen>();
	private static double BUTTON_WIDTH = 200;
	private ArrayList<String> gameNames = new ArrayList<String>();
	private Map<String, GameLevelChoiceInterface> nameToChoice = new HashMap<String, GameLevelChoiceInterface>();
	private TextField myField;
	private MainController mainController;
	private Iterable<GameData> gamesInfo;
	private EventHandler<ActionEvent> backEvent;
	private AuthUser myUser;
	private ProfileMaster myProfileMaster;
	public GameChooser(Iterable<GameData> gamesInfo, EventHandler<ActionEvent> backEvent, MainController mainMenu,
			DisplayInterface display) {

		this.mainController = mainMenu;
		myProfileMaster = new ProfileMaster();
		myGlobal = new Pane();

		this.display = display;
		this.gamesInfo = gamesInfo;
		this.backEvent = backEvent;
		profileScreen();
	}

	public void profileScreen() {
		myUser = new AuthUser(myProfileMaster);
		myGlobal.setPrefSize(display.getScene().getWidth(), display.getScene().getWidth());
		myGlobal.getChildren().add(myUser.getNode());
		myUser.getStart().addListener((x, y, z) -> {
			myGlobal.getChildren().clear();
			myCurrentProfile = myUser.getProfile();
			init();
			searchField();
			setProfile(myCurrentProfile.getMyImagePath(), myCurrentProfile.getMyName());
		});

	}

	public void init() {
		gameNames.clear();
		myChooseScreen = new TilePane();
		myBorder = new BorderPane();
		topBar = new ToolBar();
		GameLevelChoice myChoice = null;
		for (GameData game : gamesInfo) {
			gameNames.add(game.getName());
			myChoice = new GameLevelChoice(game);
			myChoice.getStartProperty().addListener((x, y, z) -> {
				setChoice(game);
			});
			nameToChoice.put(game.getName(), myChoice);
			myChooseScreen.getChildren().addAll(myChoice.getNode());
		}
		myScroller = new ScrollPane(myChooseScreen);
		setTopBar();
		setPaneAppearance();
	}

	private void setProfile(String profPicURL, String name) {
		HBox myBox = new HBox();
		Label myLabel = new Label(name);
		ImageView myImage = new ImageView(new Image(profPicURL));

		myBox.getChildren().addAll(myLabel, myImage);
		myBorder.setRight(myBox);
		myBox.setAlignment(Pos.CENTER);
	}

	private void setPaneAppearance() {
		display.getStage().setFullScreen(true);
		myGlobal.setId("pane");
		setPaneSizes(display.getScene().getWidth(), display.getScene().getWidth());

		display.getScene().widthProperty().addListener((x, y, z) -> {
			setPaneSizes(display.getScene().getWidth(), display.getScene().getHeight());
		});
		display.getScene().heightProperty().addListener((x, y, z) -> {
			setPaneSizes(display.getScene().getWidth(), display.getScene().getHeight());
		});
		setChooserAppearance();
		setScrollBarPolicy();
		myScroller.setFitToWidth(true);

	}

	private void setPaneSizes(double x, double y) {
		myChooseScreen.setPrefSize(x, y);
		myScroller.setPrefSize(x, y);
		myBorder.setPrefSize(x, y);
		myGlobal.setPrefSize(x, y);

	}

	private void setChooserAppearance() {
		myChooseScreen.setPadding(new Insets(10, 20, 10, 20));
		myChooseScreen.setId("pane");
		myChooseScreen.setOrientation(Orientation.HORIZONTAL);
	}

	private void setTopBar() {
		topBar.addButton(MenuLanguage.getInstance().getValue("Home"), BUTTON_WIDTH, backEvent);
		topBar.addButton(MenuLanguage.getInstance().getValue("OpenAuthoring"), BUTTON_WIDTH, e -> {
			mainController.setToAuthoring();
		});
	}

	private void setScrollBarPolicy() {
		myScroller.getVbarPolicy();
		myScroller.setVbarPolicy(ScrollBarPolicy.NEVER);
	}

	private void setChoice(GameData game) {
		SavedOrNewScreen myScreen = null;
		if (!splashMap.containsKey(game.getName())) {
			myScreen = new SavedOrNewScreen(new returnToGameChooser(), display, game, myCurrentProfile);
			splashMap.put(game.getName(), myScreen);
		} else {
			myScreen = splashMap.get(game.getName());
		}
		myGlobal.getChildren().clear();
		myGlobal.getChildren().add(myScreen.getNode());
	}

	private void searchField() {
		myField = new TextField();
		HBox myBox = new HBox(topBar.getNode(), myField);
		myField.textProperty().addListener((x, y, z) -> {
			reorderChooseScreen();
		});
		myBorder.setTop(myBox);
		myBorder.setCenter(myScroller);
		myGlobal.getChildren().add(myBorder);
		reorderChooseScreen();
	}

	private class returnToGameChooser implements Runnable {
		public void run() {
			myGlobal.getChildren().clear();
			searchField();
		}
	}

	private void reorderChooseScreen() {
		Comparator c = (s1, s2) -> {
			System.out.println(myField.getText());
			return compareThreeStrings(s1.toString(), s2.toString(), myField.getText());
		};
		Collections.sort(gameNames, c);
		myChooseScreen.getChildren().clear();
		for (String gameName : gameNames) {
			myChooseScreen.getChildren().add(nameToChoice.get(gameName).getNode());
		}
	}

	private int compareThreeStrings(String o1, String o2, String keyWord) {
		o1 = o1.toLowerCase();
		o2 = o2.toLowerCase();
		keyWord = keyWord.toLowerCase();
		if (o1.startsWith(keyWord)) {
			return o2.startsWith(keyWord) ? o1.compareTo(o2) : -1;
		} else {
			return o2.startsWith(keyWord) ? 1 : o1.compareTo(o2);
		}

	}

	public Node getNode() {
		return myGlobal;
	}


	public BooleanProperty getPaused() {
		return paused;
	}

	public void setPaused(BooleanProperty pause) {
		this.paused = pause;
	}

	@Deprecated
	public BooleanProperty displayGame() {
		// TODO Auto-generated method stub
		return null;
	}
}