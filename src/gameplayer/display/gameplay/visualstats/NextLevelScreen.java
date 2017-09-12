package gameplayer.display.gameplay.visualstats;

import configuration.MenuLanguage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * Display the screen that occurs when you beat a level and there are more levels that follow
 * @author Brian
 *
 */
public class NextLevelScreen extends FinishLevelScreen  {

    public NextLevelScreen(String message, EventHandler<ActionEvent> restartGame, 
                           EventHandler<ActionEvent> goToNextLevel, EventHandler<ActionEvent> home) {
        super(message, restartGame, home);
        Button level = new Button(MenuLanguage.getInstance().getValue("NextLevel"));
        level.setOnAction(goToNextLevel);
        level.setFont(Font.font(40));
        level.setPrefWidth(500);

        endBox.getChildren().add(level);

    }


}

