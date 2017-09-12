package gameauthoring;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Authoring Factory class for creating javafx components
 * Can create:Buttons,custom buttons, textfields,labels,text,toggle buttons, and togglebuttons with images
 * Contains an hbox helper function for wrapping an arbitrary number of javafx nodes into an hbox
 * @author michaelseaberg
 *
 */
public class AuthoringFactory {
	
	public AuthoringFactory(){

	}
	
	public static Button makeButton(String property, EventHandler<ActionEvent> handler){
		Button ret = new Button();
		ret.setText(property);
		ret.setOnAction(handler);
		ret.getStyleClass().add("button");
		return ret;
	}
	
	public static Button makeButton(String property, EventHandler<ActionEvent> handler, Image image){
		Button ret = new Button();
		ret.setTooltip(new Tooltip(property));
		ret.setOnAction(handler);
		ret.setGraphic(new ImageView(image));
		return ret;
	}

	public static TextField makeTextField(String string) {
		TextField ret = new TextField(string);
		ret.getStyleClass().add("text-field");
		return ret;
	}

	public static Label makeLabel(String string){
		Label ret = new Label(string);
		ret.getStyleClass().add("label");
		return ret;
	}
	
	public static Text makeText(String text){
		Text ret = new Text(text);
		ret.getStyleClass().add("text");
		return new Text(text);
	}
	
	public static HBox wrapInHBox(Pos alignment,Node... args) {
		HBox newBox = new HBox(args);
		newBox.setAlignment(alignment);
		newBox.setSpacing(6);
		newBox.getStyleClass().add("hBox");
		return newBox;
	}
	
	public static ToggleButton makeToggleButton(String string, Runnable pressed, Runnable notPressed){
		ToggleButton toggler = new ToggleButton(string);
		toggler.selectedProperty().addListener((butt, originalToggle, newToggle)->{
			if(newToggle){
				pressed.run();
			}else{
				notPressed.run();;
			}
		});
		return toggler;
	}
	public static ToggleButton makeToggleButton(String string, Runnable pressed, Runnable notPressed, Image image){
		ToggleButton toggler = new ToggleButton();
		toggler.setTooltip(new Tooltip(string));
		toggler.setGraphic(new ImageView(image));
		toggler.selectedProperty().addListener((butt, originalToggle, newToggle)->{
			if(newToggle){
				pressed.run();
			}else{
				notPressed.run();;
			}
		});
		return toggler;
	}

}