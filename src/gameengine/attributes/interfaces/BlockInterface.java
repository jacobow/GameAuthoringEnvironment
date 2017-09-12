package gameengine.attributes.interfaces;

public interface BlockInterface extends AttributeInterface{

    double getTotalTime ();

    void setTotalTime (double time);
    
    boolean retrieveBlock ();

    void setBlock(boolean block);
}

