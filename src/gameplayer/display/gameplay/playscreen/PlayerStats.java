package gameplayer.display.gameplay.playscreen;

import gameplayer.display.HUDElements.StatusBar;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.stage.Screen;
/**
 * 
 * @author SamFurlong
 *
 */
public class PlayerStats{
	private double width = Screen.getPrimary().getBounds().getWidth();
	private double height = Screen.getPrimary().getBounds().getWidth();
	private VBox myInfo = new VBox();
	private static final String X = "x";
	private static final String COLOR_CSS = "-fx-text-fill: #";
	private static final int COLOR_INDEX = 1;
	/**
	 * 
	 * @param info
	 * @param b
	 * @param color
	 */
	public void addStatusBar(DoubleProperty info, boolean b, String color){
		StatusBar status = new StatusBar(info, width/20, height/100, b);
		myInfo.getChildren().add(status.getNode());
	}
	/**
	 * 
	 * @param info
	 * @param color
	 */
	public void addLabel(String info, Color color){
		Label myLab = new Label(info);
		String[] colors = color.toString().split(X);
		myLab.setStyle( COLOR_CSS+ colors[COLOR_INDEX]);
		myInfo.getChildren().add(myLab);
	}
	/**
	 * 
	 * @param info
	 */
	public void addLabel(String info){
		Label myLab = new Label(info);
		myInfo.getChildren().add(myLab);
	}
	/**
	 * 
	 * @return
	 */
	public Node getNode(){
		return myInfo;
	}
	
	
}
