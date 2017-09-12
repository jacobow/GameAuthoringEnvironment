package gameauthoring.components.selectors.abstractselectors;

import java.util.List;

public interface CheckBoxSelectorInterface {

	/**
	 * purpose: prepares the selector to be closed by ensuring that all 
	 * the boxes the user has checked have their content added to mySelections
	 * for processing
	 * assumptions: assumes that mySelections has been initialized
	 */
	public void closeSelector() ; 
	
	/**
	 * purpose: returns the list of user selected objects
	 * @return the list of user selected objects
	 */
	public List<Object> getSelected();
	
	
}
