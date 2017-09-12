package gamedata.fileIO;

import java.util.Optional;
import javafx.scene.control.TextInputDialog;

public class ChooseFileName {

    private String fileName;
    
    public ChooseFileName(String title, String header, String content){
        this.fileName = chooseFileName(title, header, content);
    }
    
    private String chooseFileName(String title, String header, String content){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        else{
            return "defaultGame";
        }
    }
    
    public String getFileName(){
        return this.fileName;
    }
}
