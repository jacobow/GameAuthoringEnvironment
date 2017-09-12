package gameauthoring.components.entitycreation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import configuration.MenuLanguage;
import gameauthoring.AuthoringFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Purpose: this class creates a pop-up that allows the user to select which
 * controller input they would like to assign to a specific ability of an 
 * entity
 * Assumptions: the class assumes that the user correctly selects an input and that
 *  /src/resources/controller/xbox_mappings.properties
 * contains a list of controller input properties, but this line could be changed to
 * reflect a different file location
 * Dependencies: the class is dependent on the number of controllers that is passed in
 * Example Use: used to allow the user to assign the right joystick of a controller to the
 * movement of an entity in a game
 * 
 * @author Larissa
 *
 */

public class ControllerInputPopup {
	private String code;
	private int controllerNum;
	private int numControllers;
	private String inputDisplayName;

	public ControllerInputPopup(int number) {
		numControllers = number;
		Stage dialog = new Stage();
		VBox dialogVbox = new VBox(35);
		dialogVbox.getStylesheets().add("defaultStyle.css");
		dialogVbox.getStyleClass().add("vbox");
		dialogVbox.getChildren()
				.add(AuthoringFactory.makeLabel(MenuLanguage.getInstance().getValue("SelectControllerInput")));
		dialogVbox.setAlignment(Pos.CENTER);
		Scene dialogScene = new Scene(dialogVbox, 260, 600);
		Button close = AuthoringFactory.makeButton(MenuLanguage.getInstance().getValue("SetMapping"),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						dialog.close();
					}
				});
		dialogVbox.getChildren().addAll(setControllerSelectionButtons(), setInputSelectionButtons(), close);
		dialog.setScene(dialogScene);
		dialog.showAndWait();
	}
	
	/**
	 * purpose: returns the controller code that the user has selected to apply to the 
	 * current ability
	 * assumptions: assumes that the user has selected a code and that code is not null
	 * @return the chosen controller code
	 */
	public String getControllerCode() {
		return code;
	}

	/**
	 * purpose: returns the number of controllers that the user has selected to use
	 * assumptions: assumes that the user has selected a number of controllers and that 
	 * controllerNum is not null
	 * @return the chosen number of controllers
	 */
	public int getControllerNumber() {
		return controllerNum;
	}

	/**
	 * purpose: returns the display name for the input that the user has selected to 
	 * apply to the current ability
	 * assumptions: assumes that the user has selected a code and that inputDisplayName
	 * is not null
	 * @return the chosen code's display name
	 */
	public String getInputDisplayName() {
		return inputDisplayName;
	}

	private MenuBar setControllerSelectionButtons() {
		Menu buttonMenu = new Menu(MenuLanguage.getInstance().getValue("SelectController"));
		for (int i = 1; i < numControllers + 1; i++) {
			buttonMenu.getItems().add(generateControllerMenuItem(i));
			buttonMenu.setText(MenuLanguage.getInstance().getValue("SelectControllerController") + i);
			
		}
		MenuBar buttonMenuBar = new MenuBar(buttonMenu);
		buttonMenu.setId("menu2");

		return buttonMenuBar;
	}
	
	private MenuItem generateControllerMenuItem(int controller){
		MenuItem item = new MenuItem(MenuLanguage.getInstance().getValue("Controller") + controller);
		item.getStyleClass().add("menu-item");
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controllerNum = controller;
			}
		});
		return item;
	}

	private MenuBar setInputSelectionButtons() {
		Menu buttonMenu = new Menu(MenuLanguage.getInstance().getValue("SelectInput"));
		Properties codes = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(
					System.getProperty("user.dir") + "/src/resources/controller/xbox_mappings.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			codes.load(input);
			for (String key : codes.stringPropertyNames()) {
				buttonMenu.setText(MenuLanguage.getInstance().getValue("SelectInput") + codes.getProperty(key));
				buttonMenu.getItems().add(generateCodeMenuItem(key, codes));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		buttonMenu.setId("menu2");
		MenuBar buttonMenuBar = new MenuBar(buttonMenu);
		return buttonMenuBar;
	}
	
	private MenuItem generateCodeMenuItem(String key, Properties codes){
		MenuItem item = new MenuItem(codes.getProperty(key));
		item.getStyleClass().add("menu-item");
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				code = key;
				inputDisplayName = codes.getProperty(key);
			}
		});
		return item;
	}
}
