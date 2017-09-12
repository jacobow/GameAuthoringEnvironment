package gameengine.attributes;

import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.attributes.interfaces.EnergyInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class Energy implements EnergyInterface, AttributeInterface{

	private double energy;
	private double maxEnergy;
	private DoubleProperty energyPercentage;

	public Energy(){
		energyPercentage = new SimpleDoubleProperty();
		setMaxEnergy(100);
		assignEnergy(100);
	}
	public Energy (double energy) {
		energyPercentage = new SimpleDoubleProperty();
		setMaxEnergy(energy);
		assignEnergy(energy);
	}
	
	public double getEnergy() {
		return energy;
	}

	public void useEnergy(double amount) {
		assignEnergy(energy-amount);
	}

	public void chargeEnergy(double amount) {
		assignEnergy(energy+amount);
	}
	
	public DoubleProperty retrieveEnergyPercentage() {
		return energyPercentage;
	}
	
	public double getEnergyPercentage() {
		return energyPercentage.doubleValue();
	}

	
	@Override
	public double getMaxEnergy () {
		return maxEnergy;
	}

	@Override
	public void assignEnergy (double energy) {
		energy=Math.max(Math.min(energy,maxEnergy),0);
		energyPercentage.set(energy/maxEnergy);
	}

	@Override
	public void decreaseMaxEnergy (double amount) {
		setMaxEnergy(maxEnergy-amount);       
	}

	@Override
	public void increaseMaxEnergy (double amount) {
		setMaxEnergy(maxEnergy+amount);
	}

	@Override
	public void setMaxEnergy (double energy) {
		maxEnergy=Math.max(energy,0);
		this.energy = energy;
	}
	
}
