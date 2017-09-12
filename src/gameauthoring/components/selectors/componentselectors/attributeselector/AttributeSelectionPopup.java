package gameauthoring.components.selectors.componentselectors.attributeselector;

import java.util.ArrayList;
import configuration.MenuLanguage;
import gameauthoring.AuthoringFactory;
import gameauthoring.AuthoringPane;
import gameengine.attributes.interfaces.AttributeInterface;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * Purpose: this class allows the user to select an Attribute for use in the AssignAttribtue
 * ability
 * Dependencies: the class is dependent on the the AttributeSelector class
 * Example Use: used to allow the user to give an entity the ability to give another entity an 
 * Energy attribute from a list of other attributes and then set it's properties
 * 
 * @author Larissa Cox, Michael Seaberg
 *
 */


public class AttributeSelectionPopup extends AuthoringPane{
	private AttributeSelector myAttributeSelector;
	private final static String TITLE = "Attribute Selection Pane";
	private Stage myStage;
	private Scene myScene;
	
	public AttributeSelectionPopup(){
		super(TITLE);
		myAttributeSelector = new AttributeSelector();
        myStage = new Stage();
        myScene = new Scene(this.getBox(),200,200);
	}
	
	/**
	 * purpose: initializes the pop-up and selection options for selecting an 
	 * attribute to use as the attribute added in a AssignAttribute ability
	 */
	public void initialize(){
		this.setChildrenSpacing(10);
        this.getBox().setMinWidth(200);
        HBox attributeSection = AuthoringFactory.wrapInHBox(Pos.CENTER_LEFT, myAttributeSelector.createAttributeSection(
        		new ArrayList<AttributeInterface>()));
        this.getBox().getChildren().add(attributeSection);
        this.getBox().getChildren().add(AuthoringFactory.wrapInHBox(Pos.CENTER,AuthoringFactory.makeButton(
                                             MenuLanguage.getInstance().getValue("CreateEntity"), e->myStage.close()),
                                             AuthoringFactory.makeButton(MenuLanguage.getInstance().getValue("Cancel"), e->myStage.close())));
        myStage.centerOnScreen();
        myScene.getStylesheets().add("defaultstyle.css");
        myStage.setScene(myScene);
        myStage.showAndWait();
	}
	
	/**
	 * purpose: to provide access to the first attribute selected by the user
	 * assumptions: assumes that the user has selected at least one attribute
	 * @return the first attribute that the user selected
	 */
	public AttributeInterface getAttribute(){
		return myAttributeSelector.getAttributes().get(0);
	}
}
