package gameplayer.display.HUDElements;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * 
 * @author SamFurlong
 *
 */
public class HeadsUpDisplay{
	private BorderPane clearPane;
	private HBox bottomBox;
	private VBox rightBox;
	private VBox leftBox;
	private HBox topBox;
	private static final String CLEAR_CSS = "clearPane"; 
	public HeadsUpDisplay(){
		bottomBox = new HBox();
		rightBox = new VBox();
		leftBox = new VBox();
		topBox = new HBox();
	
		setUpBoxes();
	}
	private void  setUpBoxes(){
		clearPane = new BorderPane();
		clearPane.setId(CLEAR_CSS);
		clearPane.setBottom(bottomBox);
		clearPane.setRight(rightBox);
		clearPane.setTop(topBox);
		clearPane.setLeft(leftBox);
	}
	/**
	 * 
	 * @param toAdd
	 * adds the given node to the top portion of the HUD border pane
	 */
	public void addToTop(Node toAdd){
		topBox.getChildren().addAll(toAdd);
	}
	/**
	 * 
	 * @param toAdd
	 * adds the given node to the bottom portion of the HUD border pane
	 */
	public void addToBottom(Node toAdd){
		bottomBox.getChildren().addAll(toAdd);
	}
	/**
	 * 
	 * @param toAdd
	 * adds the given node to the left portion of the HUD border pane
	 */
	public void addToLeft(Node n){
		leftBox.getChildren().addAll(n);
	}
	/**
	 * 
	 * @param toAdd
	 * adds the given node to the right portion of the HUD border pane
	 */
	public void addToRight(Node n){
		rightBox.getChildren().addAll(n);
	}
	/**
	 * 
	 * @return 
	 * returns the Node for the HUD border pane
	 */
	public Node getNode(){
		return clearPane;
	}
}