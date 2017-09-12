package gameauthoring.components.selectors.componentselectors.attributeselector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import configuration.MenuLanguage;
import gameauthoring.components.selectors.abstractselectors.AttributeSelectorInterface;
import gameauthoring.components.selectors.componentselectors.MenuComparator;
import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.AttributeInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import util.reflection.Reflection;

/**
 * Purpose: this class allows the user to select which attributes they want to add to an 
 * entity and set values for it's properties
 * Dependencies: the class is dependent on the the TabSelector class
 * Example Use: used to allow the user to select an Energy attribute from a list of other attribtues
 * and then set it's properties
 * 
 * @author Larissa Cox and Michael Seaberg
 *
 */

public class AttributeSelector implements AttributeSelectorInterface{
	private TabPane myTabSelectorPane;
	private List<TabSelector> myTabSelectors;
	private Set<Class <? extends AttributeInterface>> attributes;
	private List<AttributeInterface> myNewEntityAttributes;
	private VBox contentArea;
	
	public AttributeSelector(){
		myTabSelectors = new ArrayList<TabSelector>();
		myNewEntityAttributes = new ArrayList<AttributeInterface>();
		new ArrayList<AttributeInterface>();
	}
	
	/**
	 * purpose: creates the menu of attributes from which the user can choose
	 * which attributes to add to their entity
	 * @param existingAttributes the list of attributes that the entity already has
	 * @return the VBox containing the menu of attributes for display
	 */
	public VBox createAttributeSection(List<AttributeInterface> existingAttributes) {
		contentArea = new VBox();
		contentArea.getStyleClass().add("vbox");
		createAttributeMenu();
		initializeExistingAttributes(existingAttributes);
		return contentArea;
	}
	
	/**
	 * purpose: creates a listener that helps the pop-up to be responsive and resizable
	 * @return the change listener that acts when the pop-up is resized
	 */
	public ChangeListener<Number> getWidthListener(){
		return new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			contentArea.setTranslateX(20);
			contentArea.prefWidthProperty().set((double)newValue - 50);
			
		};};
	}
	
	/**
	 * purpose: provides access to the list of attributes that the user has 
	 * chosen to add to their entity
	 * assumptions: assumes that the list of attributes has not already been compiled
	 * and that myNewEntityAttributes has been initialized
	 * @return the list of added attributes
	 */
	public List<AttributeInterface> getAttributes(){
		compileAttributes();
		return myNewEntityAttributes;
	}
	
	private void createAttributeMenu(){
		Menu addAttributeMenu = new Menu(MenuLanguage.getInstance().getValue("AddAttribute"));
		addAttributeMenu.setId("menu3");
		addAttributeMenu.getStyleClass().add("menu-item");
		addAttributeMenu.getItems().addAll(createMenuItems());
		MenuBar newMenuBar = new MenuBar(addAttributeMenu);
		myTabSelectorPane = new TabPane();
		contentArea.getChildren().addAll(newMenuBar,myTabSelectorPane);
	}
	
	private void initializeExistingAttributes(List<AttributeInterface> existingAttributes){
		for (AttributeInterface attribute: existingAttributes){
			if (!(attribute instanceof Spacial)){
				initializeTab(attribute);
			}
		}
	}
	
	private List<MenuItem> createMenuItems() {
 		Reflections ref = new Reflections();
 		attributes = ref.getSubTypesOf(AttributeInterface.class);
 		ArrayList<MenuItem> attributesList = new ArrayList<MenuItem>();
 		for(Class<? extends AttributeInterface> possibleAttribute : attributes){
 			if(!possibleAttribute.getName().contains("Interface")){
 				attributesList.add(initializeAttribute(possibleAttribute));
 			}
 		}
 		Collections.sort(attributesList, MenuComparator.menuComparator);
 		return attributesList;
 	}
	
	private MenuItem initializeAttribute(Class<? extends AttributeInterface> attribute){
		MenuItem attItem = new MenuItem(attribute.getSimpleName());
		attItem.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				initializeTab(Reflection.createInstance(attribute.getName()));
			}
		});
		attItem.setId(attribute.getName());
		return attItem;
	}
	
	private void initializeTab(Object attribute){
			TabSelector newTabSelector = new TabSelector();
			newTabSelector.initialize(attribute);
			myTabSelectorPane.getTabs().add(newTabSelector.getTab());
			myTabSelectors.add(newTabSelector);
	}
	
	private void compileAttributes() {
		for(TabSelector currentTab :  myTabSelectors){
			currentTab.closeSelector();
			myNewEntityAttributes.add((AttributeInterface)(currentTab).getObject());
		}
	}

}
