package gameplayer.display.gameplay.visualstats;

import configuration.MenuLanguage;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HighScores{
    
	private VBox myBox = new VBox();
	private int rank = 0;
	private static final String COLON = ": ";
	public HighScores(){
		Label highScores = new Label(MenuLanguage.getInstance().getValue("HighScore"));
		myBox.getChildren().add(highScores);
	}
	public void addScore(String scoreType, Double score){
		Label newScore = new Label(rank + scoreType + COLON + score);
		rank++;
		myBox.getChildren().add(newScore);
	}
	
	public Node getNode(){
		return myBox;
	}
}

