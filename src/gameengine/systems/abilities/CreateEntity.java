package gameengine.systems.abilities;

import java.util.ArrayList;

import gameengine.attributes.Child;
import gameengine.attributes.CleanUp;
import gameengine.attributes.Path;
import gameengine.attributes.Range;
import gameengine.attributes.Spacial;
import gameengine.attributes.Team;
import gameengine.attributes.interfaces.SpacialInterface;
import gameengine.entities.Entity;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.EntityCreationInterface;

/**
 * 
 * @author walker
 *
 */
public class CreateEntity implements AbilityInterface{
	private EntityInterface myParent;
	private EntityInterface myChild;
	private EntityCreationInterface myGod;
	private double myCooldown;
	private int cooldownCounter;
	private int numPrimaryCooldowns;
	private double mySecondaryCooldown;
	private double myPrimaryCooldown;
	private int index=0;

	public CreateEntity(EntityInterface child, EntityInterface parent, EntityCreationInterface creator){
		myGod = creator;
		myCooldown = 0.5;
		cooldownCounter=1;
		myPrimaryCooldown = .5;
		mySecondaryCooldown = 5;
		numPrimaryCooldowns = 10;
		myParent = parent;
		setChild(child);
	}

	@Override
	public void setCooldown(double newCooldown){
		myPrimaryCooldown = newCooldown;
	}

	@Override
	public void setSecondaryCooldown(double newCooldown){
		mySecondaryCooldown = newCooldown;
	}
	
	public void useCooldown(){
		if(numPrimaryCooldowns>0 && cooldownCounter>numPrimaryCooldowns && mySecondaryCooldown!=0){
			myCooldown = mySecondaryCooldown;
			cooldownCounter=1;
		}else{
			myCooldown = myPrimaryCooldown;
			if(numPrimaryCooldowns>0) cooldownCounter++;
		}
	}
	@Override
	public void setSizeOfWaves(double newMax){
		numPrimaryCooldowns=(int) Math.floor(newMax);
	}
	
	@Override
	public double getCooldown(){
		return myCooldown;
	}

	/**
	 * Modifies the location then places the entity.
	 */
	public void activate(double timeElapsed, double[] extraInputs) {
		SpacialInterface currSpacial = null;
		Entity toCreate = new Entity(myChild.getID()+index, myChild.getAttributesList());
		if(toCreate.containsAttribute(Spacial.class)) {
			currSpacial = new Spacial(toCreate.getAttribute(Spacial.class));
			if(extraInputs.length<=1){
				if(myParent.containsAttribute(Spacial.class)){
					double parentHalfHeight = myParent.getAttribute(Spacial.class).retrieveShape().getBoundsInLocal().getHeight()/2.0;
					double parentHalfWidth = myParent.getAttribute(Spacial.class).retrieveShape().getBoundsInLocal().getWidth()/2.0;
					double parentMidFrontX = myParent.getAttribute(Spacial.class).retrieveX().get() + parentHalfWidth + parentHalfWidth * Math.cos(Math.PI/180.0 * myParent.getAttribute(Spacial.class).retrieveOrientation().get());
					double parentMidFrontY = myParent.getAttribute(Spacial.class).retrieveY().get() + parentHalfHeight+ parentHalfHeight* Math.sin(Math.PI/180.0 * myParent.getAttribute(Spacial.class).retrieveOrientation().get());

					offsetSpacial(currSpacial, toCreate, parentMidFrontX, parentMidFrontY);

					//Projectile
					if(toCreate.containsAttribute(Range.class)){
						createProjectile(currSpacial, toCreate, parentMidFrontX, parentMidFrontY);
					}
				}

			}else{
				moveSpacialToClick(extraInputs, currSpacial, toCreate);
			}
		}
		index++;
		myGod.addEntity(toCreate);
	}

	private void moveSpacialToClick(double[] extraInputs, SpacialInterface currSpacial, Entity toCreate) {
		currSpacial.setX(extraInputs[0]);
		currSpacial.assignY(extraInputs[1]);
		toCreate.addAttribute(currSpacial);
	}

	private void createProjectile(SpacialInterface currSpacial, Entity toCreate, double parentMidFrontX, double parentMidFrontY) {
		double range = toCreate.getAttribute(Range.class).getRange();
		double[] target = new double[]{
				(parentMidFrontX) + range*Math.cos(Math.PI/180.0 * currSpacial.retrieveOrientation().get()),
				(parentMidFrontY) + range*Math.sin(Math.PI/180.0 * currSpacial.retrieveOrientation().get())
		};
		ArrayList<double[]> singlePoint = new ArrayList<double[]>();
		singlePoint.add(target);
		ArrayList<AbilityInterface> oneTime = new ArrayList<AbilityInterface>();
		AssignAttribute death = new AssignAttribute(toCreate);
		death.setAttributeToAssign(new CleanUp());
		oneTime.add(death);
		Path projectilePath = new Path(toCreate,oneTime,singlePoint);
		if(toCreate.containsAttribute(Path.class)){
			toCreate.getAbilityMap().remove(Path.class);
		}
		toCreate.addAttribute(projectilePath);
	}

	private void offsetSpacial(SpacialInterface currSpacial, Entity toCreate, double parentMidFrontX, double parentMidFrontY) {
		currSpacial.setOrientation(myParent.getAttribute(Spacial.class).retrieveOrientation().get());
		double currHalfHeight= currSpacial.retrieveShape().getBoundsInLocal().getHeight()/2.0;
		double currHalfWidth = currSpacial.retrieveShape().getBoundsInLocal().getWidth() /2.0;
		double currAdjustedY = currHalfHeight- currHalfHeight*Math.sin(Math.PI/180.0 * currSpacial.retrieveOrientation().get());
		double currAdjustedX = currHalfWidth - currHalfWidth *Math.cos(Math.PI/180.0 * currSpacial.retrieveOrientation().get());
		currSpacial.setX(parentMidFrontX-currAdjustedX);
		currSpacial.assignY(parentMidFrontY-currAdjustedY);
		toCreate.addAttribute(currSpacial);
	}

	public void setChild(EntityInterface child){
		myChild = child;
		Child childAtt = new Child();
		childAtt.assignParent(myParent);
		if(child.containsAttribute(Child.class)){
			child.getAbilityMap().remove(Child.class);
		}
		child.addAttribute(childAtt);
		if(myChild.containsAttribute(Child.class))	myParent = myChild.getAttribute(Child.class).retrieveParent();
		else myParent = null;
		if(myParent !=null && myParent.containsAttribute(Team.class)) myChild.addAttribute(new Team(myParent.getAttribute(Team.class).getTeam()));
	}

}
