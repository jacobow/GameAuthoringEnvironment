package inpututilities;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class ControllerInitialization {
    private static final String CONTROLLER_LABEL_DETECTION="Controller";   
    private ControllerInput myInput;

    public ControllerInitialization(){
        myInput=new ControllerInput();
    }
    
    /**
     * Initialize set of controllers
     * 
     * @return
     */
    public Set<Controller> initializeControllers(){
        Set<Controller> detectedControllers=new HashSet<Controller>();
        try {
            Controllers.create();

            Controllers.poll();
            for(int i=0;i<Controllers.getControllerCount();i++){
                if(Controllers.getController(i).getName().contains(CONTROLLER_LABEL_DETECTION)){
                    detectedControllers.add((Controller) Controllers.getController(i));
                }
            }
            Controllers.destroy();
           
        }
        catch (LWJGLException e) {
            // TODO Auto-
        	//Catch silently, no detected controllers.
        }
        return detectedControllers;
    }
    
    /**
     * Return controller of which button has been pressed
     * 
     * @param controllers
     * @return 
     */
    public Controller selectController(Set<Controller> controllers){
        while(true){
            Iterator<Controller> controllerIter = controllers.iterator();      

            while(controllerIter.hasNext()){

                Controller currentController = controllerIter.next();
                //logic
                if(myInput.getButtonsPressed(currentController).size()!=0){

                    for(int i=0;i<currentController.getAxisCount();i++){
                        currentController.setDeadZone(i, (float) 0.3);
                        //eliminate accidental movements of axes
                    }
                    return currentController;
                }
            }
        }
    }
}
