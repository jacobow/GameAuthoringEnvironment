package gameengine.attributes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.attributes.interfaces.PathInterface;
import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.AbilityInterface;
import gameengine.systems.abilities.MoveForward;
import gameengine.systems.abilities.NullAbility;
import gameengine.systems.abilities.RotateToClick;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * 
 * @author walker
 *
 */
public class Path implements AttributeInterface, PathInterface{
	
	private EntityInterface myEntity=null;
	private RotateToClick myRotator=null;
	private MoveForward myMover=null;
	private List<AbilityInterface> myAbilitiesToActivate;
	private LinkedHashMap<double[],AbilityInterface> myPointsList;
	private Iterator<double[]> myPointIterator; 
	private boolean shouldIRepeat=false;
	private double[] myCurrentPoint;
	
	///////////////////////////
	// Constructors          //
	///////////////////////////
	public Path(){
		myEntity = null;
		myRotator = null;
		myMover = null;
		myAbilitiesToActivate = new ArrayList<AbilityInterface>();
		myPointsList = new LinkedHashMap<double[], AbilityInterface>();
		myPointIterator =null;
		shouldIRepeat=false;
		myCurrentPoint =null;
	}
	
	public Path(EntityInterface entityToControl,List<AbilityInterface> initialAbilities, List<double[]> initialPoints){
		setEntity(entityToControl);
		myAbilitiesToActivate.addAll(initialAbilities);
		for(int i=0;i<initialPoints.size();i++){
			addPoint(initialPoints.get(i));
		}		
	}
	
	public Path(EntityInterface entityToControl, List<AbilityInterface> initialAbilities){
		setEntity(entityToControl);
		myAbilitiesToActivate.addAll(initialAbilities);
		
	}
	
	public Path(EntityInterface entityToControl){
		setEntity(entityToControl);
	}

	private void initializeMovers() {
		if(myEntity.containsAttribute(Spacial.class)){
			myRotator = new RotateToClick(myEntity);
			myMover = new MoveForward(myEntity);
		}
	}
	
	///////////////////////////
    // Other                 //
    ///////////////////////////
	
	public void setEntity(EntityInterface entityToControl) {
		myEntity = entityToControl;
		if(myPointsList==null) myPointsList = new LinkedHashMap<double[],AbilityInterface>();
		myAbilitiesToActivate = new ArrayList<AbilityInterface>();
		initializeMovers();
	}
	
	public void setRepeating(boolean repeat){
		shouldIRepeat = repeat;
	}
	
	public boolean retrieveRepeating(){
		return shouldIRepeat;
	}
	
	public void addPoint(double[] newPoint){
		if(myAbilitiesToActivate.size()>myPointsList.size()){
			addPoint(newPoint, myAbilitiesToActivate.get(myPointsList.size()));
		}else{
			addPoint(newPoint, new NullAbility(null));
		}
	}
	
	public void addPoint(double[] newPoint, AbilityInterface newAbility){
		myPointsList.put(newPoint, newAbility);
		myPointIterator = new ArrayList<double[]>(myPointsList.keySet()).listIterator();
		double[] test = myPointIterator.next();
		if(myCurrentPoint!=null){
			while(!myCurrentPoint.equals(test)){
				test = myPointIterator.next();
			}
		}else{
			myCurrentPoint = test;
		}
		
		if(myPointsList.size()>1) shouldIRepeat=true;

	}
	
	public void removeAllPoints(){
		myCurrentPoint=null;
		myPointsList.clear();
		myPointIterator=null;
	}
	
	public RotateToClick retrieveRotator(){
		return myRotator;
	}
	
	public MoveForward retrieveMover(){
		return myMover;
	}
	
	public double[] retrieveCurrentPoint(){
		return myCurrentPoint;
	}
	
	public AbilityInterface retrieveCurrentAbility(){
		if(myPointsList.containsKey(retrieveCurrentPoint()))
		return myPointsList.get(retrieveCurrentPoint());
		return new NullAbility(null);
	}
	
	public void increaseIterator(){
		if(myPointIterator!=null && myPointIterator.hasNext()){
			myCurrentPoint = myPointIterator.next();
		}else{
			myPointIterator = new ArrayList<double[]>(myPointsList.keySet()).listIterator();
			myCurrentPoint = myPointIterator.next();
		}
	}
	
	public boolean isRepeating(){
		return shouldIRepeat;
	}

	@Override
	public EntityInterface retrieveControlledEntity() {
		return myEntity;
	}

	public void setPath(Path copyPath) {
		myPointsList = new LinkedHashMap<double[], AbilityInterface>(copyPath.retrievePath());
		myPointIterator = new ArrayList<double[]>(myPointsList.keySet()).listIterator();
	}
	
	public Map<double[], AbilityInterface> retrievePath(){
		return myPointsList;
	}
}
