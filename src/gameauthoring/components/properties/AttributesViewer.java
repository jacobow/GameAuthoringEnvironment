package gameauthoring.components.properties;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import gameauthoring.AuthoringFactory;
import gameengine.attributes.interfaces.AttributeInterface;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.reflection.Reflection;

/**
 * Purpose: this class allows the program to display the Attributes of a selected entity, along
 * with their properties, in an accordion
 * Dependencies: the class is dependent on the the Viewer class
 * Example Use: used to allow the user to view that a selected entity has an Energy attribute with a
 * MaxEnergy attribute of 150
 * 
 * @author Larissa Cox 
 *
 */

public class AttributesViewer extends Viewer{
	private final static String TITLE = "Entity Attributes";
	
	public AttributesViewer() {
		super(TITLE);
	}
	
	/**
	 * purpose: to return the attributes displayed in the accordion
	 */
	@Override
	protected List<Object> getProperties() {
		List<Object> myObjects = new ArrayList<Object>();
		for (AttributeInterface attribute: getEntity().getAttributesList()){
			myObjects.add(attribute);
		}
		return myObjects;
	}
	
	/**
	 * purpose: to fill the VBox with the attributes of the selected entity and 
	 * their properties
	 */
	@Override
	protected VBox fillPropteryPane(Object property) {
		VBox properties = new VBox();
		for (Method method: property.getClass().getDeclaredMethods()){
			if (method.getName().contains("get")){
				HBox propertyDisplay = new HBox();
				propertyDisplay.getChildren().addAll(AuthoringFactory.makeText(method.getName().substring(3) + ": " + Reflection.callMethod(property, method.getName())));
				properties.getChildren().add(propertyDisplay);
			}
		}
		return properties;
	}
	
	
}