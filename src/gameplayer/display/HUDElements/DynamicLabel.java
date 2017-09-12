package gameplayer.display.HUDElements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * 
 * @author Sam Furlong 
 * 
 *
 */
public class DynamicLabel {
	private static final String LABEL_CSS = "myLabel";
	private static final String ZERO = "0";
	private static final String SLASH = "/";
	private static final double PREF_WIDTH = 100;
	private Label dynamicLabel;
	public DynamicLabel(String toDisplay, DoubleProperty prop1, DoubleProperty prop2){
		dynamicLabel = new Label( toDisplay + ZERO);
		dynamicLabel.setId(LABEL_CSS);
		dynamicLabel.setPrefWidth(100);
		prop1.addListener((x,y,z)->{
		
			dynamicLabel.setText(toDisplay + prop1.get()+SLASH+prop2.get());
		});
		prop2.addListener((x,y,z)->{
			
			dynamicLabel.setText(toDisplay + prop1.get()+SLASH+prop2.get());
		});
	}
	public DynamicLabel(String toDisplay, DoubleProperty prop){
		dynamicLabel = new Label( toDisplay + ZERO);
		dynamicLabel.setId(LABEL_CSS);
		dynamicLabel.setPrefWidth(PREF_WIDTH);
		prop.addListener((x,y,z)->{
			dynamicLabel.setText(toDisplay + prop.get());
		});
	}
	public DynamicLabel(String toDisplay, IntegerProperty killCountStatistics,
			IntegerProperty deathCountStatistics) {
		dynamicLabel = new Label( toDisplay + ZERO+SLASH+ZERO);
		dynamicLabel.setId(LABEL_CSS);
		dynamicLabel.setPrefWidth(100);
		killCountStatistics.addListener((x,y,z)->{
		
			dynamicLabel.setText(toDisplay + killCountStatistics.get()+SLASH+deathCountStatistics.get());
		});
		deathCountStatistics.addListener((x,y,z)->{
			
			dynamicLabel.setText(toDisplay + killCountStatistics.get()+"/"+deathCountStatistics.get());
		});
	}
	public Node getNode(){
		return dynamicLabel;
	}
}
