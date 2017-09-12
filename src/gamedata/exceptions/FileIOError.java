package gamedata.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FileIOError {

    private Alert error;
    
    
    public FileIOError(String errorMessage){
        error = new Alert(AlertType.ERROR);
        error.setTitle("I/O Error!");
        error.setContentText(errorMessage);        
    }
    
    public FileIOError(String errorType, String errorMessage){
        this(errorMessage);
        error.setHeaderText(errorType);
    }
    
    public void displayMessage(){
        error.show();
    }
}
