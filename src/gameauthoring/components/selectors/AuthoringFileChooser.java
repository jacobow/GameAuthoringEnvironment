package gameauthoring.components.selectors;

import java.io.File;
import configuration.MenuLanguage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Helper class that provides methods for choosing images and music for a game.
 * @author michaelseaberg
 *
 */
public class AuthoringFileChooser {
	private Stage myStage;

	public File chooseImage(String purpose) {
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images (*.png, *jpg)", "*.png", "*.jpg");
		return chooseFile(purpose,extensionFilter);
	}
	
	public File chooseMusic() {
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Music (*.mp3", "*.mp3");
		return chooseFile("ChooseMusic",extensionFilter);
	}
	
	private File chooseFile(String title, FileChooser.ExtensionFilter filter){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(MenuLanguage.getInstance().getValue(title));
		String userDirectoryString = System.getProperty("user.dir") + "/src/resources";
		File userDirectory = new File(userDirectoryString);
		fileChooser.setInitialDirectory(userDirectory);
		fileChooser.getExtensionFilters().add(filter);
		File chosenFile = fileChooser.showOpenDialog(myStage);
		if (chosenFile == null) {
			return null;
		}
		return chosenFile;
	}
}
