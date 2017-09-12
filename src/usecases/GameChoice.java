package usecases;


import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class GameChoice {
	public GameChoice(String name, Image i, Node Achievments){
		Label myLabel = new Label();
		myLabel.setText(name);
		myLabel.setGraphic(new ImageView(i));
		
		Button button1 = new Button();
		button1.setText("Achievments");
		HBox myBox = new HBox();
		myBox.getChildren().addAll(button1, myLabel);
		StackPane myPane = new StackPane();
		
		Button button2 = new Button();
		
		button2.setText(name);
		
		HBox myBox1 = new HBox();
		myBox1.getChildren().addAll(button2, Achievments);
		myPane.getChildren().addAll(myBox, myBox1);
		
		button2.setOnAction(e->{
			myBox.setOpacity(1);
		});
		button1.setOnAction(e->{
			myBox.setOpacity(0);
		});
		
	}
}
