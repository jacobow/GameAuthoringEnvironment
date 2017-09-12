package gameengine.entities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.systems.abilities.AbilityInterface;
import javafx.scene.input.KeyCode;

/**
 *
 * @author jacob
 * @author walker
 * @author david
 *
 */
public class Entity implements EntityInterface {
	private String myID;
	private List<AttributeInterface> myAttributes;
	private Map<AbilityInterface, KeyCode> myAbilityKeys;
	private Map<AbilityInterface, String> myAbilityControllerKeys;
	private Map<AbilityInterface, Integer> myAbilityControllers;
	private Map<Class<? extends AttributeInterface>, AttributeInterface> myAttributeMap;
	private Map<Class<? extends AbilityInterface>, AbilityInterface> myAbilitiesMap;

	public Entity(String identification, List<AttributeInterface> attributes){
		myID = identification;
		myAttributes = new ArrayList<AttributeInterface>();
		myAbilityKeys = new HashMap<AbilityInterface, KeyCode>();
		myAttributeMap = new HashMap<Class<? extends AttributeInterface>, AttributeInterface>();
		myAbilitiesMap = new HashMap<Class<? extends AbilityInterface>, AbilityInterface>();
		myAbilityControllerKeys = new HashMap<AbilityInterface, String>();
		myAbilityControllers = new HashMap<AbilityInterface, Integer>();
		for(AttributeInterface attribute: attributes) addAttribute(attribute);
	}

	public List<AttributeInterface> getAttributesList() {
		return myAttributes;
	}

	public Map<AbilityInterface, KeyCode> getAbilitiesList(){
		return myAbilityKeys;
	}

	public boolean containsAttribute(Class<? extends AttributeInterface> attribute) {
		return myAttributeMap.containsKey(attribute);
	}

	@Deprecated
	public Map<Class<? extends AttributeInterface>, AttributeInterface> getAttributeMap() {
		return myAttributeMap;
	}

	public Map<Class<? extends AbilityInterface>, AbilityInterface> getAbilityMap(){
		return myAbilitiesMap;
	}

	public void addAttribute(AttributeInterface attribute) {
		myAttributes.add(attribute);
		myAttributeMap.put(attribute.getClass(), attribute);
	}

	public void removeAttribute(AttributeInterface attribute) {
		myAttributes.remove(attribute);
		myAttributeMap.remove(attribute.getClass());
	}

	public void addAbility(AbilityInterface ability, KeyCode key){
		myAbilityKeys.put(ability, key);
		myAbilitiesMap.put(ability.getClass(), ability);
	}

	public void addAbility(AbilityInterface ability, String key, int controller){
		myAbilityControllerKeys.put(ability, key);
		myAbilityControllers.put(ability, controller);
	}

	public void clear() {
		myAttributes.clear();
		myAttributeMap.clear();
	}
	public String getID() {
		return myID;
	}

	public void setID(String id) {
		myID = id;
	}

	@SuppressWarnings("unchecked")
	public <T extends AttributeInterface> T getAttribute(Class<T> attributeClass) {
		return (T)myAttributeMap.get(attributeClass);
	}

}