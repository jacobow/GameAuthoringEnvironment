package gameplayer.display.gameplay.visualstats;

import configuration.MenuLanguage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Template for the screen that pops up after completing a level
 * @author Brian
 * @author Sam
 *
 */
public abstract class FinishLevelScreen {
    
    private static final double DIMENSION = 500;
    private static final String CSS_ID = "end";
    private static final String CSS_FONT = "-fx-font-size: 70";
    
    public VBox endBox; 
    Label myMessage;
    
    /**
     * 
     * @param message - message to be displayed after completing the level
     * @param restartGame - action to be taken upon pressing the restart button
     * @param home - action to be taken upon pressing the home button
     */
    public FinishLevelScreen(String message, EventHandler<ActionEvent> restartGame, EventHandler<ActionEvent> home){
        endBox = new VBox();
        endBox.setPrefHeight(DIMENSION);
        endBox.setPrefWidth(DIMENSION);
        endBox.setId(CSS_ID);
        endBox.setAlignment(Pos.CENTER);
        myMessage = new Label(message);
        myMessage.setStyle(CSS_FONT);
        Button newGame = new Button(MenuLanguage.getInstance().getValue("NewGame"));
        newGame.setOnAction(restartGame);
        Button myHome = new Button(MenuLanguage.getInstance().getValue("Menu"));
        myHome.setOnAction(home);
        newGame.setFont(Font.font(30));
        newGame.setPrefWidth(1000);
        endBox.getChildren().addAll(myMessage, myHome, newGame);
    }
    
    /**
     * @return the VBox with the Label/buttons to display after completing a level
     */
    public Node getNode() {
        return this.endBox;
    }

}
