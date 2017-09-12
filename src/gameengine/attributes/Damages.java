package gameengine.attributes;

import gameengine.attributes.interfaces.DamageInterface;

public class Damages implements DamageInterface{
    private double damage;
    private double myTotalTime;
    private double myTimeRemaining;
    
    public Damages(){
    	setDamageDone(0.0);
        setTotalTime(-1);
        assignTimeRemaining(-1);
    }
    
    public Damages (double damage) {
        setDamageDone(damage);
        setTotalTime(-1);
        assignTimeRemaining(-1);
    }
    
    public Damages(double damage, double time){
        setDamageDone(damage);
        setTotalTime(time);
        assignTimeRemaining(time);
    }

    public double getDamageDone () {
        return damage;
    }
    
   
    public void increaseDamage (double damage) {
        setDamageDone(this.damage+damage);
    }

   
    public void decreaseDamage (double damage) {
        setDamageDone(this.damage-damage);
    }
    
   
    public void setDamageDone (double damage) {
        this.damage=damage;
    }
    
    @Override
    public double retrieveTimeRemaining () {
        return myTimeRemaining;
        
    }
    
    @Override
    public double getTotalTime () {
        return myTotalTime;
    }



    @Override
    public void setTotalTime (double time) {
        myTotalTime=time;
        myTimeRemaining=time;
    }

    @Override
    public void decreaseRemainingTime (double time) {
        myTimeRemaining-=time;
    }
    
    @Override
    public void assignTimeRemaining (double time) {
        myTimeRemaining=time;
        
    }
}