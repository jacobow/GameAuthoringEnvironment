package gameplayer.display.HUDElements;

import gameengine.entities.EntityInterface;
import gameengine.systems.coresystems.StatisticsManager;
import javafx.scene.control.Label;
/**
 * 
 * @author SamFurlong
 *
 */
public class SetUpHUD extends HeadsUpDisplay{
	private StatisticsManager stats;
	private double width;
	private double height;
	private EntityInterface player;
	private static final String KD = "KD";
	private static final String CSS_ID = "myLabel";
	private static final double HEIGHT_DIV = 40; 
	private static final double WIDTH_DIV = 10;
	private static final double PREF_WIDTH = 100;
	private static final boolean IS_MAIN_BAR = true;
	public SetUpHUD(Double width, Double height, EntityInterface player, StatisticsManager stats) {
		super();
		this.player = player;
		this.stats = stats;
		this.height = height;
		this.width = width;
	}
	/**
	 * adds a health bard to the HUD
	 */
	
	public void addHealth(){
		StatusBar myHealth = new StatusBar(stats.getHealth(player), width/WIDTH_DIV, height/HEIGHT_DIV, IS_MAIN_BAR);
		super.addToTop(myHealth.getNode());
	}
	/**
	 * adds the players chosenname to the HUD
	 * @param chosenName
	 */
	public void addName(String chosenName){
		Label playerName = new Label(chosenName);
		playerName.setId(CSS_ID);
		playerName.setPrefWidth(PREF_WIDTH);
		super.addToTop(playerName);
	}
	/**
	 * add the players Kd tracker to the HUD
	 */
	public void addKD(){
		DynamicLabel kd = new DynamicLabel(KD, stats.getKillCountStatistics(player), stats.getDeathCountStatistics(player));
		super.addToTop(kd.getNode());
	}

}
