package gameengine.attributes.interfaces;

public interface BlockedInterface extends AttributeInterface{

    double retrieveTimeRemaining ();

    double getTotalTime ();

    void setTotalTime (double time);
    
    void assignTimeRemaining(double time);
    
    void decreaseRemainingTime (double time);
    
    boolean retrieveBlock ();

    void assignBlock(boolean block);
}

