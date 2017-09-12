// This entire file is part of my masterpiece.
// Larissa Cox


package gameauthoring.components.selectors.abstractselectors;
import java.util.Collection;
import gameauthoring.AuthoringPane;

/**
 * Purpose: this class provides a structure for a selector class that can be used whenever 
 * the programmer is trying to write a class that takes some Object from a list and wraps it
 * in a Node for UI display
 * Dependencies: the class is dependent on the the AuthoringPane class
 * Example Use: used to provide a structure for the AbstractCheckBoxSelector class
 * 
 * @author Larissa Cox 
 *
 */

public abstract class AbstractSelector extends AuthoringPane{
	
	public AbstractSelector(String title) {
		super(title);	
	}
	
	/**
	 * purpose: to create the visual nodes for user selection
	 * @param reflectionObject the object that will be used to 
	 * display the selection options for the user
	 */
	protected void createSelectables(Object reflectionObject){	
		if(getReflectables(reflectionObject).isEmpty())
			wrapObject(null);
		for (Object content: getReflectables(reflectionObject)){
			wrapObject(content);
		}
	}
	
	/**
	 * purpose: to generate the objects that will be used to create the user 
	 * display
	 * @param reflectionObject the object that will be used to reflectively create
	 * the selection objects
	 * @return the collection of objects generated for display
	 */
	protected abstract Collection<Object> getReflectables(Object reflectionObject);
	
	/**
	 * purpose: to process and display the information in the content object
	 * @param content the object to be processed and displayed
	 */
	protected abstract void wrapObject(Object content);
		
}