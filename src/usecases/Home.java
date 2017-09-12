//package usecases;
//
//import gameplayer.display.gamechoiceandsaved.GameChooserInterface;
//import javafx.scene.Node;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.layout.VBox;
//
//public class Home {
//	//view all games in a list 
//	ScrollPane scroller;
//	public Home(Iterable<GameChooserInterface> games){
//		VBox myBox = new VBox();
//		for( GameChooserInterface game: games){
//			myBox.getChildren().add(game.getNode());		
//		}
//		scroller = new ScrollPane(myBox);
//
//	}
//	public Node getNode(){
//		return scroller;
//	}
//}
