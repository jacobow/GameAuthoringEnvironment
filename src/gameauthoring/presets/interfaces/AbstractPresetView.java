package gameauthoring.presets.interfaces;

import java.io.File;

import javafx.scene.layout.BorderPane;

/**
 * @author DavidYoon
 *
 */
public abstract class AbstractPresetView implements GeneralPresetViewInterface {
	
	public static final String SYSTEM_FILEPATH = System.getProperty("user.dir") + File.separator + "src" + File.separator;
	protected BorderPane centerPane;

	@Override
	public BorderPane getCenterPane() {
		return centerPane;
	}


}
