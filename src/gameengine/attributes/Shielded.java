package gameengine.attributes;

import gameengine.attributes.interfaces.ShieldedInterface;

/**
 * 
 * @author vincent
 * @author walker
 *
 */
public class Shielded implements ShieldedInterface{
    private double myTotalShieldHealth;
    private double myCurrentShieldHealth;
    private double myTotalTime;
    private double myTimeRemaining;
    
    public Shielded(){
        myTotalShieldHealth=0.0;
    	myCurrentShieldHealth = 0.0;
    	myTotalTime = 0.0;
    	myTimeRemaining = 0.0;
    }
    
    public Shielded (double health, double time) {
        myTotalShieldHealth=health;
        myCurrentShieldHealth=health;
        setTotalTime(time);
        myTimeRemaining=time;
    }
    
    @Override
    public double getCurrentHealth () {
        return myCurrentShieldHealth;
    }
    
    @Override
    public void decreaseCurrentHealth (double health) {
        myCurrentShieldHealth-=health;
    }
    
    @Override
    public double getTotalShieldHealth () {
        return myTotalShieldHealth;
    }
    
    @Override
    public void setTotalShieldHealth (double health) {
        myTotalShieldHealth=health;
    }

    @Override
    public void setCurrentShieldHealth (double health) {
        myCurrentShieldHealth=health;
    }


    @Override
    public double getTimeRemaining () {
        return myTimeRemaining;
    }

    @Override
    public double getTotalTime () {
        return myTotalTime;
    }



    @Override
    public void setTotalTime (double time) {
        myTotalTime=time;
    }

    @Override
    public void decreaseRemainingTime (double time) {
        myTimeRemaining-=time;
    }
    
    @Override
    public void setTimeRemaining (double time) {
        myTimeRemaining=time;
        
    }
}
