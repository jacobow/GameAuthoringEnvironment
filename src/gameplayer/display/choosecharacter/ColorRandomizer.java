package gameplayer.display.choosecharacter;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
/**
 * 
 * @author SamFurlong
 *
 */
public class ColorRandomizer {

	public Color getRandomColor() {

		int R = 0;
		int G = 0;
		int B = 0;
		while (Math.abs(R - G) < 50 && Math.abs(R - B) < 50 && Math.abs(G - B) < 50) {
			R = (int) (Math.random()*256);
			G = (int) (Math.random()*256);
			B = (int) (Math.random()*256);
		}		
		
		Color color = Color.rgb(R, G, B);

		return color;
	}
	
	public Color getInvertedColor(Color original) {
		//
		return original.invert();
	}
	
	public Color getInvertedColor(Paint original) {
		Color newColor = Color.web(original.toString()).invert();
		return newColor;
	}
}
