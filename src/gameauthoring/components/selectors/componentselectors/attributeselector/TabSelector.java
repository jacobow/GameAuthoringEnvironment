// This entire file is part of my masterpiece.
// Larissa Cox


package gameauthoring.components.selectors.componentselectors.attributeselector;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import configuration.MenuLanguage;
import gameauthoring.AuthoringFactory;
import gameauthoring.components.selectors.abstractselectors.AbstractSelector;
import gameauthoring.components.selectors.componentselectors.attributeselector.enums.InputCreator;
import gameauthoring.components.selectors.componentselectors.attributeselector.enums.InputHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Purpose: this class allows the EntityCreator class to create tabs for every attribute that 
 * is added to it's current entity and to fill them with visual Nodes that allow the user to 
 * input info about the properties of those attributes
 * Assumptions: assumes that the object passed in has getter and setter functions and that 
 * each getter function has a corresponding setter function
 * Dependencies: the class is dependent on the InputHandler and InputCreator enums and the 
 * type of the myObject object
 * Example Use: used to create a new Energy attribute tab and fill it with a TextField that 
 * allows the user to input a numeric value
 * 
 * @author Larissa Cox, Michael Seaberg
 *
 */

public class TabSelector extends AbstractSelector {
	private Object myObject;
	private Map<Method, Object> myInputs;
	private AnchorPane myArea;
	private Tab myTab;
	private VBox myVBox;
	private List<InputCreator> myInputCreators;
	private List<InputHandler> myInputHandlers;
	
	public TabSelector() {
		super("Attribute Selection Tabs");
		myInputs = new HashMap<Method, Object>();
	}

	/**
	 * purpose: initializes the TabSelector with an object whose methods will
	 * be the inputs in the tab
	 * assumptions: assumes that the reflectionObject has matching getter and
	 * setter methods
	 * @param reflectionObject the object whose getter methods will be reflectively
	 * determined, displayed, and whose corresponding setters will be called
	 */
	public void initialize(Object reflectionObject) {
		myInputHandlers = new ArrayList<InputHandler>(EnumSet.allOf(InputHandler.class));
		myInputCreators = new ArrayList<InputCreator>(EnumSet.allOf(InputCreator.class));
		myObject = reflectionObject;
		myTab = new Tab(reflectionObject.getClass().getSimpleName());
		myTab.setId("specialTab");
		myVBox = new VBox();
		myArea = new AnchorPane();
		myArea.setPrefSize(200, 350);
		createSelectables(myObject);
		myArea.getChildren().add(myVBox);
		myVBox.setMinSize(300, 75);
		myVBox.setAlignment(Pos.CENTER);
		myVBox.setId("entitycreatorvbox");
		myTab.setContent(myVBox);
	}

	/**
	 * purpose: returns the tab of the selector
	 * assumptions: assumes that myTab has been inititalized
	 * @return the TabSelector's tab
	 */
	public Tab getTab() {
		return myTab;
	}
	
	/**
	 * purpose: returns the VBox display of the selector
	 * assumptions: assumes that myVBox has been inititalized
	 * @return the TabSelector's VBox
	 */
	public VBox getVBox(){
		return myVBox;
	}
	
	/**
	 * purpose: returns the object whose methods are being displayed
	 * in the selector
	 * assumptions: assumes that myObject has been initialized
	 * @return the TabSelector's reflective target object
	 */
	public Object getObject(){
		return myObject;
	}
	
	/**
	 * purpose: adds a method user input pair to the myInputs 
	 * map
	 * @param method the method being stored
	 * @param input the input object that will hold the user input for 
	 * the method
	 */
	public void addInput(Method method, Object input){
		myInputs.put(method, input);
	}
	
	/**
	 * purpose: prepares the selector to be closed by handling all its inputs
	 * assumptions: assumes that there is a member of the InputHandlers enum that 
	 * corresponds to the parameter type of each method in question
	 */
	public void closeSelector() {
		for (Method method : myInputs.keySet()) {
			Parameter parameter = method.getParameters()[0];
			for (InputHandler handler: myInputHandlers){
				if (handler.name().toLowerCase().equals(getSimpleTypeName(parameter))){
					handler.handleInput(method.getName(), myInputs.get(method), myObject);
					return;
				}
			}
		}
	}
	

	@Override
	protected Collection<Object> getReflectables(Object reflectionObject) {
		Method[] possibleSetters = myObject.getClass().getMethods();
		Collection<Object> setters = new ArrayList<Object>();
		for (Method possible : possibleSetters) {
			if (possible.getName().substring(0,3).equals("set")) {
				setters.add(possible);
			}
		}
		return setters;
	}

	@Override
	protected void wrapObject(Object content) {
		if(content == null){
			myVBox.getChildren().add(AuthoringFactory.makeLabel(MenuLanguage.getInstance().getValue("NoProperties")));
		}
		else{
			Parameter parameter = ((Method) content).getParameters()[0];
			for (InputCreator creator: myInputCreators){
				if (creator.name().toLowerCase().equals(getSimpleTypeName(parameter))){
					myVBox.getChildren().add(creator.createInput(myObject, (Method) content, myInputs));
					return;
				}
			}
			myVBox.getChildren().add(InputCreator.TEXTINPUT.createInput(myObject, (Method) content, myInputs)); 
		}
	}

	private String getSimpleTypeName(Parameter parameter) {
		return parameter.getType().getName().split("\\.")[parameter.getType().getName().split("\\.").length - 1].toLowerCase();
	}

}
