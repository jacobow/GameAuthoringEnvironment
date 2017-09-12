package gameplayer.display.levels;

import gamedata.data.GameData;

import gamedata.data.LevelData;
import gameplayer.display.gamechoiceandsaved.GameLevelChoice;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * @author Sam Furlong
 *
 */
public class LevelChooser{
	private TilePane myBox = new TilePane();
	private String selectedLevel;
	private BooleanProperty start = new SimpleBooleanProperty();
	public LevelChooser(GameData game){
		for(String levelName: game.getLevelMap().keySet()){
			setUpLevelSquare(game.getLevelMap().get(levelName));
		}

	}
	public void setUpLevelSquare(LevelData lvl){
		GameLevelChoice level = new GameLevelChoice(lvl);
		myBox.getChildren().add(level.getNode());
		level.getStartProperty().addListener((x,y,z)->{
		    selectedLevel = lvl.getLevelName();
			start.set(level.getStartProperty().get());
		});
	}
	/**
	 * 
	 * @return
	 * is the level started
	 */
	public ReadOnlyBooleanProperty getStartLevel() {
		return start;
	}

	/**
	 * node to be visualized
	 * @return
	 */
	public Node getNode(){
		return myBox;
	}
	/*
	 * returns the selected level  
	 */
	public String getSelectedLevel() {
		return selectedLevel;
	}
	
	
}
