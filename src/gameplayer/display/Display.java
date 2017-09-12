package gameplayer.display;

import application.MainController;
import gamedata.data.GameData;
import gameengine.interfaces.ExternalEngineInterface;
import gameplayer.display.gamechoiceandsaved.GameChooser;
import gameplayer.filereading.FindDefaultGames;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import junit.framework.Test;

public class Display implements DisplayInterface{

    private ExternalEngineInterface currentEngine;
    private Scene myDisplayScene;
    private Pane globPane;
    private GameChooser myChooser;
    private boolean pause = false;
    private Stage s;
    private static final double SCREEN_DIM = 500;
    private static final String CSS = "myStyle.css";
    @Override
    public Scene getScene(){
        return myDisplayScene;
    }
    @Override public void setScene(Scene newScene){
        s.setScene(newScene);

    }
    @Override
    public Scene init(EventHandler backEvent, MainController mainController, Stage s) {
    	 

        this.s = s;
        globPane = new Pane();
        FindDefaultGames myGameInfo = new FindDefaultGames();
        Iterable<GameData> games = myGameInfo.findDefaultGames();
        DisplayInterface toPass = this;
        myDisplayScene = new Scene(globPane, SCREEN_DIM, SCREEN_DIM);
        myChooser = new GameChooser(games,  backEvent, mainController, toPass);
        globPane.getChildren().add(myChooser.getNode());
        myDisplayScene.getStylesheets().add(CSS);
        return myDisplayScene;
    }
    
    @Override public void setEngine(ExternalEngineInterface currentChoice){
        pause = true;
        this.currentEngine = currentChoice;
    }
    
    @Override
    public void step(double elapsedTime) {
        if(pause){
            currentEngine.update(elapsedTime);
        }
    }

    @Override
    public Stage getStage(){
        return s;
    }
    
    @Override
    public void togglePause(){
        pause = !pause;
    }


}