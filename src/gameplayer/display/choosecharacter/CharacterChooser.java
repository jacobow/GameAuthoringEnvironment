package gameplayer.display.choosecharacter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import gameengine.GameWorld;
import gameengine.attributes.Player;
import gameengine.attributes.Team;
import gameengine.entities.EntityInterface;
import gameplayer.interfaces.CharacterChooserInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
/**
 * 
 * @author SamFurlong
 *
 */
public class CharacterChooser implements CharacterChooserInterface{
	private ScrollPane myScroller;
	private TilePane myPane = new TilePane();
	private HashMap<TeamNameColor, ArrayList<EntityInterface>> teams;
	private GameWorld world;
	private int startNum;
	private int numStarted;
	private BooleanProperty StartProperty;
	private HashMap<EntityInterface, String> chosenNames;
    private ArrayList<EntityInterface> player;
    private static final double INSETS =40;
	public CharacterChooser(GameWorld world){
		startNum = 0; 
		numStarted = 0; 
		myPane.setPadding(new Insets(INSETS));
		this.world = world;
		chosenNames = new HashMap<EntityInterface, String>();
		player = new ArrayList<EntityInterface>();
		teams = new HashMap<TeamNameColor, ArrayList<EntityInterface>>();
		StartProperty = new SimpleBooleanProperty();	
		populateTeamToEntityMap();
		populatePlayerList();
		noTeamsCheck();
		populateTeamPanels();		
	}
	private void noTeamsCheck(){
		if(teams.isEmpty()){
			TeamPanel teamVis = new TeamPanel(null, player);
			myPane.getChildren().add(teamVis.getNode());
			teamVis.startProperty().addListener((x,y,z)->{
			    chosenNames.putAll(teamVis.getChosenNameMap());
			});
			addStartListen(teamVis);
			return;
		}
	}
	private void populateTeamPanels(){
		for(TeamNameColor team: teams.keySet()){
			TeamPanel teamVis = new TeamPanel(team.getTeam(), teams.get(team));
			addInduvidualTeamPanelListeners(teamVis, team);
			myPane.getChildren().add(teamVis.getNode());
			addStartListen(teamVis);

		}
	}
	private void addInduvidualTeamPanelListeners(TeamPanel teamVis, TeamNameColor team){
		teamVis.startProperty().addListener((x,y,z)->{
			teams.put(new TeamNameColor(teamVis.getChosenName(), team.getColor()), teams.get(team));
			chosenNames.putAll(teamVis.getChosenNameMap());
			teams.remove(team);

		});
	}
	private void addStartListen(TeamPanel teamVis){
		startNum++;
		teamVis.startProperty().addListener((x,y,z)->{
			if(teamVis.startProperty().get()){
				numStarted++;
				StartProperty.set(numStarted == startNum);	
			}
			else{
				numStarted--;
			}
		});
	}
	private void populatePlayerList(){
	    for(EntityInterface entity : world.getEntities()){
                if(entity.containsAttribute(Player.class)){
                			player.add(entity);
                              
                }
        }
	}
	private void populateTeamToEntityMap(){
		ColorRandomizer randColor = new ColorRandomizer();
		for(EntityInterface entity : world.getEntities()){
			if(entity.containsAttribute(Team.class)&& entity.containsAttribute(Player.class)){
				String team = entity.getAttribute(Team.class).getTeam();
				if(!teams.containsKey(team)){
					ArrayList<EntityInterface> e = new ArrayList<EntityInterface>();
					e.add(entity);
					Color myColor =randColor.getRandomColor();
					teams.put(new TeamNameColor(team, myColor), e);
				}
				else{
				 teams.get(team).add(entity);
				}
			}
		}
	}
	@Override
	public BooleanProperty startProperty(){
		return StartProperty;
	}
	@Override
	public Map<EntityInterface, String>getChosenNameMap(){
		return chosenNames;
	}
	@Override
	public Map<TeamNameColor, ArrayList<EntityInterface>> getChosenTeamMap(){
		return teams;
	}
	@Override
	public Node getNode(){
		myScroller = new ScrollPane(myPane);
		myScroller.setVbarPolicy(ScrollBarPolicy.NEVER);
		myScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		myPane.setId("pane");
		myPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getWidth());
		return myScroller;
	}
}
