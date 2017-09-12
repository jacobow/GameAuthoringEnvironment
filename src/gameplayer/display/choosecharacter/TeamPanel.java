package gameplayer.display.choosecharacter;
import java.util.HashMap;
import java.util.Map;

import configuration.MenuLanguage;
import gameengine.entities.EntityInterface;
import gameplayer.interfaces.NodeStartInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
/**
 * 
 * @author SamFurlong
 *
 */
public class TeamPanel implements NodeStartInterface{
	private VBox myBox;
	private HashMap<EntityInterface, String> entityToChosenName;
	private ToggleButton startButton;
	private BooleanProperty startProp;
	private Label myTeam;
	private String teamName;
	private static final double INSETS =20;
	private Iterable<EntityInterface> entitiesOnTeam;
	/**
	 * 
	 * @param team
	 * @param entitiesOnTeam
	 */
	public TeamPanel(String team, Iterable<EntityInterface> entitiesOnTeam){
		entityToChosenName= new HashMap<EntityInterface, String>();
		myBox = new VBox();
		startButton = new ToggleButton(MenuLanguage.getInstance().getValue("Start"));
		startProp = new SimpleBooleanProperty();
		this.entitiesOnTeam = entitiesOnTeam;
		teamName = team;
		myTeam = new Label(team);
		myBox.getChildren().add(myTeam);
		addCharacters();
		myBox.getChildren().add(startButton);
		addStartListener();
		enterName();
		
	}
	private void addStartListener(){
		startButton.selectedProperty().addListener((x,y,z)->{
		    startProp.set(!entityToChosenName.isEmpty());
		     
		});
	}
	private void addCharacters(){
		for(EntityInterface entity: entitiesOnTeam){
			ChooseCharacter character = new ChooseCharacter(entity);
			myBox.getChildren().add(character.getNode());
			character.startProperty().addListener((x,y,z)->{
			entityToChosenName.put(entity, character.getChosenName());
			startProp.set(startButton.isSelected());
			});
		}
		}
	
	private void enterName(){
		TextField enterTeam = new TextField();
		enterTeam.setPromptText(MenuLanguage.getInstance().getValue("ChooseTeamName"));
		enterTeam.textProperty().addListener((x,y,z)->{
			myTeam.setText(enterTeam.getText());
			teamName = enterTeam.getText();
		});
		myBox.getChildren().add(enterTeam);
		myBox.setPadding(new Insets(INSETS));

	}
	@Override
	public Node getNode(){
		return myBox;
	}
	@Override
	public String getChosenName(){
		return teamName;
	}
	@Override
	public BooleanProperty startProperty(){
		return startProp;
	}
	@Override
	public Map<EntityInterface, String> getChosenNameMap(){
		return entityToChosenName;
	}
	
}

	

