package gameengine.attributes.interfaces;

import java.util.List;
import java.util.Queue;

import gameengine.entities.EntityInterface;
import javafx.scene.shape.Circle;

public interface ZoneInterface extends AttributeInterface{

	/**
	 *
	 * @return the JavaFX Circle representation of that zone
	 */
	Circle retrieveZone();

	/**
	 *
	 * @param radius new zone radius
	 */
	void setRadius(double radius);

	/**
	 *
	 * @param x circle's new center x coordinate
	 */
	void assignX(double x);

	/**
	 *
	 * @param y circle's new center y coordinate
	 */
	void assignY(double y);

	/**
	 *
	 * @return Team name of the zone, if any.
	 */
	public String getTeamName();

	/**
	 * @param New Zone team name
	 */
	//public void setTeamName(String teamname);

	public Queue<EntityInterface> getInRangeEntities();

	public void assignInRangeEntities(Queue<EntityInterface> entities);
}
