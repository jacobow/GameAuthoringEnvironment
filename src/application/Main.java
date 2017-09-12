package application;

import java.awt.Dimension;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main class for Vooga_UDS JavaFX Application
 * @author michaelseaberg
 *
 */
public class Main extends Application{
	public static final Dimension DEFAULT_SIZE = new Dimension(1400, 1200);
	public static final String DEFAULT_RESOURCE_PACKAGE = "";
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

	@Override
	public void start(Stage stage){
		MainController mySLogoMenu = new MainController(stage);
		stage.setScene(mySLogoMenu.getScene());
		stage.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e->mySLogoMenu.step(SECOND_DELAY));

		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public static void main(String[] args) {
		launch(args);
	}
}