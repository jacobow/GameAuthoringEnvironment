package gameengine.systems.statistics;

import java.util.Set;
import java.util.TreeMap;

import gameengine.attributes.Spacial;
import gameengine.entities.EntityInterface;
import gameengine.systems.statistics.interfaces.BaseLocatorInterface;
import gameengine.systems.statistics.interfaces.StatisticsInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class BaseLocator implements BaseLocatorInterface, StatisticsInterface {

	private DoubleProperty baseDirection;
	private TreeMap<Double, EntityInterface> distanceMap;
	private EntityInterface entity;
	private Set<EntityInterface> listOfBases;
	private Double closestDistance;
	private EntityInterface closestBase;
	private Spacial entitySpacial;

	public BaseLocator(EntityInterface entity, Set<EntityInterface> listOfBases) {
		this.entity = entity;
		this.entitySpacial = entity.getAttribute(Spacial.class);
		this.listOfBases = listOfBases;
		this.baseDirection = new SimpleDoubleProperty();
		this.distanceMap = new TreeMap<Double, EntityInterface>();
		initializeListeners();
	}

	private void initializeListeners(){
		entitySpacial.retrieveY().addListener((x,y,z)->{
			findClosestBase();
		});
		entitySpacial.retrieveY().addListener((x,y,z)->{
			findClosestBase();
		});
		for(EntityInterface base: listOfBases){
			base.getAttribute(Spacial.class).retrieveX().addListener((x,y,z)->{
				findClosestBase();
			});
			base.getAttribute(Spacial.class).retrieveY().addListener((x,y,z)->{
				findClosestBase();
			});
		}
	}


	@Override
	public DoubleProperty getDirectionToBase() {
		findClosestBase();
		closestDistance = distanceMap.firstKey();
		closestBase = distanceMap.get(closestDistance);

		baseDirection = new SimpleDoubleProperty(findAngle());
		distanceMap.clear();
		return baseDirection;
	}


	private double findAngle() {
		double playerAngle =  entitySpacial.retrieveOrientation().get();
		double playerX =  entitySpacial.retrieveX().get();
		double baseX =  closestBase.getAttribute(Spacial.class).retrieveX().get();
		double dX =  baseX-playerX;

		double baseAngle = Math.acos(closestDistance/dX);

		if (dX<0) baseAngle = 180 - baseAngle;

		return baseAngle - playerAngle;
	}

	private void findClosestBase() {
		double x1 = entity.getAttribute(Spacial.class).retrieveX().get();
		double y1 = entity.getAttribute(Spacial.class).retrieveY().get();

		for (EntityInterface base : listOfBases){
			double x2 = base.getAttribute(Spacial.class).retrieveX().get();
			double y2 = base.getAttribute(Spacial.class).retrieveY().get();

			double distance = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));

			distanceMap.put(distance, base);
		}
	}

}
