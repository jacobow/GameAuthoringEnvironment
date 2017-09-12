package gameplayer.display.gameplay.visualstats;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Display the pop-up screen that occurs after beating the final level of a game
 * @author Brian
 * @author Sam
 *
 */
public class EndScreen extends FinishLevelScreen {


    public EndScreen(String message, EventHandler<ActionEvent> restartGame, EventHandler<ActionEvent> home) {
        super(message, restartGame, home);

    }

}
