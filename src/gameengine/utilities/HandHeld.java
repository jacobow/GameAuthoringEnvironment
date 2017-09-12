package gameengine.utilities;

import java.util.HashMap;
import java.util.Map;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import gameengine.systems.abilities.AbilityInterface;

public class HandHeld {

        @XStreamAlias("myControls")
	private Map<String, AbilityInterface> myControls;

	public HandHeld(){
	    myControls=new HashMap<String, AbilityInterface>();
	}

	public void addAbility(String key, AbilityInterface ability){
		myControls.put(key, ability);
		System.out.println("Key: "+key+" Abilities: "+ability.getClass().getSimpleName());

	}

	public void printScreen(){
		myControls.forEach((l,m)->{
			System.out.println(l);
			System.out.println(m.getClass().getName());
		});
	}
	public boolean containsKey(String key){
	    return myControls.containsKey(key);
	}

    public AbilityInterface get(String key){
        return myControls.get(key);
    }

    public int size(){
    	return myControls.size();
    }
}
