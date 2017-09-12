package gameauthoring.components.selectors.abstractselectors;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import gameauthoring.AuthoringFactory;
import gameauthoring.components.selectors.util.Reflector;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * Purpose: this class provides a structure for a class that gives the user the ability to 
 * select things from a list of check boxes
 * Dependencies: the class is dependent on the the AbstractSelector class
 * Example Use: used to allow the user to select a WinCondition from a list of WinCondition
 * possibilities
 * 
 * @author Larissa Cox 
 *
 */

public abstract class AbstractCheckBoxSelector extends AbstractSelector implements CheckBoxSelectorInterface{
	private static final Dimension CONTENT_DIMENSIONS = new Dimension(450, 330);
	private Stage myStage;
	private List<CheckBox> mySelectionPossibilities;
	private List<Object> mySelections;
	private Set<String> myClassSet;
	
	public AbstractCheckBoxSelector(String title) {
		super(title);
		myClassSet = new HashSet<String>();
	}
	
	/**
	 * purpose: prepares the selector to be closed by ensuring that all 
	 * the boxes the user has checked have their content added to mySelections
	 * for processing
	 * assumptions: assumes that mySelections has been initialized
	 */
	public void closeSelector() {
		for (CheckBox currAttribute : mySelectionPossibilities) {
			if (currAttribute.isSelected()) {
				mySelections.add(getObject(currAttribute.getId()));
			}
		}
		addSelections();
		myStage.close();
	}
	
	/**
	 * purpose: returns the list of user selected objects
	 * @return the list of user selected objects
	 */
	public List<Object> getSelected() {
		return mySelections;
	}
	
	/**
	 * purpose: to initialize the pop-up for display to the user
	 * @param interfaceClass the class to be used to generate the
	 * options to be displayed to the user
	 */
	protected void initialize(Object interfaceClass) {
		mySelectionPossibilities = new ArrayList<CheckBox>();
		mySelections = new ArrayList<Object>();
		myStage = new Stage();
		this.getBox().getChildren().add(
				AuthoringFactory.makeText("Select your " + ((Class<?>)interfaceClass).getSimpleName()));
		createSelectables(interfaceClass);
		this.getBox().getChildren()
		.add((AuthoringFactory.makeButton("Select " + ((Class<?>)interfaceClass).getSimpleName(), e -> closeSelector())));

		this.getBox().getStylesheets().add("defaultStyle.css");
		this.getBox().getStyleClass().add("vbox");
		myStage.centerOnScreen();
		myStage.setScene(new Scene(this.getBox(), CONTENT_DIMENSIONS.getWidth(), CONTENT_DIMENSIONS.getHeight())); // codes
		myStage.show();
	}
	
	/**
	 * purpose: gets all subclasses of the reflectionObject that are not interfaces 
	 * to be displayed
	 */
	protected Collection<Object> getReflectables(Object reflectionObject) {
		Reflector ref = new Reflector();
		Set<String> allProperties = ref.getSubclassNames((Class<?>) reflectionObject);
		Collection<Object> nonInterfaceProperties = new HashSet<Object>();
		for (String property : allProperties) {
			if (!property.contains("Interface")) {
				nonInterfaceProperties.add(property);
			}
		}
		return nonInterfaceProperties;
	}
	
	/**
	 * purpose: wraps the string class name content into a check box,
	 * which will be initialized as checked if the class already belongs 
	 * to the world 
	 */
	protected void wrapObject(Object content) {
		String[] contentList = ((String) content).split("\\.");
		CheckBox checkBox = new CheckBox(contentList[contentList.length - 1]);
		checkBox.getStyleClass().add("check-box");
		checkBox.setId((String) content);
		checkBox.setSelected(myClassSet.contains(content));
		mySelectionPossibilities.add(checkBox);
		this.getBox().getChildren().add(checkBox);

	}
	
	/**
	 * purpose: provides access to the class set, which stores the names of classes 
	 * that are already in the world 
	 * @return the set of classes in the world
	 */
	protected Set<String> getClassSet(){
		return myClassSet;
	}
	
	/**
	 * purpose: specifies how the selections should be processed
	 */
	protected abstract void addSelections();
	
	/**
	 * purpose: creates and returns the object that has the name className
	 * @param className the name of the class to be instantiated
	 * @return the object that is instantiated
	 */
	protected abstract Object getObject(String className);
}