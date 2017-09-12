package gameplayer.display.gameplay.playscreen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import configuration.MenuLanguage;
import gameauthoring.components.selectors.AuthoringFileChooser;
import gamedata.data.LevelData;
import gameengine.attributes.Health;
import gameengine.attributes.Player;
import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.ExternalEngineInterface;
import gameengine.systems.coresystems.StatisticsManager;
import gameplayer.display.DisplayInterface;
import gameplayer.display.HUDElements.SetUpHUD;
import gameplayer.display.choosecharacter.TeamNameColor;
import gameplayer.display.levels.SetMusic;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
/**
 *
 * @author SamFurlong
 *
 */
public class SetPlayerScreen extends PlayScreen{

	private Map<EntityInterface, String> chosenNames;
	private Map<TeamNameColor, ArrayList<EntityInterface>> teamToEntity;
	private StatisticsManager stats = new StatisticsManager();
	private static final double WIN = 1;
	private static final double LOSE = -1;
	private static final String RESOURCES = "resources/";
	public SetPlayerScreen(ExternalEngineInterface currentChoice, SetMusic setMusic,DisplayInterface display, LevelData level,  double width, double height){

		super(currentChoice, display, width, height);
		chosenNames = new HashMap<EntityInterface,String>();
		teamToEntity = new HashMap<TeamNameColor, ArrayList<EntityInterface>>();
		createContextMenu(level, setMusic);

	}
	private void createContextMenu(LevelData level, SetMusic setMusic){
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem music = new MenuItem(MenuLanguage.getInstance().getValue("SetMusic"));
		MenuItem back = new MenuItem(MenuLanguage.getInstance().getValue("SetBackgroundImage"));
		back.setOnAction(e->{
			AuthoringFileChooser myChooser = new AuthoringFileChooser();
			String myChosen = myChooser.chooseImage(MenuLanguage.getInstance().getValue("SetBackgroundImage")).getName();
			super.setBackground(RESOURCES +myChosen);
		});
		music.setOnAction(e->{
			AuthoringFileChooser myChooser = new AuthoringFileChooser();
			String myChosen = myChooser.chooseMusic().getAbsolutePath();
			level.setMusic(myChosen);
			setMusic.setMusic(myChosen);
		});
		contextMenu.getItems().addAll(music, back);
		super.getScroller().setOnMousePressed(m->{
			if(m.getButton() == MouseButton.SECONDARY){
				contextMenu.show(super.getStacker(),m.getScreenX(), m.getScreenY());
			}
			else{
				contextMenu.hide();
			}
		});
	}


	private void addHUD(EntityInterface entity){
		SetUpHUD myHUD = new SetUpHUD(super.getWidth(), super.getHeight(), entity, new StatisticsManager());
		myHUD.addHealth();
		myHUD.addKD();
		myHUD.getNode().toFront();
	}
/**
 * creates a follow status bar
 * @param attribute
 */
	public void createFollowStatusByAttribute(Class<? extends AttributeInterface> attribute){
		for(EntityInterface e: super.getEngine().getWorld().getEntities()){
			if(e.containsAttribute(attribute)){
				if(e.containsAttribute(Health.class)){
					addStatusForEntity(e);
				}
			}
		}
	}
	/**
	 *
	 * @param attributes
	 * @param chosenNames
	 * creates a follow name badge
	 */
	public void createFollowNameByAttribute(Class<? extends AttributeInterface> attributes,  Map<EntityInterface, String> chosenNames){
		setChosenNameMap(chosenNames);
		for(EntityInterface e: super.getEngine().getWorld().getEntities()){
			if(e.containsAttribute(attributes)){
				addNameForEntity(e);
			}
		}
	}

	private void addNameForEntity(EntityInterface myEnt){
		PlayerStats mydata = getPlayerData(myEnt);
		mydata.addLabel(chosenNames.get(myEnt));
		super.getGamePane().getChildren().remove(mydata.getNode());
		super.getGamePane().getChildren().add(mydata.getNode());

	}
	private PlayerStats getPlayerData(EntityInterface e){

		PlayerStats playerData = super.getEntityInfoMap().get(e).getStats();

		return playerData;
	}

	private void addTeamForEntity(EntityInterface myEnt){
		PlayerStats playerData = getPlayerData(myEnt);
		playerData.addLabel(findTeam(myEnt).getTeam(), findTeam(myEnt).getColor());
		super.getGamePane().getChildren().remove(playerData.getNode());
		super.getGamePane().getChildren().add(playerData.getNode());

	}

	private void addStatusForEntity(EntityInterface myEnt){
		PlayerStats playerData =  getPlayerData(myEnt);
		playerData.addStatusBar(stats.getHealth(myEnt), false, null);
		super.getGamePane().getChildren().remove(playerData.getNode());
		super.getGamePane().getChildren().add(playerData.getNode());
	}
/**
 * adds camera centered around
 * @param ID
 */
	public void addCamera(EntityInterface entity){
		if(entity.containsAttribute(Player.class)){
			entity.getAttribute(Player.class).retrieveWinStatus().addListener((x,y,z)->{
				Double win = entity.getAttribute(Player.class).retrieveWinStatus().get();
				if(win.equals(WIN)){
					addImageToGamePane(new Image(this.getClass().getClassLoader().getResourceAsStream("winner.jpg")));
				}
				if(win.equals(LOSE)){
					addImageToGamePane(new Image(this.getClass().getClassLoader().getResourceAsStream("loser.png")));
				}

			});
		}
		addHUD(super.getEntityInfoMap().get(entity).getMyEntity());
		super.getEntityInfoMap().get(entity).setScroller(super.getScroller(), super.getWidth(), super.getHeight());
	}
	private void addImageToGamePane(Image i){
		ImageView gameImage =new ImageView( );
		super.getGamePane().getChildren().add(gameImage);

	}
	private TeamNameColor findTeam(EntityInterface e){
		for(TeamNameColor team: teamToEntity.keySet()){
			if(teamToEntity.get(team).contains(e)){
				return team;
			}
		}
		return null;
	}
	/**
	 * sets the map of teams to be display
	 * @param map
	 */
	public void setTeamMap(Map<TeamNameColor, ArrayList<EntityInterface>> map ){
		this.teamToEntity  = map;
	}
/**
 * sets the map of chosen names to be displayed
 * @param chosenNames
 */
	public  void setChosenNameMap(Map<EntityInterface, String> chosenNames){
		this.chosenNames = chosenNames;
	}
	/**
	 * add a team badge following a given entity
	 * @param attributes
	 * @param map
	 */
	public void createFollowTeamByAttribute(Class<? extends AttributeInterface> attributes,
			Map<TeamNameColor, ArrayList<EntityInterface>> map) {
		setTeamMap(map);
		for(EntityInterface e: super.getEngine().getWorld().getEntities()){
			if(e.containsAttribute(attributes)){
				addTeamForEntity(e);
			}
		}
	}
}



