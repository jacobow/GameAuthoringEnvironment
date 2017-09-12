package gameengine.attributes;

import gameengine.attributes.interfaces.PoisonedInterface;

/**
 * 
 * @author vincent
 *
 */
public class Poisoned implements PoisonedInterface{
    private double myDamage;
    private double myTotalTime;
    private double myTimeRemaining;
    
    public Poisoned(){
    	myDamage =0.0;
    	myTotalTime =0.0;
    	myTimeRemaining = 0.0;
    }
    
    public Poisoned (double damage, double time) {
        setDamage(damage);
        setTotalTime(time);
        myTimeRemaining=time;
    }

 

    @Override
    public double getDamage () {
        return myDamage;
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
    public void setDamage (double damage) {
        myDamage=damage;
    }



    @Override
    public void setTotalTime (double time) {
        myTotalTime=time;
    }

    @Override
    public void setTimeRemaining (double time) {
        myTimeRemaining=time;
    }
    
    @Override
    public void decreaseRemainingTime (double time) {
        myTimeRemaining-=time;
    }
}
