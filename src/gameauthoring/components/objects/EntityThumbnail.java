package gameauthoring.components.objects;

import java.io.Serializable;
import gameengine.attributes.Spacial;
import gameengine.entities.Entity;
import gameengine.entities.EntityInterface;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * 
 * @author michaelseaberg
 *
 */
public class EntityThumbnail extends Group implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 6712437323821282303L;
	private Entity myEntity;
	private Node myThumbnail;

	public EntityThumbnail(EntityInterface elementAdded){
		myEntity = (Entity) elementAdded;
		myThumbnail = createThumbnail();
		myThumbnail.setId("entityThumb");
		setupClick();
	}

	public Entity getMyEntity() {
		return myEntity;
	}

	/**
	 * Method to tell the parent EntityTab that this thumbnail has been clicked and should be designated as selected.
	 * NOTE: Wasnt finished for final demonstration
	 */
	private void setupClick() {
		myThumbnail.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent mouseEvent) {

			}
		});
	}

	/**
	 * Sets the specific javafx parameters neccessary to display a thumbnail in the objects list view.
	 * @return
	 */
	private Node createThumbnail() {
		if(myEntity!=null){
			if(myEntity.getAttribute(Spacial.class) !=null){
				ImageView entityImageView = new ImageView(myEntity.getAttribute(Spacial.class).retrieveDisplay());
				VBox entityVisual = new VBox();
				entityVisual.setMinWidth(70);
				entityVisual.setMaxWidth(70);
				entityImageView.setFitWidth(60);
				entityImageView.setFitHeight(60);
				Label entityLabel = new Label(myEntity.getID());
				entityLabel.setTextAlignment(TextAlignment.CENTER);
				entityLabel.setLayoutY(80);
				entityVisual.getChildren().addAll(entityImageView,entityLabel);
				entityVisual.setAlignment(Pos.CENTER);
				return entityVisual;
			}
		}
		return null;
	}

	/**
	 * Returns the graphic for the entity thumbnail
	 * @return
	 */
	public Node getMyThumbnail() {
		return myThumbnail;
	}

}