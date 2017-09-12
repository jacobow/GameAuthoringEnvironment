package gamedata.fileIO;

import java.io.File;

public enum FilePath {
    
    XML (".xml"),
    GAMEDATA (System.getProperty("user.dir") + File.separator + "src" + File.separator + 
              "resources" + File.separator + "gamefiles" + File.separator + "gamedata" + File.separator),
    PROFILEDATA(System.getProperty("user.dir") + File.separator + "src" + File.separator + 
              "resources" + File.separator + "gamefiles" + File.separator + "profiles" + File.separator
              + "Profiles.xml");
    
    private final String filePath;
    
    private FilePath (String filePath) {
        this.filePath = filePath;
    }
    
    public String filepath(){
        return this.filePath;
    }
    
}
