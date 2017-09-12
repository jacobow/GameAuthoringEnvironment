package gameplayer.display.HUDElements;


import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
/**
 * 
 * @author SamFurlong
 *
 */
public class StatusBar{
	
	private Rectangle bar;
	private Rectangle outline;
	private Pane myPane;
	private static final double CLEAR= 0; 
	private static final double OPAC = 1;
	private static final double ANIMATION_DURATION = .5;

	public  StatusBar(DoubleProperty health, Double barWidth, Double barHeight, boolean isMain) {
				myPane = new Pane();
		        bar = new Rectangle(CLEAR,OPAC, barWidth, barHeight);
		        outline = new Rectangle(CLEAR,OPAC, barWidth, barHeight);
		        myPane.getChildren().addAll(outline, bar);
		        outline.setFill(Color.DARKRED);
		        bar.setFill(Color.DARKGREEN);
		        if(isMain){
		        bar.setOpacity(OPAC);
		        outline.setOpacity(OPAC);
		        }
		        else{
		        	bar.setOpacity(CLEAR);
			        outline.setOpacity(CLEAR);

		        }
		   
		        addListener(health, isMain, barWidth);
   }
		    
		
		     
		private void addListener(DoubleProperty health, boolean isMain, double barWidth){
		     health.addListener((x,y,z)->{
		        	Double newWidth = barWidth* health.get();
		        	bar.setWidth(newWidth);
		        	if(!isMain){
		        	bar.setOpacity(OPAC);
			        outline.setOpacity(OPAC);
		        	PauseTransition visiblePause = new PauseTransition();
		            FadeTransition ft = new FadeTransition(Duration.seconds(ANIMATION_DURATION), bar);
		            FadeTransition trans = new FadeTransition(Duration.seconds(ANIMATION_DURATION), outline);
		            trans.setFromValue(OPAC);
		            trans.setToValue(CLEAR);
		            ft.setFromValue(OPAC);
		            ft.setToValue(CLEAR);

		        	visiblePause.setDuration(Duration.seconds(1));
		        	visiblePause.setOnFinished(
		        	        event -> {ft.play();
		        	        trans.play();}
		        	);
		  
		        	visiblePause.play();

		        	}
		        });
		}
		/**
		 * returns the node to be visualized    
		 */

	public Node getNode() {
		return myPane;
	}

}
