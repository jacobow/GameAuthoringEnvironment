package usecases;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author Blake Becerra
 * Based of my teams cellsociety's file chooser but edited to match this projects purpose. 
 */

public class ChooseGame {

	private Stage myStage;

	public ChooseGame(){
		myStage = new Stage();
	}

	public File chooseGame(){
		FileChooser gameChooser = new FileChooser();
		gameChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		gameChooser.getExtensionFilters().add(new ExtensionFilter("Game (*.XML)", "*.XML"));
		File choosenGame = gameChooser.showOpenDialog(myStage);
		if (choosenGame == null) {
			myStage.close();
			return null;
		}
		return choosenGame;
	}
}
