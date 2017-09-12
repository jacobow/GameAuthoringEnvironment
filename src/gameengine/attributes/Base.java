package gameengine.attributes;

import java.util.List;
import java.util.ArrayList;
import gameengine.attributes.interfaces.BaseInterface;
import gameengine.entities.EntityInterface;

/**
 * @author DavidYoon
 *
 */
public class Base implements BaseInterface {

	private boolean seized;
	private List<EntityInterface> entitiesInteracted;
	
	public Base() {
		this.assignSeized(false);
		entitiesInteracted = new ArrayList<EntityInterface>();
	}
	
	@Override
	public boolean isSeized() {
		return seized;
	}

	public void assignSeized(boolean seized) {
		this.seized = seized;
	}

	public List<EntityInterface> retrieveEntitiesInteracted() {
		return entitiesInteracted;
	}

	public void addEntitiesInteracted(EntityInterface interactedEntity) {
		this.entitiesInteracted.add(interactedEntity);
	}
	
	public boolean interacted(){
		return !this.retrieveEntitiesInteracted().isEmpty();
		
	}

	public EntityInterface retrieveSeizer() {
		if (!entitiesInteracted.isEmpty()){
			return entitiesInteracted.get(entitiesInteracted.size()-1);
		}
		return null;
	}

}
