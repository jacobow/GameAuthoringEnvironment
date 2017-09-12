package gameauthoring.presets;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;

import org.apache.commons.io.FilenameUtils;

import gameauthoring.presets.enums.Character;
import gameauthoring.presets.interfaces.AbstractPresetView;
import gameauthoring.presets.interfaces.GeneralPresetViewInterface;
import gameengine.GameWorld;
import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.entities.Entity;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author DavidYoon
 *
 */
public class CharacterPresetView extends AbstractPresetView implements GeneralPresetViewInterface {

	private static final String CHARACTER_IMAGE_PATH = "resources" +File.separator +  "images" + File.separator + "Characters" + File.separator;
	private static final String SLEAGUE_FILEPATH = "resources" + File.separator + "images" + File.separator + "sleague"  + File.separator ;
	private static final double VIEWHEIGHT = 700;
	private static final double VIEWWIDTH = 700;

	private FadeTransition fadeIn = new FadeTransition(
			Duration.millis(3000)
			);
	private TilePane charactersImages;
	private File folder;
	private GameWorld world;
	private ToggleButton characterButton;
	private ArrayList<Character> characterValues;
	private Character character;


	public CharacterPresetView(GameWorld world){
		display();
		this.world = world;
	}

	@Override
	public void display() {
		centerPane = new BorderPane();

		Label step = new Label("Step 2.");
		Label name = new Label("Choose Your Characters :");

		centerPane.setTop(new VBox(step, name));
		centerPane.setCenter(displayCharacter());
	}

	private void setCharactersImages() {
		folder = new File(SYSTEM_FILEPATH + SLEAGUE_FILEPATH);
		File[] fileArray = folder.listFiles();

		if (folder.getAbsolutePath().endsWith(CHARACTER_IMAGE_PATH)){
			Arrays.sort(fileArray, new Comparator<File>() {
				public int compare(File image1, File image2) {
					try {
						int i1 = Integer.parseInt(FilenameUtils.removeExtension(image1.getName()));
						int i2 = Integer.parseInt(FilenameUtils.removeExtension(image2.getName()));
						return i1 - i2;
					} catch(NumberFormatException e) {
						throw new AssertionError(e);
					}
				}
			});
		}

		getMainCharacters(fileArray);
	}


	private void getMainCharacters(File[] listOfFiles) {
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && !listOfFiles[i].isHidden()) {
				createButtons(listOfFiles[i]);	
			}
		}
	}

	private void createButtons(File image) {
		String characterName = FilenameUtils.removeExtension(image.getName());
		ImageView characterImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(SLEAGUE_FILEPATH + image.getName())));
		characterImage.setFitHeight(VIEWHEIGHT/8);
		characterImage.setFitWidth(VIEWWIDTH/8);
		characterImage.setPreserveRatio(true);
		characterButton = new ToggleButton("", characterImage);
		charactersImages.getChildren().add(characterButton);

		characterButton.setMinSize(VIEWWIDTH/5, VIEWWIDTH/5);
		characterValues = new ArrayList<Character>(EnumSet.allOf(Character.class));

		for (int i = 0; i < characterValues.size(); i++){
			if (characterName.toLowerCase().startsWith(characterValues.get(i).name().toLowerCase())){
				character = characterValues.get(i);
			}
		}

		characterButton.setOnAction(e -> {
			System.out.println(image.getName());
			character.addEntity(world, new Entity(characterName, new ArrayList<AttributeInterface>()), image.getName());
			notifyUser();
		});

	}

	private void notifyUser() {
		StackPane notifyUser = new StackPane();
		Rectangle rect = new Rectangle(200, 100, Color.ALICEBLUE);
		rect.setArcHeight(20);
		rect.setArcWidth(20);
		Text userNotification = new Text("Entity Added"); 
		notifyUser.getChildren().addAll(rect, userNotification);
		initialize(notifyUser);
		if (!notifyUser.isVisible()) {
			notifyUser.setVisible(true);
			fadeIn.playFromStart();
		}
	}

	public void initialize(StackPane vbox) {
		fadeIn.setNode(vbox);

		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);
		fadeIn.setCycleCount(1);
		fadeIn.setAutoReverse(false);

	}

	public ScrollPane displayCharacter() {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.getStyleClass().add("pane");
		charactersImages = new TilePane();
		charactersImages.getStyleClass().add("tilepane");
		setCharactersImages();
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(charactersImages);
		return scrollPane;
	}

	public GameWorld getWorld() {
		return world;
	}

	public void setWorld(GameWorld world) {
		this.world = world;
	}
}
