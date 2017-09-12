package gameengine.attributes;

import gameengine.attributes.interfaces.SlowInterface;
import gameengine.attributes.interfaces.SlowedInterface;

/**
 * 
 * @author
 * @author walker
 *
 */
public class Slows implements SlowInterface{
    private double mySpeedModifier;
    private double myTotalTime;

    public Slows(){
    	mySpeedModifier = 1.0;
    	myTotalTime = 0.0;
    }
    
    public Slows (double speedModifier, double time) {
        setSpeedModifier(speedModifier);
        setTotalTime(time);
    }

 

    @Override
    public double getSpeedModifier () {
        return mySpeedModifier;
    }


    @Override
    public double getTotalTime () {
        return myTotalTime;
    }



    @Override
    public void setSpeedModifier (double speedModifier) {
    	System.out.println("\nSpeed Modifier set to: " + speedModifier  + "\n");
        mySpeedModifier=speedModifier;
    }



    @Override
    public void setTotalTime (double time) {
        myTotalTime=time;
        System.out.println("the total time is " + myTotalTime);
    }
}
