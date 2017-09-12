package gameauthoring.components.entitycreation;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import configuration.MenuLanguage;
import gameauthoring.AuthoringFactory;
import gameauthoring.AuthoringPane;
import gameauthoring.components.selectors.componentselectors.attributeselector.AttributeSelector;
import gameauthoring.components.selectors.AuthoringFileChooser;
import gameengine.attributes.Spacial;
import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.entities.Entity;
import gameengine.entities.EntityInterface;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Purpose: this class allows the user to create an entity with only attributes
 * Assumptions: assumes that the user will want every entity to have a Spacial ability
 * (this gives the Entity an image and a position)
 * Dependencies: the class is dependent on the the AttributeSelector class 
 * Example Use: used to create an Entity with a Turtle sprite and an Energy attribute 
 * with a MaxEnergy property of 100. It could then set this entity as a projectile of
 * another entity
 * 
 * @author Larissa Cox, Michael Seaberg
 *
 */

public class EntityCreator extends AuthoringPane implements EntityCreatorInterface{

	 private final static String TITLE = "Entity Creator";
	    private Stage myStage;
	    private Scene myScene;
	    private List<AttributeInterface> myNewEntityAttributes;
	    private String myNewEntityID;
	    private transient ImageView myImage;
	    private EntityInterface myEntity;
	    private AttributeSelector myAttributeSelector;
	    private TextField myNameBox,speedInput;

	    public EntityCreator(){
	        super(TITLE);
	        myAttributeSelector = new AttributeSelector();
	        myStage = new Stage();
	        myNewEntityID = "New Entity";
	        myNewEntityAttributes = new ArrayList<AttributeInterface>();
	        myScene = new Scene(this.getBox(),550,820);
	        myNameBox = AuthoringFactory.makeTextField("New Entity");
	        speedInput = AuthoringFactory.makeTextField("0");
	        myImage = new ImageView();
	        setEntity(null);
	    }

	    public void initialize() {
	    	this.getBox().getChildren().clear();
	        this.setChildrenSpacing(10);
	        this.getBox().setMinWidth(300);
	        myImage.setFitWidth(80);
	        myImage.setFitHeight(80);
	        HBox line1 = AuthoringFactory.wrapInHBox(Pos.CENTER,AuthoringFactory.makeLabel(
	                                                           MenuLanguage.getInstance().getValue("NewEntity")), myNameBox);
	        HBox line2 = AuthoringFactory.wrapInHBox(Pos.CENTER,AuthoringFactory.makeButton(
	                                                           MenuLanguage.getInstance().getValue("ChooseImage"), e->openImageFinder()),myImage);
	        HBox line3 = AuthoringFactory.wrapInHBox(Pos.CENTER,AuthoringFactory.makeLabel(
	                                                           MenuLanguage.getInstance().getValue("EnterSpeed")),speedInput);
	        HBox attributeSection = AuthoringFactory.wrapInHBox(Pos.CENTER_LEFT, myAttributeSelector.
	        													createAttributeSection(myEntity.getAttributesList()));
	        this.getBox().getChildren().addAll(line1,line2,line3,attributeSection);
	        if (myEntity.containsAttribute(Spacial.class)){
	        	addClose();
	        }
	        myStage.widthProperty().addListener(myAttributeSelector.getWidthListener());
	        myStage.centerOnScreen();
	        myScene.getStylesheets().add("defaultstyle.css");
	        myStage.setScene(myScene);
	        myStage.show();
	    }
	    
	    public EntityInterface getEntity(){
	        return myEntity;
	    }

	    /**
	     * purpose: to allow the user to choose an image from file and 
	     * load it into the entity using a spacial
	     */
	    protected void openImageFinder(){
	        AuthoringFileChooser imageChooser = new AuthoringFileChooser();
	        File image = imageChooser.chooseImage(MenuLanguage.getInstance().getValue("ChooseIcon"));
	        if(image != null){

	            Iterator<AttributeInterface> att = myNewEntityAttributes.iterator();
	            String imgPath = image.toString();
	            String separator = Pattern.quote(System.getProperty("file.separator"));
	            String imgPath2 = imgPath.split("src" + separator)[1];

	            Spacial newSpacialAttribute = new Spacial(0, 0, 0, 150, imgPath2);
	            while (att.hasNext()){
	                if(att instanceof Spacial){
	                    myNewEntityAttributes.remove(att);
	                }
	            }
	            myEntity.addAttribute(newSpacialAttribute);
	            myImage.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(imgPath2)));
	        }
	        addClose();
	    }
	    
	    /**
	     * purpose: to add a close button to the window
	     * this is done after the image is selected to keep the user from
	     * attempting to create an entity without an image
	     */
	    protected void addClose(){
	    	this.getBox().getChildren().add(AuthoringFactory.wrapInHBox(Pos.CENTER,AuthoringFactory.makeButton("Create Entity",
	    			e->closeCreator()),AuthoringFactory.makeButton("Cancel", e->getStage().close())));
	    }

	    /**
	     * purpose: to finalize the created entity and then close the window
	     */
	    protected void closeCreator() {
	        myNewEntityID = myNameBox.getText();
	        myEntity.setID(myNewEntityID);
	        finalizeEntity();
	        myStage.close();
	    }

	    /**
	     * purpose: to finalize the created entity by ensuring that all
	     * selected attributes have been added to it
	     */
	    protected void finalizeEntity(){
	        for (AttributeInterface attribute: myAttributeSelector.getAttributes()){
	            myEntity.addAttribute(attribute);
	        }
	        addSpeed();
	    }

	    /**
	     * purpose: to allow subclasses to set the entity that should be 
	     * updated
	     * @param entity the new entity to be customized
	     */
	    protected void setEntity(EntityInterface entity){
	    	if (entity == null){
	    		myEntity = new Entity(myNewEntityID, myNewEntityAttributes);
	    	}
	    	else{
	    		myEntity = entity;
	    		setSpacial();
	    	}	
	    }
	    
	    /**
	     * purpose: to provide subclasses access to the stage
	     * @return the window stage
	     */
	    protected Stage getStage(){
	        return myStage;
	    }
	    
	    private void setSpacial(){
	    	Spacial spacial = myEntity.getAttribute(Spacial.class);
	    	myImage.setImage(new Image(spacial.retrieveImageFile()));
	    	myNewEntityID = myEntity.getID();
	    	myNameBox = AuthoringFactory.makeTextField(myNewEntityID);
	    	speedInput = AuthoringFactory.makeTextField(""+spacial.getVelocity());
	    }
	    
	    /**
	     * purpose:helper method to set the speed value to a Spacial attribute upon finalizing
	     * the entity and closing the entityCreator.
	     */
	    private void addSpeed(){
	        try{
	            speedInput.getText();
	            Double.parseDouble(speedInput.getText());
	            myEntity.getAttribute(Spacial.class).setSpeed(Double.parseDouble(speedInput.getText()));
	        }catch(NumberFormatException nfe){
	            myEntity.getAttribute(Spacial.class).setSpeed(0.0);
	        }
	    }
	  
}