package gameauthoring.components.entitycreation;

import gameauthoring.AuthoringFactory;
import gameauthoring.components.selectors.componentselectors.abilityselector.AbilitySelector;
import gameengine.GameWorld;
import gameengine.entities.EntityInterface;

/**
 * Purpose: this class allows the user to create an entity with attributes and
 * abilities 
 * Assumptions: assumes that the addition of Attributes is handled by the Entity
 * Creator class
 * Dependencies: the class is dependent on the the GameWorld, AbilitySelector, 
 * and EntityCreator classes 
 * Example Use: used to create an Entity with a Turtle sprite, a MoveForward ability 
 * set to the key W, and an Energy attribute with a MaxEnergy property of 100
 * 
 * @author Larissa Cox
 *
 */

public class ParentEntityCreator extends EntityCreator implements EntityCreatorInterface{
	private GameWorld myWorld;
	private AbilitySelector myAbilitySelector;

	public ParentEntityCreator(GameWorld world, EntityInterface entity) {
		super();
		myWorld = world;
		setEntity(entity);
		myAbilitySelector = new AbilitySelector(getEntity(), myWorld);
	}

	/**
	 * purpose: overrides the default finalizeEntity() method in EntityCreator to account for added abilities
	 */
	@Override
	protected void finalizeEntity() {
		super.finalizeEntity();
		myAbilitySelector.compileAbilities();

		for (int i = 0; i < myAbilitySelector.getAbilities().size(); i++) {
			myWorld.addAbility(myAbilitySelector.getKeyCodes().get(i), getEntity(),
					myAbilitySelector.getAbilities().get(i));
			getEntity().addAbility(myAbilitySelector.getAbilities().get(i), myAbilitySelector.getKeyCodes().get(i));
		}
		myWorld.addEntity(getEntity());
		myAbilitySelector.getControllerCodes();
	}

	/**
	 * purpose: to add the abilities selector and close button to the window
	 * this is done after the image is added to prevent the user from trying to create an
	 * entity without an image
	 */
	@Override
	protected void addClose() {
		super.addClose();
		myAbilitySelector.setMyEntity(getEntity());
		this.getBox().getChildren().add(this.getBox().getChildren().size() - 1, myAbilitySelector.getMyContentArea());
		getStage().widthProperty().addListener(myAbilitySelector.getWidthListener());
	}

}