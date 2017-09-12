package gameauthoring.components.selectors.componentselectors.attributeselector.enums;

import java.lang.reflect.Method;
import java.util.Map;

import gameauthoring.AuthoringFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import util.reflection.Reflection;

/**
 * Purpose: this enum allows an outside class to determine which type of userinput the class
 * needs, to create an appropriate way for the user to input this information, and to call an 
 * appropriate getter method on a target object in order to get the current value of that 
 * property for display to the user
 * Assumptions: assumes that there is an existing method called getmethodName in the targetObject's
 * API
 * Dependencies: the class is dependent on the the targetObject
 * Example Use: used to create a TextField with which the user can input their desired MaxEnergy for 
 * an energy attribute
 * 
 * @author Larissa Cox
 *
 */

public enum InputCreator {

	/**
	 * contains methods to create a node for input that can be typed by the user in 
	 * String form	 
	*/
	TEXTINPUT(){
		public Node createInput(Object targetObject, Method method, Map<Method, Object> inputs){
			String defaultValue = Reflection.callMethod(targetObject, "get" + method.getName().substring(3)).toString();
			HBox row = new HBox(6);
			row.setAlignment(Pos.CENTER);
			row.getStyleClass().add("hBox");
			TextField userInput = AuthoringFactory.makeTextField(defaultValue);
			inputs.put(method, userInput);
			row.getChildren().addAll(AuthoringFactory.makeLabel(method.getName() + ":"), userInput);
			return row;
		}
	},

	/**
	 * contains methods to create a node for input that can be typed by the user in 
	 * boolean form	 
	*/
	BOOLEAN(){
		public Node createInput(Object targetObject, Method method, Map<Method, Object> inputs){
			CheckBox check = new CheckBox(method.getName().substring(3));
			if ((Boolean) Reflection.callMethod(targetObject, "get" + method.getName().substring(3))){
				check.setSelected(true);
			}
			inputs.put(method, check);
			return check;
		}
	};
	
	/**
	 * purpose: to reflectively call the getter method method in the class targetObject
	 * in order to create an input whose default value reflects the current value of 
	 * targetObject for the property the getter gets
	 * assumptions: assumes that the targetObject has a getter method named method
	 * @param targetObject the object the method is being called on
	 * @param method the getter method being called
	 * @param inputs a map in which Method, user input pairs can be stored for later
	 * parsing
	 * @return the Node to be displayed so that the user can add user input to it
	 */
	public abstract Node createInput(Object targetObject, Method method, Map<Method, Object> inputs);
	
}
