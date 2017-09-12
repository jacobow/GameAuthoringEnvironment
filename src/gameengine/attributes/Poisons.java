package gameengine.attributes;

import gameengine.attributes.interfaces.PoisonInterface;

/**
 * 
 * @author vincent
 *
 */
public class Poisons implements PoisonInterface{
    private double myDamage;
    private double myTotalTime;

    public Poisons(){
    	setDamage(0.0);
    	setTotalTime(0.0);
    }
    public Poisons (double damage, double time) {
        setDamage(damage);
        setTotalTime(time);
    }

    @Override
    public double getDamage () {
        return myDamage;
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

}
