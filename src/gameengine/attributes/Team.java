package gameengine.attributes;

import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.attributes.interfaces.TeamInterface;

/**
 * 
 * @author walker
 * @author DavidYoon
 *
 */
public class Team implements TeamInterface, AttributeInterface{
    private String myTeam;
    
    public Team(){
    	myTeam = "";
    }
    
    public Team (String team) {
        setTeam(team);
    }

    @Override
    public String getTeam () {
        return myTeam;
    }

    @Override
    public void setTeam (String team) {
        myTeam=team;
    }

    public boolean checkSameTeam(String team){
    	return myTeam.equals(team);
    }
    
    
}
