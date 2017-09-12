package gameplayer.display.gamechoiceandsaved;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
/**
 * 
 * @author SamFurlong
 *
 */
public class ToolBar {
	private HBox myBox = new HBox();

	public void addButton(String name, Double width, EventHandler<ActionEvent> event){
		Button b = new Button(name);
		b.setOnAction(event);
		b.setPrefWidth(width);
		myBox.getChildren().add(b);
	}
	/**
	 * node to be displaye
	 * @return
	 */
	public Node getNode(){
		return myBox;
	}
	

}
