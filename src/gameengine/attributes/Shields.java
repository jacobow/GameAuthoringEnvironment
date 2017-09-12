package gameengine.attributes;

import gameengine.attributes.interfaces.ShieldInterface;
import gameengine.attributes.interfaces.ShieldedInterface;

/**
 * 
 * @author vincent
 * @author walker
 *
 */
public class Shields implements ShieldInterface{
    private double myCurrentShieldHealth;
    private double myTotalTime;
    public Shields(){
    	myCurrentShieldHealth = 0.0;
    	myTotalTime = 0.0;
    }
    public Shields (double health, double time) {
        myCurrentShieldHealth=health;
        setTotalTime(time);
    }
    @Override
    public double getTotalHealth () {
        return myCurrentShieldHealth;
    }
    
    @Override
    public void setTotalHealth (double health) {
        myCurrentShieldHealth=health;
    }
    @Override
    public double getTotalTime () {
        return myTotalTime;
    }


    @Override
    public void setTotalTime (double time) {
        myTotalTime=time;
    }
}
