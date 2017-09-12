package gameauthoring.presets;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import gameauthoring.presets.enums.Genre;
import gameauthoring.presets.interfaces.AbstractPresetView;
import gameauthoring.presets.interfaces.GenrePresetViewInterface;
import gameengine.GameWorld;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

/**
 * @author DavidYoon
 *
 */
public class GenrePresetView extends AbstractPresetView implements GenrePresetViewInterface {
	
	public static final String GENRE_PRESET_FILEPATH = "resources/images/genrePreset/";
	private static final double VIEWHEIGHT = 700;
	private static final double VIEWWIDTH = 700;
	private TilePane genreImages;
	private File folder;
	private Genre genre;
	private GameWorld world;
	private Genre gameGenre;
	private List<Genre> genreValues;
	private ToggleGroup genreButtonGroup;

	public GenrePresetView(GameWorld world){
		this.world = world;
		genreButtonGroup = new ToggleGroup();
		display();
	}

	@Override
	public void loadGenreButtons() {
		folder = new File(SYSTEM_FILEPATH + GENRE_PRESET_FILEPATH);
		File[] listOfFiles = folder.listFiles();
		getGenres(listOfFiles);
	}


	private void getGenres(File[] listOfFiles) {
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && !listOfFiles[i].isHidden()) {
				createButtons(listOfFiles[i]);
			}
		}
	}


	private void createButtons(File image) {
		String genreName = FilenameUtils.removeExtension(image.getName());
		ImageView genreImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(GENRE_PRESET_FILEPATH + image.getName())));
		genreImage.setFitHeight(VIEWHEIGHT/4);
		genreImage.setFitWidth(VIEWWIDTH/4);
		genreImage.setPreserveRatio(true);
		ToggleButton genreButton = new ToggleButton(genreName, genreImage);
		genreButton.getStyleClass().add("toggle-button");
		genreButton.setMinSize(VIEWWIDTH/5, VIEWWIDTH/5);
		genreValues = new ArrayList<Genre>(EnumSet.allOf(Genre.class));
		
		for (int i = 0; i < genreValues.size(); i++){
			if (genreValues.get(i).name().equalsIgnoreCase(genreName)){
				gameGenre = genreValues.get(i);
			}
		}
		
		genreButton.setOnAction(e -> gameGenre.createLevel(world));
		genreButtonGroup.getToggles().add(genreButton);
		genreImages.getChildren().add(genreButton);
	}

	@Override
	public void display() {
		centerPane = new BorderPane();

		Label direction = new Label("Choose Your Game Genre");

		getCenterPane().setTop(direction);
		getCenterPane().setCenter(displayGenre());
	}

	@Override
	public Pane displayGenre() {
		genreImages = new TilePane();

		loadGenreButtons();

		return genreImages;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

}
