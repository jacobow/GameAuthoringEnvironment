package gameplayer.display.choosecharacter;

import javafx.scene.paint.Color;
/**
 * 
 * @author SamFurlong
 *
 */
public class TeamNameColor {
	public final String teamName;
	public final Color teamColor;
	
	public TeamNameColor(String teamName, Color teamColor){
		this.teamName = teamName;
		this.teamColor = teamColor;
	}
	public String getTeam(){
		return teamName;
	}
	public Color getColor(){
		return teamColor;
	}
}
