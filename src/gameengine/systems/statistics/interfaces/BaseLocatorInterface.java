package gameengine.systems.statistics.interfaces;

import gameengine.systems.interfaces.SubSystemInterface;
import javafx.beans.property.DoubleProperty;

/**
 * @author DavidYoon
 *
 */
public interface BaseLocatorInterface extends SubSystemInterface {

	public DoubleProperty getDirectionToBase();

}
