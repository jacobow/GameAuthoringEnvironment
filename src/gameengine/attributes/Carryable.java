package gameengine.attributes;

import gameengine.attributes.interfaces.CarryableInterface;

public class Carryable implements CarryableInterface{
    private boolean carried;
    private boolean dropped;
    
    public Carryable(){
    	assignCarryable(false);
    	assignDropped(true);
    }
    
    public Carryable (boolean carryable) {
        assignCarryable(carryable);
    }

    @Override
    public boolean beingCarried () {
        return carried;
    }

    @Override
    public void assignCarryable (boolean carryable) {

    }
    
    @Override
    public boolean isDropped () {
        return dropped;
    }

    @Override
    public void assignDropped (boolean dropped) {
        this.dropped=dropped;   
        
    }
}
