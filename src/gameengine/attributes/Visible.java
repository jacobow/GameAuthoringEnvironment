package gameengine.attributes;

import gameengine.attributes.interfaces.VisibleInterface;

public class Visible implements VisibleInterface{

    private boolean visible;

    public Visible(){
    	assignVisible(true);
    }
    
    public Visible(boolean visible) {
            assignVisible(visible);
    }

    @Override
    public boolean isVisible() {
            return visible;
    }

    @Override
    public void assignVisible(boolean visible) {
            this.visible = visible;
    }
}
