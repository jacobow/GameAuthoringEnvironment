package gameauthoring.components.selectors.componentselectors.abilityselector;

import configuration.MenuLanguage;
import gameengine.systems.abilities.AbilityInterface;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.input.KeyCode;

/**
 * Ability Mapping utility class to simplify the TableView implementation for Abilities in the EntityCreator.
 * Class uses SimpleStringProperties to store information so that on parameter change, the TableView can observe and
 * change its information accordingly.
 * abilityName:Stores the name of the ability
 * keyName:Stores the name (letter) of the keyboard mapped to the ability
 * controllerName:Stores the name of the controller
 * myAbility:Stores the actual ability, used to instantiate the abilityMapping
 * myKeyCode:Stores the keyCode object associated with a user keyboard mapping
 * myControllerCode:Stores the string associated with a user controller mapping
 * myControllerNumber:Stores the number of the controller being used to control this ability
 * @author michaelseaberg
 *
 */
public class AbilityMapping {
	private SimpleStringProperty abilityName;
	private SimpleStringProperty keyName;
	private SimpleStringProperty controllerName;
	private AbilityInterface myAbility;
	private KeyCode myKeyCode;
	private String myControllerCode;
	private int myControllerNumber;
	
	public AbilityMapping(AbilityInterface ability){
		myAbility = ability;
		this.setAbilityName(new SimpleStringProperty(myAbility.getClass().getSimpleName()));
		keyName = new SimpleStringProperty("");
		controllerName = new SimpleStringProperty("");
	}

	public String getKeyName() {
		return keyName.get();
	}

	public void setKeyName(String keyName) {
		this.keyName.set(keyName);
	}
	
	public void setControllerName(String name){
		this.controllerName.set(name);
	}

	public String getAbilityName() {
		return abilityName.get();
	}

	public void setAbilityName(SimpleStringProperty abilityName) {
		this.abilityName = abilityName;
	}

	public AbilityInterface getMyAbility() {
		return myAbility;
	}

	public void setMyAbility(AbilityInterface myAbility) {
		this.myAbility = myAbility;
	}

	public KeyCode getMyKeyCode() {
		return myKeyCode;
	}

	public void setMyKeyCode(KeyCode myKeyCode) {
		this.myKeyCode = myKeyCode;
		setKeyName(this.myKeyCode.getName());
	}
	
	public void setMyControllerCode(String controllerCode, String displayName, int controllerNum){
		setMyControllerNumber(controllerNum);
		myControllerCode = controllerCode;
		setControllerName(MenuLanguage.getInstance().getValue("Controller") + " " + controllerNum + " :" + displayName);
	}
	
	public void setMyControllerNumber(int number){
		myControllerNumber = number;
	}
	
	public int getMyControllerNumber(){
		return myControllerNumber;
	}
	
	public String getMyControllerCode(){
		return myControllerCode;
	}
	
	public String getControllerName(){
		return controllerName.get();
	}
	
}
