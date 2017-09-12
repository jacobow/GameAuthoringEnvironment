package gameauthoring.components.selectors.componentselectors.abilityselector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import util.reflection.*;
import gameauthoring.components.entitycreation.EntityCreator;
import gameauthoring.components.selectors.abstractselectors.AbilitySelectorInterface;
import gameauthoring.components.selectors.componentselectors.MenuComparator;
import gameauthoring.components.selectors.componentselectors.attributeselector.AttributeSelectionPopup;
import gameengine.GameWorld;
import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.AbilityInterface;
import gameengine.systems.abilities.AssignAttribute;
import gameengine.utilities.HandHeld;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;


/**
 * Purpose: this class allows the user to select which abilities they want to add to an 
 * entity and set key or controller inputs with which to access them
 * Dependencies: the class is dependent on the the GameWorld, AbilityMapping, EntityCreator, 
 * AttributeSelectionPopup, and AbilityTable classes
 * Example Use: used to allow the user to select a MoveForward ability from a list of other attribtues
 * and then set it's input method
 * 
 * @author Larissa Cox and Michael Seaberg 
 *
 */


public class AbilitySelector implements AbilitySelectorInterface{
	private Set<Class<? extends AbilityInterface>> Abilities;
	private List<AbilityInterface> myNewEntityAbilities;
	private List<KeyCode> myKeyCodes;
	private EntityInterface myEntity;
	private GameWorld myWorld;
	private ObservableList<AbilityMapping> myMappings;
	private VBox myContentArea;
	private EntityCreator myChildCreator;
	private AttributeSelectionPopup myAttributeSelector;
	private TableView<AbilityMapping> myMappingTable;
	private List<HandHeld> myHandHelds;
	private int myNumControllers;

	public AbilitySelector(EntityInterface entity, GameWorld world) {
		myEntity = entity;
		myWorld = world;
		myNumControllers = 1;
		myHandHelds = new ArrayList<HandHeld>();
		for (int i = 0; i < myNumControllers; i++) {
			myHandHelds.add(new HandHeld());
		}
		initialize();
	}
	
	/**
	 * purpose: provides access to the VBox that contains the selector content
	 * @return the selectors main VBox
	 */
	public VBox getMyContentArea() {
		return myContentArea;
	}

	/**
	 * purpose: initializes the selector and the corresponding ability items
	 * for selection by the user
	 */
	public void initialize() {
		myNewEntityAbilities = new ArrayList<AbilityInterface>();
		myKeyCodes = new ArrayList<KeyCode>();
		myChildCreator = new EntityCreator();
		createAbilitySection();
	}
	
	/**
	 * purpose: creates a listener that helps the pop-up to be responsive and resizable
	 * @return the change listener that acts when the pop-up is resized
	 */
	public ChangeListener<Number> getWidthListener() {
		return new ChangeListener<Number>() {

			@Override

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				myContentArea.setTranslateX(45);
				myMappingTable.maxWidthProperty().set((double) newValue - 100);
				myContentArea.maxWidthProperty().set((double) newValue - 100);
			};
		};
	}
	
	/**
	 * purpose: provides access to the abilities that the use has selected to be added to
	 * their entity
	 * @return the list of selected abilities
	 */
	public List<AbilityInterface> getAbilities() {
		return myNewEntityAbilities;
	}
	
	/**
	 * purpose: provides access to the key codes that the use has selected to correspond to
	 * the abilities that they have added to their entity
	 * @return the list of selected key codes in the same order as the abilities
	 */
	public List<KeyCode> getKeyCodes() {
		return myKeyCodes;
	}

	/**
	 * purpose: adds the HandHelds that contain information about any controller inputs '
	 * mapped to abilities by the user to the world for use in the engine
	 * assumptions: assumes that myHandHelds has been initialized
	 */
	public void getControllerCodes() {
		for (HandHeld controller : myHandHelds) {
			myWorld.addControllerToAbilityMap(controller);
		}
	}
	
	/**
	 * purpose: compiles all of the users selected abilities from their AbiltiyMappings into
	 * the key codes list, abilities list, and handheld objects for use by outside classes 
	 */
	public void compileAbilities() {
		Iterator<AbilityMapping> mappingsIterator = myMappings.iterator();
		while (mappingsIterator.hasNext()) {
			AbilityMapping currentMapping = mappingsIterator.next();
			if (currentMapping.getMyKeyCode() != null) {
				myNewEntityAbilities.add(currentMapping.getMyAbility());
				myKeyCodes.add(currentMapping.getMyKeyCode());
			} else if (currentMapping.getMyControllerCode() != null) {
				myHandHelds.get(currentMapping.getMyControllerNumber() - 1)
						.addAbility(currentMapping.getControllerName(), currentMapping.getMyAbility());
				myEntity.addAbility(currentMapping.getMyAbility(), currentMapping.getControllerName(),
						currentMapping.getMyControllerNumber());
			}
		}
	}
	
	/**
	 * purpose: allows outside classes to update the entity that the selector adds abilities to
	 * @param entity the new entity to be added to 
	 */
	public void setMyEntity(EntityInterface entity) {
		myEntity = entity;
	}

	/**
	 * purpose: to create and provide access to the ability selection VBox
	 * for use in other classes
	 * @return the VBox containing the necessary components for ability selection
	 */
	public VBox getAbilitySection(){
		return myContentArea;
	}

	private VBox createAbilitySection() {
		myContentArea = new VBox();
		myContentArea.setMaxHeight(350);
		myContentArea.setTranslateX(50);
		myContentArea.setMaxWidth(450);
		myContentArea.getStyleClass().add("vbox");
		AbilityTable abilityTable = new AbilityTable(myContentArea, myNumControllers);
		abilityTable.createAbilitySection();
		myMappingTable = abilityTable.getMappingTable();
		myMappings = abilityTable.getMappings();
		Menu addAbilityMenu = new Menu("Set Abilities...");
		Menu abilitiesSub = new Menu("Add Ability");
		abilitiesSub.getItems().addAll(createMenuItems());
		Menu setAbilityToClick = new Menu("Link Click To Ability");
		setAbilityToClick.getItems().addAll(createRadioMenuItems());
		addAbilityMenu.getItems().addAll(abilitiesSub, setAbilityToClick);
		MenuBar newMenuBar = new MenuBar(addAbilityMenu);
		addAbilityMenu.setId("menu3");
		addAbilityMenu.getStyleClass().add("menu-item");
		
		myContentArea.getChildren().addAll(newMenuBar, myMappingTable);
		initializeAbilities();
		return myContentArea;
	}
	
	private void initializeAbilities(){
		for (AbilityInterface ability : myEntity.getAbilitiesList().keySet()) {
			AbilityMapping mapping = new AbilityMapping(ability);
			mapping.setMyKeyCode(myEntity.getAbilitiesList().get(ability));
			myMappings.add(mapping);

		}
	}

	private List<RadioMenuItem> createRadioMenuItems() {
		ArrayList<RadioMenuItem> myClickAbilities = new ArrayList<RadioMenuItem>(createRadioItems());
		ToggleGroup radioGroup = new ToggleGroup();
		myClickAbilities.forEach(item -> {
			item.setToggleGroup(radioGroup);
		});

		return myClickAbilities;
	}

	private List<MenuItem> createMenuItems() {
		Reflections ref = new Reflections();
		Abilities = ref.getSubTypesOf(AbilityInterface.class);
		ArrayList<MenuItem> AbilitysList = new ArrayList<MenuItem>();
		for (Class<? extends AbilityInterface> possibleAbility : Abilities) {
			if (!possibleAbility.getName().contains("Interface")) {
				MenuItem attItem = new MenuItem(possibleAbility.getSimpleName());
				attItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						AbilityInterface ability = abilityHandler(possibleAbility);
						myMappings.add(new AbilityMapping(ability));
					}
				});
				attItem.setId(possibleAbility.getName());
				AbilitysList.add(attItem);
			}
		}
		Collections.sort(AbilitysList, MenuComparator.menuComparator);
		return AbilitysList;
	}

	private List<RadioMenuItem> createRadioItems() {
		Reflections ref = new Reflections();
		Abilities = ref.getSubTypesOf(AbilityInterface.class);
		ArrayList<RadioMenuItem> AbilitysList = new ArrayList<RadioMenuItem>();
		for (Class<? extends AbilityInterface> possibleAbility : Abilities) {
			if (!possibleAbility.getName().contains("Interface")) {
				RadioMenuItem attItem = new RadioMenuItem(possibleAbility.getSimpleName());
				attItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						AbilityInterface ability = abilityHandler(possibleAbility);
						myWorld.setMouseClickAbility(ability);
					}
				});
				attItem.setId(possibleAbility.getName());
				AbilitysList.add(attItem);
			}
		}
		Collections.sort(AbilitysList, MenuComparator.menuComparator);
		return AbilitysList;
	}
	
	private AbilityInterface abilityHandler(Class<? extends AbilityInterface> possibleAbility){
		AbilityInterface ability = null;
		if (possibleAbility.getName().contains("CreateEntity")) {
			myChildCreator = new EntityCreator();
			myChildCreator.initialize();
			ability = (AbilityInterface) Reflection.createInstance(possibleAbility.getName(),
					myChildCreator.getEntity(), myEntity, myWorld);
		} else {
			ability = (AbilityInterface) Reflection.createInstance(possibleAbility.getName(), myEntity);
			if (possibleAbility.getName().contains("AssignAttribute")) {
				myAttributeSelector = new AttributeSelectionPopup();
				myAttributeSelector.initialize();
				((AssignAttribute) ability).setAttributeToAssign(myAttributeSelector.getAttribute());
			}
		}
		return ability;
	}

}
