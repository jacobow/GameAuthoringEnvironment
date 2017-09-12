package inpututilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import org.lwjgl.input.Controller;

public class ControllerInput {
    public ControllerInput(){
    }
    
    
    public Set<Integer> getButtonsPressed(Controller controller){
        //note that RT and LT do not count as buttons for xbox
        controller.poll();
        int numButtons=controller.getButtonCount();
        Set<Integer> myButtons=new HashSet<Integer>();
        for(int i=0;i<numButtons;i++){
            if(controller.isButtonPressed(i)){
                myButtons.add(i);
            }
        }
        return myButtons;
    }
    
    public Set<Integer> getAxisPressed(Controller controller){
        //note that RT and LT do not count as buttons for xbox
        controller.poll();
        int numAxes=controller.getAxisCount();
        Set<Integer> myAxes=new HashSet<Integer>();
        for(int i=0;i<numAxes;i++){
            if(controller.getAxisValue(i)!=0){
                myAxes.add(i);
            }
        }
        return myAxes;
    }
}
