package gameauthoring.presets;

import java.util.Properties;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * 
 * @author DavidYoon
 *
 */
public class ButtonManager {
	private Button nextButton;
	private IntegerProperty presetIndex = new SimpleIntegerProperty();
	private Button skipAllButton;
	private HBox controlPanel;
	private Properties myResources;
	
	public ButtonManager(Properties resources) {
		this.myResources = resources;
		
	}


	public HBox makeControlPanel() {
		controlPanel = new HBox();
		nextButton = makeButton("nextButton", event -> presetIndex.set(presetIndex.get()+1));
		controlPanel.getChildren().add(nextButton);
		skipAllButton = makeButton("skipAllCommand", event -> presetIndex.set(presetIndex.get()+5));
		controlPanel.getChildren().add(skipAllButton);
		return controlPanel;
	}

	private Button makeButton (String property, EventHandler<ActionEvent> handler) {
//		String label = myResources.getString(property);
//		ImageView imageView = new ImageView();
		Button button = new Button(property);
		button.setOnAction(handler);
		return button;
	}


	public IntegerProperty getPresetIndex() {
		return presetIndex;
	}


	public void setPresetIndex(IntegerProperty presetIndex) {
		this.presetIndex = presetIndex;
	}

}
