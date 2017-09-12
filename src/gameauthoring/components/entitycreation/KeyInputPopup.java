package gameauthoring.components.entitycreation;

import configuration.MenuLanguage;
import gameauthoring.AuthoringFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Purpose: this class creates a pop-up that allows the user to select which
 * key input they would like to assign to a specific ability of an 
 * entity
 * Dependencies: the class is dependent on the user correctly selecting a key
 * Example Use: used to allow the user to assign the W key to the MoveLeft ability of an 
 * entity in a game
 * 
 * @author Larissa Cox
 *
 */

public class KeyInputPopup {
	private KeyCode code;
	private boolean errorDisplayed;
	
	public KeyInputPopup(){
		Stage dialog = new Stage();
        VBox dialogVbox = new VBox(20);
        dialogVbox.getStylesheets().add("defaultStyle.css");
        dialogVbox.getStyleClass().add("vbox");
        dialogVbox.getChildren().add(AuthoringFactory.makeLabel((MenuLanguage.getInstance().getValue("SelectKey"))));
        dialogVbox.getChildren().add(AuthoringFactory.makeLabel((MenuLanguage.getInstance().getValue("KeyAssigned"))));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        setKeyHandler(dialogScene, dialogVbox);
        setCloseButton(dialog, dialogVbox);
        
        dialog.setScene(dialogScene);
        dialog.showAndWait();
	}
	
	/**
	 * purpose: to provide access to the key code that the user
	 * has selected
	 * @return the selected key code
	 */
	public KeyCode getKeyCode(){
		return code;
	}
	
	private void setKeyHandler(Scene dialogScene, VBox dialogVbox){
		 dialogScene.setOnKeyPressed( new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent event) {
					dialogVbox.getChildren().remove(dialogVbox.getChildren().size()-2);
					code = event.getCode();
					dialogVbox.getChildren().add(dialogVbox.getChildren().size()-1, AuthoringFactory.makeLabel((
					                                         MenuLanguage.getInstance().getValue("KeyAssigned") + code.getName())));
				}
	        	
	        });
	}
	
	private void setCloseButton(Stage dialog, VBox dialogVbox){
		Button exit = new Button();
		exit.setText(MenuLanguage.getInstance().getValue("CreateMapping"));
		exit.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if (code == null && !errorDisplayed){
					errorDisplayed = true;
					Text error = new Text(MenuLanguage.getInstance().getValue("PressKey"));
					error.setFill(Color.RED);
					dialogVbox.getChildren().add(dialogVbox.getChildren().size()-2, error);
					return;
				}
				if (code != null){
					dialog.close();
				}
			}
			
		});
		dialogVbox.getChildren().add(exit);
	}
	
}
