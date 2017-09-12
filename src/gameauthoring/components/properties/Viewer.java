package gameauthoring.components.properties;
import java.util.ArrayList;
import java.util.List;

import gameauthoring.AuthoringPane;
import gameengine.entities.EntityInterface;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 * Purpose: this class provides structure for a class that displays information about an entity's
 * properties using accordions 
 * Dependencies: the class is dependent on the the Entity and AuthoringPane classes
 * Example Use: used to provide a structure for the PropertiesViewer class
 * 
 * @author Larissa Cox and Michael Seaberg 
 *
 */

public abstract class Viewer extends AuthoringPane implements ViewerInterface{
	private EntityInterface myEntity;
	private Accordion myAccordion;

	public Viewer(String title){
		super(title);
		myAccordion = new Accordion();
		myAccordion.getStyleClass().add("accordion");
		this.getBox().getChildren().add(myAccordion);	
	}
	
	public EntityInterface getEntity(){
		return myEntity;
	}

	public void updateInfo(EntityInterface newEntity){
		this.getBox().getChildren().remove(myAccordion);
		myEntity = newEntity;
		myAccordion  = createPropertyAccordion();
		this.getBox().getChildren().add(myAccordion);
	}

	/**
	 * purpose: to create a VBox full of the properties for each accordion
	 * @param property the object whose properties will be displayed in the accordion
	 * @return the VBox filled with the object's properties and their values
	 */
	protected abstract VBox fillPropteryPane(Object property) ;
	
	/**
	 * purpose: to return the list of properties for the accordion
	 * @return the list of properties
	 */
	protected abstract List<Object> getProperties();

	private Accordion createPropertyAccordion() {
		Accordion returnAccordion = new Accordion();
		ArrayList<TitledPane> panes = new ArrayList<TitledPane>();
		for (Object property: getProperties()){
			VBox attributePaneContent = fillPropteryPane(property); 
			TitledPane newPane = new TitledPane(property.getClass().getSimpleName(), attributePaneContent);
			newPane.getStyleClass().add("titled-pane");
			panes.add(newPane);
		}
		
		returnAccordion.getPanes().addAll(panes);
		return returnAccordion;
	}
}
