package gameauthoring;

import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

/**
 * Class used as skeleton JavaFX class for all components of the Authoring environment modular workspace.
 * Each pane contains its own vBox, and each pane has methods for adding specific parameters that could be desired in 
 * such a workspace.
 * @author michaelseaberg
 *
 */
public class AuthoringPane{
	private MenuBar myMenuBar;
	private VBox myBox;
	private ToolBar myToolbar;
	
	public AuthoringPane(String title){
		myBox = new VBox();
		myToolbar = new ToolBar();
		createMenuBar(title);
		setStyle();
	}
	
	public void setChildrenSpacing(int spacingValue){
		myBox.setSpacing(spacingValue);
	}
	private void setStyle() {
		myBox.getStyleClass().add("vbox");
	}
	private void createMenuBar(String title) {
		Menu menuLabel = new Menu(title);
		menuLabel.getStyleClass().add("menu");
		myMenuBar = new MenuBar(menuLabel);
		
		myMenuBar.getStyleClass().add("menu-bar");
		myBox.getChildren().add(myMenuBar);
	}
	
	public VBox getBox(){
		return myBox;
	}
	
	public void addMenuToBar(Menu menu){
		menu.setId("menu2");
		menu.getStyleClass().add("menu-item");
		myMenuBar.getMenus().add(menu);
	}
	
	public MenuBar getMyMenuBar(){
		return myMenuBar;
	}
	
	public ToolBar getMyToolBar(){
		myToolbar.getStyleClass().add("tool-bar");
		return myToolbar;
	}
	
	public void addButtonToToolbar(Node button){
		button.setId("toolbarButton");
		button.setScaleX(0.8);
		button.setScaleY(0.8);
		myToolbar.getItems().add(button);
	}
}