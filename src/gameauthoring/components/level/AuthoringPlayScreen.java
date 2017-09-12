package gameauthoring.components.level;

import java.util.HashSet;
import java.util.Set;
import configuration.MenuLanguage;
import gameauthoring.AuthoringFactory;
import gameauthoring.components.selectors.componentselectors.abilityselector.AbilitySelector;
import gameengine.GameWorld;
import gameengine.attributes.Path;
import gameengine.attributes.Spacial;
import gameengine.attributes.Zone;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.ExternalEngineInterface;
import gameengine.utilities.LiveObserver;
import gameplayer.display.DisplayInterface;
import gameplayer.display.gameplay.playscreen.PlayScreen;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * @author Walker Eacho
 */
public class AuthoringPlayScreen extends PlayScreen implements LiveObserver{
	private boolean draggingActive;
	private double[] lastPoint;
	private Path currentPath;
	private Pane pathingPane;
	private EntityInterface mySelectedEntity;
	private SimpleBooleanProperty myEntityBoolean;
	private GameWorld myWorld;
	private Set<EntityInterface> oldEntities;
	private Set<EntityInterface> newEntities;

	public AuthoringPlayScreen(ExternalEngineInterface currentChoice, DisplayInterface display, double width,
			double height, GameWorld world) {
		super(currentChoice, display, width, height);
		oldEntities = new HashSet<EntityInterface>();
		newEntities = new HashSet<EntityInterface>();
		myWorld = world;
		myWorld.getEntities().registerObserver(this);
		getGamePane().setOnMouseReleased(r->engineMouseListener(r));
		myEntityBoolean = new SimpleBooleanProperty();
		getEngine().loadGameWorld(world);
		amend();
	}

	public void amend(){
		super.amend();
		myWorld.getEntities().forEach(entity->{
			if(oldEntities.contains(entity)) oldEntities.remove(entity);
			else newEntities.add(entity);
		});
		newEntities.forEach(toAdd -> addPotentialPath(toAdd));
		oldEntities = new HashSet<EntityInterface>(myWorld.getEntities());
		newEntities.clear();
	}

	protected void addPotentialPath(EntityInterface elementAdded) {
		try{
			ImageView entityIView = getEntityInfoMap().get(elementAdded).getImageView();
			entityIView.setOnMouseClicked(r->{
				initializeGeneralListeners(elementAdded,entityIView);
			});
		}catch(NullPointerException superNotInitialized){
			ImageView entityIView = getEntityInfoMap().get(elementAdded).getImageView();
			entityIView.setOnMouseClicked(r->{
				initializeGeneralListeners(elementAdded,entityIView);
			});
		}
	}

	private void engineMouseListener(MouseEvent r) {
		if(r.isShiftDown()) getEngine().setOnMouseAction(r);
	}

	private void initializeGeneralListeners(EntityInterface entity, ImageView entityIView){
		mySelectedEntity = entity;
		getEngine().getWorld().getEntities().forEach(ent -> {
			if(ent.containsAttribute(Zone.class)){
				DropShadow zoneShadow = new DropShadow();
				zoneShadow.setColor(Color.BLUE);
				zoneShadow.setRadius(ent.getAttribute(Zone.class).getRadius());
				zoneShadow.setSpread(1.0);
				getEntityInfoMap().get(ent).getImageView().setEffect(zoneShadow);
			}else getEntityInfoMap().get(ent).getImageView().setEffect(null);
		});
		entityIView.setEffect(null);
		DropShadow ds= new DropShadow();
		ds.setColor(Color.BLACK);
		if(entityIView.getBoundsInLocal().getHeight()>entityIView.getBoundsInLocal().getWidth()){
			ds.setRadius(entityIView.getBoundsInLocal().getHeight()/2.0);
		}else{
			ds.setRadius(entityIView.getBoundsInLocal().getWidth()/2.0);
		}
		entityIView.setEffect(ds);
		getGamePane().setOnMouseDragged(dr-> onIViewDragged(dr,entityIView,entity));
		getGamePane().setOnMouseReleased(r-> updateBackend(r,entityIView,entity));
		myEntityBoolean.set(!myEntityBoolean.get());
	}

	private void onIViewDragged(MouseEvent dr, ImageView entityIView, EntityInterface selected) {
		if(entityIView.getLayoutX()-entityIView.getBoundsInLocal().getWidth() < dr.getX() &&
				entityIView.getLayoutX()+entityIView.getBoundsInLocal().getWidth()> dr.getX()
				&& entityIView.getLayoutY()-entityIView.getBoundsInLocal().getHeight() < dr.getY()
				&& entityIView.getLayoutY()+entityIView.getBoundsInLocal().getHeight() > dr.getY())
		{
			mySelectedEntity=selected;
			if(dr.isShortcutDown()){
				double adjustedWidth = entityIView.getLayoutBounds().getWidth()/2.0;
				double adjustedHeight = entityIView.getLayoutBounds().getHeight()/2.0;
				entityIView.setRotate(-90+180/Math.PI*(Math.atan2(
						(dr.getX()-(entityIView.getLayoutX()+adjustedWidth)),((entityIView.getLayoutY()+adjustedHeight) - dr.getY()))));
			}else{
				entityIView.relocate(dr.getX(), dr.getY());
			}
			draggingActive=true;
		}
	}

	private void updateBackend(MouseEvent r, ImageView entityIView, EntityInterface entity) {
		if(draggingActive){
			entity.getAttribute(Spacial.class).setOrientation(entityIView.getRotate());
			entity.getAttribute(Spacial.class).setX(r.getX());
			entity.getAttribute(Spacial.class).assignY(r.getY());
			draggingActive=false;
		}
	}

	public void mouseRelease(MouseEvent m, EntityInterface user) {
		if(m.isShiftDown()) getEngine().setOnMouseAction(m,user);
		else if(m.isControlDown() && m.isControlDown()){
			if(mySelectedEntity!=null){
				myWorld.getEntities().remove(mySelectedEntity);
				myEntityBoolean.set(!myEntityBoolean.get());
			}
		}else if(m.isAltDown()) {
			if(currentPath==null) setUpPaths();
			else submitPath();
		}
	}

	public void setUpPaths(){
		if(mySelectedEntity!=null){
			currentPath = new Path();
			pathingPane = new Pane();
			pathingPane.setMinWidth(getGamePane().getWidth());
			pathingPane.setMinHeight(getGamePane().getHeight());
			pathingPane.setPrefWidth(getGamePane().getWidth());
			pathingPane.setPrefHeight(getGamePane().getHeight());
			pathingPane.setOnMouseClicked(cl->addNewPoint(cl));
			pathingPane.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 255,.1), null, null)));
			getStacker().getChildren().add(pathingPane);
		}

	}

	public EntityInterface getSelectedEntity(){
		return mySelectedEntity;
	}

	public BooleanProperty getEntityBoolean(){
		return myEntityBoolean;
	}

	private void addNewPoint(MouseEvent clicked){
		if(currentPath.retrievePath().size()>0){
			Line line = new Line();
			line.setStartX(lastPoint[0]);
			line.setStartY(lastPoint[1]);
			line.setEndX(clicked.getX());
			line.setEndY(clicked.getY());
			pathingPane.getChildren().add(line);
		}
		lastPoint= new double[]{clicked.getX(),clicked.getY()};
		if(clicked.getButton()==MouseButton.SECONDARY){
			Stage abStage = new Stage();
			Group abGroup = new Group();
			Scene abScene = new Scene (abGroup,200,400,Color.WHITE);
			VBox abFlower = new VBox();
			abFlower.setSpacing(abScene.getHeight()/10);

			AbilitySelector abSelector = new AbilitySelector(mySelectedEntity, myWorld);
                        abSelector.initialize();
			abFlower.getChildren().add(AuthoringFactory.makeButton(
			                      MenuLanguage.getInstance().getValue("Submit"), b->{

				if(abSelector.getAbilities().size()==1)
					currentPath.addPoint(new double[]{lastPoint[0],lastPoint[1]},abSelector.getAbilities().get(0));
				abStage.close();
			}));
			abFlower.getChildren().add(abSelector.getAbilitySection());
			abGroup.getChildren().add(abFlower);
			abStage.setScene(abScene);
			abStage.showAndWait();

		}else{
			currentPath.addPoint(new double[]{lastPoint[0], lastPoint[1]});
		}
	}

	public void submitPath(){
		currentPath.setRepeating(true);
		if(mySelectedEntity.containsAttribute(Path.class)){
			mySelectedEntity.getAttribute(Path.class).setPath(currentPath);
		}else{
			mySelectedEntity.addAttribute(currentPath);
		}
		currentPath.setEntity(mySelectedEntity);

		getStacker().getChildren().remove(getStacker().getChildren().size()-1);
		getEngine().getPathingManager().updateTrackedPaths();
		currentPath=null;
		pathingPane=null;

	}

	public void clearPath(){
		currentPath.removeAllPoints();
	}
}