package gameengine.attributes;

import gameengine.attributes.interfaces.AcheivementPointsInterface;
import gameengine.attributes.interfaces.AttributeInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class AchievementPoints implements AttributeInterface, AcheivementPointsInterface {

	private DoubleProperty points;

	public AchievementPoints() {
		points = new SimpleDoubleProperty();
	}
	
	public void addPoints(double points){
		this.points.set(this.points.doubleValue()+points);
	}
	
	@Override
	public DoubleProperty getAcheivementPoints() {
		return points;
	}
	
	
	
}
