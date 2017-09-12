package gameengine.systems.coresystems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.lwjgl.input.Controller;

import gameengine.entities.EntityInterface;
import gameengine.interfaces.WorldControllerInterface;
import gameengine.systems.abilities.AbilityInterface;
import gameengine.systems.abilities.NullAbility;
import gameengine.systems.coresystems.interfaces.AbilityManagerInterface;
import gameengine.systems.coresystems.interfaces.InputManagerInterface;
import gameengine.utilities.HandHeld;
import gameengine.utilities.LiveObserver;
import inpututilities.ControllerInput;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class InputManager implements InputManagerInterface, LiveObserver {
    private static final String XBOX_BUTTONS_RESOURCES = "resources/controller/xbox_mappings_input_manager_buttons";
    private static final String PS4_BUTTONS_RESOURCES = "resources/controller/ps_mappings_input_manager_buttons";
    private static final String XBOX_AXIS_RESOURCES = "resources/controller/xbox_mappings_input_manager_axis";
    private static final String PS4_AXIS_RESOURCES = "resources/controller/ps_mappings_input_manager_axis";
    private ResourceBundle xboxMappingsbuttons;
    private ResourceBundle ps4Mappingsbuttons;
    private ResourceBundle xboxMappingsaxis;
    private ResourceBundle ps4Mappingsaxis;
    private Map<KeyCode,AbilityInterface> myKeyToAbilityMap;
    private List<HandHeld> myControllerToAbilityMap;
    private transient Map<Controller,Integer> myControllerToIndexMap;
    private AbilityManagerInterface myAbilityManager;
    private PathingManager myPathingManager;
    private EntityInterface myOnClickEntity;
    private transient ControllerInput myControllerInput;
    private AbilityInterface myOnClickAbility;
    private WorldControllerInterface myWorld;
    /**
     * Sets up the map of buttons to actions from File, initializes hashsets
     */
    public InputManager(WorldControllerInterface controllers, AbilityManagerInterface abilitymanager, PathingManager pather){
    	myWorld = controllers;
    	xboxMappingsbuttons=ResourceBundle.getBundle(XBOX_BUTTONS_RESOURCES);
        xboxMappingsaxis=ResourceBundle.getBundle(XBOX_AXIS_RESOURCES);
        ps4Mappingsbuttons=ResourceBundle.getBundle(PS4_BUTTONS_RESOURCES);
        ps4Mappingsaxis=ResourceBundle.getBundle(PS4_AXIS_RESOURCES);
        myAbilityManager = abilitymanager;
        myKeyToAbilityMap = new HashMap<KeyCode, AbilityInterface>();
        myPathingManager = pather;
        myOnClickEntity = null;
        myControllerInput=new ControllerInput();
        myOnClickAbility = new NullAbility(myOnClickEntity);
        myKeyToAbilityMap = myWorld.getKeyToAbilityMap();
        myControllerToAbilityMap = myWorld.getControllerToAbilityMap();

        myControllerToIndexMap = myWorld.getControllerToIndexMap();

        myWorld.mouseAbilityNotifier().addListener(b -> {
        	myOnClickAbility = myWorld.getOnClickAbility();
        });
        myWorld.getKeyToAbilityMap().registerObserver(this);
        myWorld.getControllerToIndexMap().registerObserver(this);
        myWorld.getControllerToAbilityMap().registerObserver(this);
    }
    public Map<KeyCode,AbilityInterface> getKeyToAbilityMap(){
    	return myKeyToAbilityMap;
    }

    public void swapKeys(KeyCode oldKey, KeyCode newKey){
    	AbilityInterface placeHolder = myKeyToAbilityMap.get(oldKey);
    	myKeyToAbilityMap.remove(oldKey);
    	myKeyToAbilityMap.put(newKey, placeHolder);
    }


    /**
     * @deprecated use the addAbility with no List<EntityInterfaces> method instead
     */
    public void addAbility(KeyCode key, List<EntityInterface> addedEntities, AbilityInterface ability){
        if(myKeyToAbilityMap.containsKey(key)) myKeyToAbilityMap.remove(key);
        myKeyToAbilityMap.put(key, ability);
    }

    public void addAbility(KeyCode key, AbilityInterface ability){
        if(myKeyToAbilityMap.containsKey(key)) myKeyToAbilityMap.remove(key);
        myKeyToAbilityMap.put(key, ability);
    }

    public void keyboardButtonPressed(KeyCode keyPressed) {

        if(myKeyToAbilityMap.containsKey(keyPressed)){
            myAbilityManager.addAbility(myKeyToAbilityMap.get(keyPressed), new double[]{});
        }
    }

    public void keyboardButtonReleased(KeyCode keyReleased) {

        myAbilityManager.removeAbility(myKeyToAbilityMap.get(keyReleased));
    }

    /**
     * @deprecated use mouseButtonReleased
     */
    public void mouseButtonPressed(MouseEvent mousePressedEvent) {
        if(myKeyToAbilityMap.containsKey(KeyCode.F12)){
            myAbilityManager.addAbilityOnce(myKeyToAbilityMap.get(KeyCode.F12),new double[]{mousePressedEvent.getX(),mousePressedEvent.getY()});
        }

    }

    public void mouseButtonReleased(MouseEvent mouseReleasedEvent, EntityInterface myNewClicker){
        setOnClickEntity(myNewClicker);
        mouseButtonReleased(mouseReleasedEvent);
    }

    public void setOnClickEntity(EntityInterface newClickMe){
        myOnClickEntity = newClickMe;
    }

    public void mouseButtonReleased(MouseEvent mouseReleasedEvent) {
        if(myOnClickAbility!=null) myAbilityManager.addAbilityOnce(myOnClickAbility, new double[]{
        		mouseReleasedEvent.getX(),mouseReleasedEvent.getY()});
    }

    public void update(){

        myControllerToIndexMap.forEach((controller, mapIndex)->{
                    ResourceBundle buttons;
                    ResourceBundle axis;

                    if(controller.getName().contains(ps4Mappingsbuttons.getString("ControllerType"))){
                        buttons=ps4Mappingsbuttons;
                        axis=ps4Mappingsaxis;
                    }
                    else{
                        buttons=xboxMappingsbuttons;
                        axis=xboxMappingsaxis;

                    }
                    controller.poll();
                    axisInput(controller,mapIndex, axis);
                  //  buttonInput(controller,mapIndex, buttons);
        });
    }

    private void axisInput(Controller controller, int mapIndex, ResourceBundle controllerbundle){
        Set<Integer> axesPressed=myControllerInput.getAxisPressed(controller);
        for(int i=0;i<controller.getAxisCount();i+=2){
            if((axesPressed.contains(i) || axesPressed.contains(i+1)) &&
                    myControllerToAbilityMap.get(mapIndex).containsKey(controllerbundle.getString(String.valueOf(i)))){
                double[] input= new double[]{0,0};
                if(i==4 ){
                    if(controller.getAxisCount()<=5){
                    //on xbox controllers only z triggers are one axis
                        input[0]=controller.getAxisValue(i);
                        input[1]=0;
                    }
                    else{
                        //on ps controllers z triggers are two axes-need to fix volume
                        input[0]=(controller.getAxisValue(i+1)-controller.getAxisValue(i))/2;
                        input[1]=0;
                    }
                }
                else{
                        input[0]=controller.getAxisValue(i+1);
                        input[1]=controller.getAxisValue(i);
                }
                myAbilityManager.addAbilityOnce(myControllerToAbilityMap.get(mapIndex).get(controllerbundle.getString(String.valueOf(i))),input);

            }
        }
    }

    private void buttonInput(Controller controller, int mapIndex,ResourceBundle controllerbundle){
        myControllerInput.getButtonsPressed(controller).forEach(buttonIndex->{
            if(myControllerToAbilityMap.get(mapIndex).containsKey(controllerbundle.getString(String.valueOf(buttonIndex)))){
                myAbilityManager.addAbilityOnce(myControllerToAbilityMap.get(mapIndex).get(controllerbundle.getString(String.valueOf(buttonIndex))),new double[]{});
            }
        });
    }

    @Override
	public void amend() {
        myKeyToAbilityMap = myWorld.getKeyToAbilityMap();
        myControllerToAbilityMap = myWorld.getControllerToAbilityMap();
        myControllerToIndexMap = myWorld.getControllerToIndexMap();

	}
}


