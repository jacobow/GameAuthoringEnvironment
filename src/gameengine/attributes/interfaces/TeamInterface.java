package gameengine.attributes.interfaces;


public interface TeamInterface extends AttributeInterface {
    public String getTeam();
    
    /**
     * 
     * @param team The entity's new team
     */
    public void setTeam(String team);
    
    /**
     * 
     * @param team The team of the other entity to check
     * @return If both entities are on the same team
     */
    public boolean checkSameTeam(String team);
}

