package gameplayer.display.choosecharacter;

import java.util.Map;

import configuration.MenuLanguage;
import gameengine.attributes.Spacial;
import gameengine.entities.EntityInterface;
import gameplayer.interfaces.NodeStartInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * 
 * @author SamFurlong
 *
 */
public class ChooseCharacter implements NodeStartInterface{
	private VBox myBox;
	private BooleanProperty chosen;
	private String ID;
	private String chosenName;
	private ToggleButton chooseCharacter;
	private static final double INSETS = 20;
	public ChooseCharacter(EntityInterface character){
		myBox = new VBox();
		chosen = new SimpleBooleanProperty();;
		ID = character.getID();
		chosenName = character.getID();
		if(character.containsAttribute(Spacial.class)){
			ImageView icon = new ImageView(character.getAttribute(Spacial.class).retrieveDisplay());
			icon.setFitHeight(100);
			icon.setFitWidth(100);
			myBox.getChildren().addAll(icon);

		}
		createCharacterToggle();
		enterName();
		

	}

	private void createCharacterToggle(){
		chooseCharacter = new ToggleButton(ID);
		chosen = chooseCharacter.selectedProperty();
		myBox.getChildren().add(chooseCharacter);

	}
	private void enterName(){
		TextField enterTeam = new TextField();
		enterTeam.setPromptText(MenuLanguage.getInstance().getValue("ChooseCharacterName"));
		enterTeam.textProperty().addListener((x,y,z)->{
			chooseCharacter.setText(enterTeam.getText());
			chosenName = enterTeam.getText();
		});
		myBox.getChildren().add(enterTeam);
		myBox.setPadding(new Insets(20));

	}
	
	@Override
	public String getChosenName(){
		return chosenName;
	}
	@Override
	public Node getNode(){
		return myBox;
	}
	

	@Override
	public BooleanProperty startProperty() {
		// TODO Auto-generated method stub
		return chosen;
	}

	@Override
	public Map<EntityInterface, String> getChosenNameMap() {
		// TODO Auto-generated method stub
		return null;
	}
}
