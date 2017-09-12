// This entire file is part of my masterpiece.
// Larissa Cox


package gameauthoring.components.selectors.componentselectors.attributeselector.enums;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import util.reflection.Reflection;

/**
 * Purpose: this enum allows an outside class to determine which type of userinput it is 
 * dealing with and to call an appropriate setter method on a target object in order to set
 * the value of the user input to the object
 * Assumptions: assumes that there is an existing method called getmethodName in the targetObject's
 * API
 * Dependencies: the class is dependent on the the targetObject
 * Example Use: used to read the data in from a TextField in which the user has input their 
 * desired MaxEnergy for an energy attribute
 * 
 * @author Larissa Cox
 *
 */

public enum InputHandler {
	
	/**
	 * contains methods to handle an input that needs to be in string form when 
	 * it's method is called reflectively
	 */
	STRING(){
		
		@Override
		public void handleInput(String methodName, Object input, Object targetObject){
			String userInput = ((TextField) input).getText();
			Reflection.callMethod(targetObject, methodName, userInput);
		}
	}, 
	
	/**
	 * handles an input that needs to be in double form when it's method 
	 * is called reflectively
	 */
	DOUBLE(){
		
		@Override
		public void handleInput(String methodName, Object input, Object targetObject) {
			String userInput = ((TextField) input).getText();
			Reflection.callMethod(targetObject, methodName, Double.parseDouble(userInput));
		}
		
	}, 
	
	/**
	 * handles an input that needs to be in boolean form when it's method 
	 * is called reflectively
	 */
	BOOLEAN(){

		@Override
		public void handleInput(String methodName, Object input, Object targetObject) {
			boolean userInput = ((CheckBox) input).isSelected();
			Reflection.callMethod(targetObject, methodName, userInput);
		}
		
	};
	
	/**
	 * purpose: to reflectively call the method methodName in the class targetObject
	 * with the parameter input
	 * assumptions: assumes that the targetObject has a method method name
	 * @param methodName name of the method being called
	 * @param input the parameter for the method
	 * @param targetObject the object the method is being called on 
	 */
	public abstract void handleInput(String methodName, Object input, Object targetObject);
	
}
