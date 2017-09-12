package gameengine.utilities;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * 
 * For use by any CS308 VOOGASalad team.
 * 
 * Improvements can definitely be made, however a stipulation of use is that if you
 * improve the algorithm for any efficiency boost, you will share that improvement 
 * with the entire class. 
 *
 * @author Walker Eacho 
 * 
 *
 */
public enum BoundingBox {
	
	/**
	 * Gives as close to perfect bounds as possible based on the image. 
	 */
	PERFECT_BOUNDS(){
		public Shape getBounds(Image image) {
			PixelReader pixelReader = image.getPixelReader();
			ArrayList<Rectangle> pixelList = new ArrayList<Rectangle>();
			for(int i=0;i<image.getWidth();i++){
				int startPix =-1;
				int endPix =-1;
				boolean makingRect =false;
				for(int j=0;j<image.getHeight();j++){
					int currentPixel = pixelReader.getArgb(i, j);
					if(currentPixel!=0){
						if(!makingRect){
							makingRect=true;
							startPix = j;
						}
					}else{
						if(makingRect){
							pixelList.add(new Rectangle(i, startPix,1,j-startPix));
							makingRect=false;
						}
					}
					endPix = j;
				}

				if(makingRect){
					pixelList.add(new Rectangle(i,startPix,1,endPix-startPix));
					makingRect=false;
				}
			}

			if(pixelList.size() == 0){
				return null;
			}

			Shape toReturn = Shape.union(pixelList.get(0), pixelList.get(1));

			for(int i=2;i<pixelList.size();i++){
				Rectangle curr = pixelList.get(i);
				toReturn = Shape.union(toReturn, curr);
			}

			return toReturn;
		}
	},

	/**
	 * Gives bounds based on the columns of the image. Ignores any invisible pixels in between the first and last visible pixel 
	 */
	EXTERNAL_BOUNDS(){
		public Shape getBounds(Image image) {
			PixelReader pixelReader = image.getPixelReader();
			ArrayList<Rectangle> pixelList = new ArrayList<Rectangle>();
			for(int i=0;i<image.getWidth();i++){
				System.out.println(/*"Row " + i + " of " + (image.getWidth()-1)*/);
				int startPix =-1;
				int endPix =-1;
				for(int j=0;j<image.getHeight();j++){
					int currentPixel = pixelReader.getArgb(i, j);
					System.out.printf(" %02d ", currentPixel/1000000);
					if(currentPixel!=0){
						if(startPix==-1){
							startPix=j;
						}
						endPix = j;
					}
				}
				pixelList.add(new Rectangle(i,startPix,1,endPix-startPix));
			}

			if(pixelList.size() == 0){
				return null;
			}

			Shape toReturn = Shape.union(pixelList.get(0), pixelList.get(1));

			for(int i=2;i<pixelList.size();i++){
				Rectangle curr = pixelList.get(i);
				toReturn = Shape.union(toReturn, curr);
			}

			return toReturn;
		}
	},
	
	/**
	 * Gives a JavaFX rectangle the size of the image
	 */
	SQUARE_BOUNDS(){
		public Shape getBounds(Image image){
			return new Rectangle(image.getWidth(),image.getHeight());
		}
	};

	/**
	 * Returns the bounds of an image
	 * 
	 * @param image An image that you want to get the bounds of. PNG Images from Photoshop work best.
	 * @return JavaFX Shape of the bounds
	 */
	public Shape getBounds(Image image){
		return null;
	}
}
