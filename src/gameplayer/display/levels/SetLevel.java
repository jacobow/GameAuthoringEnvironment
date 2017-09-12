package gameplayer.display.levels;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import configuration.MenuLanguage;
import gamedata.data.GameData;
import gamedata.data.LevelData;
import gameengine.GameEngine;
import gameengine.GameWorld;
import gameengine.attributes.Energy;
import gameengine.attributes.Health;
import gameengine.attributes.Player;
import gameengine.attributes.Team;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.ExternalEngineInterface;
import gameengine.systems.coresystems.StatisticsManager;
import gameplayer.display.DisplayInterface;
import gameplayer.display.choosecharacter.CharacterChooser;
import gameplayer.display.choosecharacter.TeamNameColor;
import gameplayer.display.gameplay.playscreen.SetPlayerScreen;
import gameplayer.display.gameplay.visualstats.EndScreen;
import gameplayer.display.gameplay.visualstats.FinishLevelScreen;
import gameplayer.display.gameplay.visualstats.NextLevelScreen;
import gameplayer.gamescores.HighScoreLists;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import junit.framework.Test;
/**
 *
 * @author SamFurlong
 *
 */
public class SetLevel implements SetMusic{
	private BorderPane global;
	private TilePane playScreen;
	private DisplayInterface display;
	private String background;
	private Node gameBar;
	private Map<EntityInterface, String> chosenNames;
	private Map<TeamNameColor, ArrayList<EntityInterface>> teams;
	private CharacterChooser myChooser;
	private GameData game;
	private ExternalEngineInterface engine;
	private EventHandler back;
	private transient SetPlayerScreen split;
	private MediaPlayer mediaPlayer;
	private MediaView mediaView;
	private ArrayList<SetPlayerScreen> splits = new ArrayList<SetPlayerScreen>();
	private HighScoreLists myScore;

	private static final double PREF_WIDTH = 200;
	private static final int ONE = 1;
	private static final int ZERO = 0;
	private static final int TWO = 2;
	private static final String DEFAULT_BACKGROUND = "blackground.png";

	public SetLevel(GameData game, DisplayInterface display, Node gameBar, EventHandler back, HighScoreLists score){
		this.back = back;
		this.game = game;
		this.gameBar = gameBar;
		myScore = score;
		checkBackground();
		this.display = display;
		global = new BorderPane();
		setUpBackButton();
		this.engine = new GameEngine();
		toCharacterChooser();
	}
	private void checkBackground(){
		try{
			this.background = game.getCurrentLevel().getBackground();
			}
			catch(Exception e){
				this.background = DEFAULT_BACKGROUND;

				}
	}
	private void setUpBackButton(){
		Button b = new Button(MenuLanguage.getInstance().getValue("back"));
		b.setPrefWidth(PREF_WIDTH);
		b.setOnAction(back);
		global.setTop(b);
	}
	private void toCharacterChooser(){
		GameWorld myGame = game.getCurrentWorld();
		engine.loadGameWorld(myGame);
		myChooser = new CharacterChooser(myGame);
		teams = myChooser.getChosenTeamMap();
		global.setCenter(myChooser.getNode());
		myChooser.startProperty().addListener((x,y,z)->{
			chosenNames = myChooser.getChosenNameMap();
			toGame();
		});
	}

	private void toGame(){
		playScreen = new TilePane();
		playScreen.setMaxSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
		double[] widthHeight = splitWidthHeight();
		for(EntityInterface entity: myChooser.getChosenNameMap().keySet()){
			split = new SetPlayerScreen(engine, this, display, game.getCurrentLevel(), widthHeight[0],  widthHeight[1]);
			drawHUD(entity);

		}
		global.getChildren().clear();
		global.setTop(gameBar);
		global.setCenter(playScreen);
		engine.getMyWinConditionManager().isGameOver().addListener((x,y,z)->{
			getStats();
			gameEndEvent();
		});
	}
	private double[] splitWidthHeight(){
		double width = Screen.getPrimary().getBounds().getWidth();
		double height = Screen.getPrimary().getBounds().getHeight();
		int colLength= (int) Math.round(Math.sqrt((double)chosenNames.size()));
		int newSize = chosenNames.size();
		if(newSize%TWO != ZERO && newSize!=ONE){
			newSize = newSize +ONE;
		}
		display.setEngine(engine);
		setMusic(game.getCurrentLevel().getMusic());
		setInput();
		int rowLength = newSize/colLength;
		double splitWidth = width/rowLength;
		double splitHeight = height/colLength;
		double[] widthHeight = new double[TWO];
		widthHeight[ZERO] = splitWidth;
		widthHeight[ONE] = splitHeight;
		return widthHeight;
 	}

	@Override
	public void setMusic(String levelMusic){
		if(!(mediaPlayer == null)){
		mediaPlayer.pause();
	      ((Pane)( display.getScene().getRoot())).getChildren().remove(mediaView);
		}
		Media media;
		try{
			File file = new File(levelMusic);
			String uri = file.toURI().toString();
			media = new Media(uri);
		}
		catch(NullPointerException n){
			media = new Media(Test.class.getResource("/resources/Monody.mp3").toString());
		}
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
        mediaPlayer.play();

      ((Pane)( display.getScene().getRoot())).getChildren().add(mediaView);

	}
	/**
	 * pausedsthe music being played
	 */
	public void stopMusic(){
        mediaPlayer.pause();
        ((Pane)( display.getScene().getRoot())).getChildren().remove(mediaView);



	}

	private  void setInput(){
		display.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			engine.setOnKeyPressed(key.getCode());
		});

		display.getScene().addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
			engine.setOnKeyReleased(key.getCode());
		});
	}
	private  void drawHUD(EntityInterface entity){
		split.setTeamMap(teams);
		split.setChosenNameMap(chosenNames);
		split.setBackground(background);
		splits.add(split);
		split.addCamera(entity);
		split.createFollowStatusByAttribute(Health.class);
		split.createFollowStatusByAttribute(Energy.class);
		split.createFollowTeamByAttribute(Team.class, myChooser.getChosenTeamMap());
		split.createFollowNameByAttribute(Player.class, chosenNames);
		playScreen.getChildren().add(split.getNode());
	}

    private void gameEndEvent(){
        LevelData myLevel = game.getNextLevel();
        if (myLevel != null){
            FinishLevelScreen myNextLevel = new NextLevelScreen(MenuLanguage.getInstance().getValue("LevelOver"),
                                                              newGame(), nextLevel(), back);

            global.setCenter(myNextLevel.getNode());
        }
        else{
          FinishLevelScreen myEnd = new EndScreen(MenuLanguage.getInstance().getValue("GameOver"), newGame(), back);
          global.setCenter(myEnd.getNode());
        }

     }
    public void getStats(){
    	 StatisticsManager myStats = new StatisticsManager();
    	engine.getWorld().getEntities().forEach(e->{
    		if(e.containsAttribute(Player.class)){
            myScore.addScore(myStats.getKillCountStatistics(e).get(), myStats.getDeathCountStatistics(e).get());
    		}

    	});
    }
    /**
     *
     * @returns the node for the set level screen
     */
	public Node getNode() {
		return global;
	}


	private EventHandler<ActionEvent> newGame(){
	    return e -> {
	        game.resetCurrentGame();
	        toCharacterChooser();
	    };
	}

	private EventHandler<ActionEvent> nextLevel(){
	    return e -> {
	        toCharacterChooser();
	    };
	}


}

