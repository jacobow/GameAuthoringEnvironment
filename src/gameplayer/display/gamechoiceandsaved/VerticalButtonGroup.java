package gameplayer.display.gamechoiceandsaved;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
/**
 * 
 * @author SamFurlong
 *
 */
public class VerticalButtonGroup {
	private VBox group = new VBox();
	public void addLabel(String s){
		group.getChildren().add(new Label(s));
	}
	public void addButton(String name, Double width, EventHandler<ActionEvent> event){
		Button b = new Button(name);
		b.setOnAction(event);
		b.setPrefWidth(width);
		group.getChildren().add(b);
	}
	/**
	 * node to be displayed
	 * @return
	 */
	public Node getNode(){
		return group;
	}
}
