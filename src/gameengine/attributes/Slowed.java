package gameengine.attributes;

import gameengine.attributes.interfaces.SlowedInterface;

/**
 * 
 * @author vincent
 * @author walker
 *
 */
public class Slowed implements SlowedInterface{
    private double mySpeedModifier;
    private double myTotalTime;
    private double myTimeRemaining;

    public Slowed(){
    	mySpeedModifier=1.0;
    	myTotalTime=0.0;
    }
    
    public Slowed (double speedModifier, double time) {
        setSpeedModifier(speedModifier);
        setTotalTime(time);
        myTimeRemaining=time;
    }

    @Override
    public double getSpeedModifier () {
        return mySpeedModifier;
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
    public void setSpeedModifier (double speedModifier) {
    	System.out.println("Speed Modifier Set: " + speedModifier);
        mySpeedModifier=speedModifier;
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
