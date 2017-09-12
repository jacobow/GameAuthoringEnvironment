package gameengine.attributes;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.attributes.interfaces.ZoneInterface;
import gameengine.entities.EntityInterface;
import javafx.scene.shape.Circle;

/**
 * 
 * @author
 * @author walker
 *
 */
public class Zone implements ZoneInterface {

	private Circle myZone;
	private Queue<EntityInterface> inRangeEntities;
	private String myTeamname = "No Name";

	public Zone(){
		myZone = new Circle();
		inRangeEntities = new LinkedList<EntityInterface>();
	}

	public Zone(double x, double y, double radius) {
		myZone = new Circle(x, y, radius);
		inRangeEntities = new LinkedList<EntityInterface>();
	}

	public Zone(double x, double y, double radius, String teamName) {
		myZone = new Circle(x, y, radius);
		this.myTeamname = teamName;
		inRangeEntities = new LinkedList<EntityInterface>();
	}

	public Zone(double radius) {
		myZone = new Circle(radius);
		inRangeEntities = new LinkedList<EntityInterface>();
	}

	public Zone(double radius, String teamName) {
		myZone = new Circle(radius);
		this.myTeamname = teamName;
		inRangeEntities = new LinkedList<EntityInterface>();
	}

	public Circle retrieveZone() {
		return myZone;
	}

	public void setRadius(double radius) {
		myZone.setRadius(radius);
	}
	
	public double getRadius(){
		return myZone.getRadius();
	}

	public void assignX(double x) {
		myZone.setCenterX(x);
	}

	public void assignY(double y) {
		myZone.setCenterY(y);
	}
//
//	public void setTeamName(String teamname){
//		myTeamname = teamname;
//	}

	public String getTeamName() {
		return myTeamname;
	}

	public Queue<EntityInterface> getInRangeEntities() {
		return inRangeEntities;
	}

	public void assignInRangeEntities(Queue<EntityInterface> entities) {
		inRangeEntities = entities;
	}

}
