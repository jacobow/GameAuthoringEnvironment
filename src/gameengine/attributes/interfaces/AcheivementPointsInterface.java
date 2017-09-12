package gameengine.attributes.interfaces;

import javafx.beans.property.DoubleProperty;

public interface AcheivementPointsInterface {
	
    public DoubleProperty getAcheivementPoints();
	
    public void addPoints(double points);
}
