package gameengine.attributes;

import gameengine.attributes.interfaces.BlockInterface;
import gameengine.attributes.interfaces.BlockedInterface;

public class Blocks implements BlockInterface{
    private boolean block;
    private double myTotalTime;
    
    public Blocks(){
    	block = false;
    	setTotalTime(0.0);
    }
    public Blocks (double time) {
        block=true;
        setTotalTime(time);
    }
    @Override
    public boolean retrieveBlock () {
        return block;
    }
    
    @Override
    public void setBlock (boolean block) {
        this.block=block;
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
