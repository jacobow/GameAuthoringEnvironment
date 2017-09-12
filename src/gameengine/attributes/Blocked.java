package gameengine.attributes;

import gameengine.attributes.interfaces.BlockedInterface;

public class Blocked implements BlockedInterface{
    private boolean block;
    private double myTotalTime;
    private double myTimeRemaining;
    
    public Blocked(){
    	block =true;
    	setTotalTime(0.0);
    	myTimeRemaining = 0.0;
    }
    
    public Blocked (double time) {
        block=true;
        setTotalTime(time);
        myTimeRemaining=time;
    }
    @Override
    public boolean retrieveBlock () {
        return block;
    }
    
    @Override
    public void assignBlock (boolean block) {
        this.block=block;
    }


    @Override
    public double retrieveTimeRemaining () {
        return myTimeRemaining;
    }

    @Override
    public double getTotalTime () {
        return myTotalTime;
    }
    public void assignTimeRemaining(double time){
    	myTimeRemaining = time;
    }

    @Override
    public void setTotalTime (double time) {
        myTotalTime=time;
    }

    @Override
    public void decreaseRemainingTime (double time) {
        myTimeRemaining-=time;
    }
}
