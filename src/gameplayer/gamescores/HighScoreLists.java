package gameplayer.gamescores;

import java.util.ArrayList;
import java.util.List;

import configuration.MenuLanguage;
import gameengine.utilities.Pair;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HighScoreLists{
    
        private String name;
        private Pair<Double,Double> score;
        private List<Pair<Double,Double>> myScores;
        
        public HighScoreLists (String name){
            this.name = name;
            this.score = new Pair<>(0.0,0.0);
            myScores = new ArrayList<>();
        }
//        public HighScoreLists(){
//                Label highScores = new Label(MenuLanguage.getInstance().getValue("HighScore"));
//                //myBox.getChildren().add(highScores);
//        }
//        public void addScore(String scoreType, Double score){
//                Label newScore = new Label(rank + scoreType + ": " + score);
//                this.score = score;
//                //myBox.getChildren().add(newScore);
//        }
        
        public void addScore(Double first, Double second) {
        	myScores.add(new Pair<>(first, second));
        }
        
        public Pair<Double, Double> getScore(){
            return this.score;
        }
        public List<Pair<Double, Double>> getMyScores() {
        	return myScores;
        }
        
//        public Node getNode(){
//                return myBox;
//        }
}

