package gameengine.attributes.interfaces;

import javafx.beans.property.DoubleProperty;

/**
 * This interface provides the energy value for the entity that uses special abilities.
 * @author DavidYoon
 *
 */
public interface EnergyInterface {

	/**
	 * 
	 * @return amount of energy remaining
	 */
	public double getEnergy();
	
	/**
	 * use energy
	 */
	public void useEnergy(double amount);
	public DoubleProperty retrieveEnergyPercentage();
	public double getMaxEnergy ();
	public void assignEnergy (double energy);
	public void decreaseMaxEnergy (double energy);
	public void increaseMaxEnergy (double energy);

	public void setMaxEnergy (double energy);
}
