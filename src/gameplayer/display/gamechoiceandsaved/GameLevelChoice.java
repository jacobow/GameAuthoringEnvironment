package gameplayer.display.gamechoiceandsaved;
import configuration.MenuLanguage;
import gamedata.data.GameData;
import gamedata.data.LevelData;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
/**
 * 
 * @author SamFurlong
 *
 */
public class GameLevelChoice implements GameLevelChoiceInterface {
	private VBox chooser;
	private static final double PREFWIDTH = 300;
	private Button start;
	private ImageView gameImageView;
	private String gameLevelName;
	private ReadOnlyBooleanProperty startProperty;
	private static final double CLEAR = .2;
	private static final double OPAC = 1;
	private static final double PADDING = 20;
	private static final String DEFAULT= "blackground.png";
	public GameLevelChoice(GameData game) {
		Image gameImage;
		try{
		gameImage = new Image(this.getClass().getClassLoader().getResourceAsStream(game.getIcon()));
		}
		catch(NullPointerException n){
		gameImage = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT));
		}
		setUpObject(gameImage, game.getName());
	}	
	
	public GameLevelChoice(LevelData level){
		Image gameImage;

		try{
			gameImage = new Image(this.getClass().getClassLoader().getResourceAsStream(level.getBackground()));
			}
			catch(NullPointerException n){
			gameImage = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT));
			}
		setUpObject(gameImage, level.getLevelName());
	}
	private  void setUpObject(Image gameImage, String name){
		try{
			gameImageView = new ImageView(gameImage);
			}
			catch(NullPointerException n){
			gameImageView = new ImageView();
			}
			setSize();
			gameImageView.hoverProperty().addListener((x,y,z)->{		
				if(gameImageView.getOpacity() == OPAC){
					gameImageView.setOpacity(CLEAR); 
				}
				else{
					gameImageView.setOpacity(OPAC); 
				}
			});
			gameLevelName = name;

		setUpStacker();
	}
	private void setSize(){
		gameImageView.setFitWidth(PREFWIDTH);
		gameImageView.setFitHeight(PREFWIDTH);
	}
	private void setUpStacker(){
		StackPane stacker = new StackPane();
		stacker.getChildren().addAll(gameImageView);
		chooser = new VBox();
		chooser.setAlignment(Pos.CENTER);
		setButton();
		chooser.getChildren().addAll(new Label(gameLevelName), gameImageView, start);
	}
	private void setButton(){

		start = new Button(MenuLanguage.getInstance().getValue("Play"));
		start.setPrefWidth(PREFWIDTH);
		startProperty = start.pressedProperty();
	}
	
	@Override
	public void setButtonText(String s){
		start.setText(s);
	}
	@Override
	public Node getNode() {
		chooser.setPadding(new Insets(PADDING));
		return chooser;
	}
	
	public ReadOnlyBooleanProperty getStartProperty() {
		return start.pressedProperty();
	}

	
}