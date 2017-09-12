package application;
import java.awt.Dimension;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import configuration.MenuLanguage;
import gameauthoring.AuthoringEnvironment;
import gameauthoring.components.selectors.AuthoringFileChooser;
import gameauthoring.presets.PresetManager;
import gamedata.data.GameData;
import gamedata.fileIO.ChooseFileName;
import gameengine.GameEngine;
import gameengine.GameWorld;
import gameplayer.display.Display;
import gameplayer.display.DisplayInterface;
import gameplayer.display.gamechoiceandsaved.VerticalButtonGroup;
import gameplayer.filereading.FindDefaultGames;
import gameplayer.filereading.FindDefaultGamesInterface;
import gameplayer.filereading.FindGames;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Launches the main menu and allows the user to enter into the various environments available
 * @author Sam
 * @author Michael
 * @author Brian
 * @author Walker
 *
 */
public class MainController{

	private static FileChooser fileChooser;
	private static final Dimension MAIN_MENU_SIZE = new Dimension(500, 302);
	final URL HTML_HELP_FILE = this.getClass().getClassLoader().getResource("resources/helpfiles/html_help_file.html");
	private String cssFile;
	//magic number not needed
	private static final int BUTTON_BOX_OFFSET = 300;
	private Scene myScene;
	private BorderPane borderPane;
	private ComboBox languages;
	private VBox menuArea;
	private Stage myStage;
	private Double BS = 300.0;
	//Used for engine testing. Unsure if this is good.
	private DisplayInterface myDisplay;
	private AuthoringEnvironment myAuthoringWorkspace;
	private GameWorld myWorld;
	public MainController(Stage stage) {
		myStage = stage;
		cssFile = "resources/stylings/myStyle.css";
		initializeMenu();
	}
	
	private void initializeMenu() {
		//TODO: Un-comment these methods when we choose background image
		//Image background = new Image(getClass().getClassLoader().getResourceAsStream(MENU_BACKGROUND));
		//myGroup.getChildren().addAll(new ImageView(background);
		myAuthoringWorkspace = new AuthoringEnvironment(Main.DEFAULT_SIZE.getWidth(),Main.DEFAULT_SIZE.getHeight(), l->{
			initializeMenu();
		}, m -> {
			setToPlayer();
		});
		borderPane = new BorderPane();
		myScene = new Scene(borderPane,MAIN_MENU_SIZE.getWidth(), MAIN_MENU_SIZE.getHeight());
		myScene.getStylesheets().add(cssFile);
		borderPane.setId("pane");
		fileChooser = new FileChooser();
		createMenuButtons();
		//createLanguageChooser();
		borderPane.setCenter(menuArea);
		myStage.setScene(myScene);
		myStage.setFullScreen(false);
		//myGroup.getChildren().add(menuArea);
	}
	private void createMenuButtons() {
		menuArea = new VBox();
		menuArea.setPrefSize(MAIN_MENU_SIZE.getWidth(), MAIN_MENU_SIZE.getHeight()-BUTTON_BOX_OFFSET);
		menuArea.setAlignment(Pos.CENTER);
		menuArea.setSpacing(15);
		//TODO: For now these buttons are generated using internal methods but should be changed to use the button factory ASAP
		createButton("NewAuthoring", e->openAuthoringEnvironment());
		createButton("OldAuthoring", e->loadGameToAuthoring());
		createButton("Player", e->openGamePlayer());
		createButton("Help", e-> getHTMLHelpFile());
		//createButton("ChangeStyling", e->changeStyling());
	}

//	private void createLanguageChooser(){
//		languages = new ComboBox<String>();
//		languages.setValue(MenuLanguage.getInstance().getValue("ChangeLanguage"));
//		languages.getItems().addAll(getAvailableLanguages());
//		languages.setOnAction(e ->
//		{
//			System.out.println(e.toString());
//			//MenuLanguage.setLanguage(e.);
//		});
//		menuArea.getChildren().add(languages);
//	}

//	private List<String> getAvailableLanguages(){
//		List<String> availableLanguages = new ArrayList<String>();
//		File file = new File(System.getProperty("user.dir") + "/src/resources/languages");
//		for (File f : file.listFiles()){
//			System.out.println(f.isDirectory());
//			availableLanguages.add(f.getName().split("\\.")[0]);
//		}
//		return availableLanguages;
//	}

	private void openGamePlayer() {
		/**
		 * code used for engine testing. pls replace with good code --Walker
		 */
		myDisplay = new Display();
		myStage.setScene(myDisplay.init(l->{
			initializeMenu();
		}, this, myStage));
		myStage.setFullScreen(false);

		step(0.001);
	}

	public void setToPlayer(){
		try{
			myStage.setScene(myDisplay.getScene());
			myStage.setFullScreen(false);
			

		} catch (NullPointerException e) {
			openGamePlayer();
		}

	}
	/**
	 * Method was was to be used for opening up the authoring environment from the game player tab. Was not implemented.
	 */
	public void setToAuthoring(){

	}

	private void openAuthoringEnvironment() {
		GameEngine myEngine = new GameEngine();
		myWorld = myEngine.getWorld();
		ChooseFileName CFN = new ChooseFileName(MenuLanguage.getInstance().getValue("NameGame"),
		                                        MenuLanguage.getInstance().getValue("Gamer Namer"),
		                                        MenuLanguage.getInstance().getValue("SelectNameOfGame"));
		GameData gameData = new GameData(CFN.getFileName());
		getGameIcon(gameData);
		myAuthoringWorkspace.initialize(gameData);
		myDisplay = myAuthoringWorkspace;
		myStage.setX(0);
		myStage.setY(0);

		myStage.setTitle(CFN.getFileName());
		myStage.setScene(myAuthoringWorkspace.getScene());
		myStage.show();
//		PresetManager presetManager = new PresetManager(myWorld);
	}

	private void getGameIcon(GameData myGameData){
            AuthoringFileChooser imageChooser = new AuthoringFileChooser();//MenuLanguage.getInstance().getValue("ChooseIcon"));
            File image = imageChooser.chooseImage(MenuLanguage.getInstance().getValue("ChooseIcon"));
            if (image != null) {
                String imgPath = image.toString();
                String separator = Pattern.quote(System.getProperty("file.separator"));
                String imgPath2 = imgPath.split("src" + separator)[1];
                myGameData.setIcon(imgPath2);
            }
        }
	
	private void loadGameToAuthoring(){
		Pane temp = new Pane();
		Scene popUP = new Scene(temp, 100, 100);
		Stage pop = new Stage();
		FindDefaultGamesInterface fg = new FindDefaultGames();
		Iterable<GameData> idg = fg.findDefaultGames();
		VerticalButtonGroup vbg = new VerticalButtonGroup();
		for(GameData gd: idg){
			vbg.addButton(gd.getName(), 100.0, e-> {
				gd.fillLevelData();
				System.out.println("Number of entities in level is: " +
				        gd.getCurrentLevel().getGameWorld().getEntities().iterator().next().getID());
				
				myAuthoringWorkspace.initialize(gd);
				pop.close();});
				
		}
		temp.getChildren().add(vbg.getNode());
		pop.setScene(popUP);
		pop.showAndWait();
		myStage.setX(0);
		myStage.setY(0);
		myStage.setScene(myAuthoringWorkspace.getScene());
		myDisplay = myAuthoringWorkspace;
		myStage.show();
	}

	private void getHTMLHelpFile() {
		Stage webStage = new Stage();
		Group webGroup = new Group();
		Scene webScene = new Scene(webGroup,webStage.getWidth(),webStage.getHeight());
		WebView helpView = new WebView();
		WebEngine helpEngine = helpView.getEngine();
		helpEngine.load(HTML_HELP_FILE.toString());
		webGroup.getChildren().add(helpView);
		webStage.setScene(webScene);
		webStage.show();
	}
	public Scene getScene() {
		return myScene;
	}

	private void createButton(String name, EventHandler<ActionEvent> handler){
		Button button = new Button();
		button.setText(MenuLanguage.getInstance().getValue(name));
		button.setOnAction(handler);
		menuArea.getChildren().add(button);
	}

	public void step(double secondDelay) {
		if(myDisplay!=null){
			myDisplay.step(secondDelay);
		}
	}
}