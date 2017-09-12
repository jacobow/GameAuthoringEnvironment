package gameplayer.filereading;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import gamedata.data.GameData;
import gamedata.data.LevelData;
import gamedata.fileIO.FilePath;
import gamedata.fileIO.XMLFileIO;

/**
 * This superclass specifies the method by which gamedata files can be found in different directories and loaded
 * in order to show what games are currently available. It is extended to provide file-searching for both the default
 * games and their saved versions. 
 * @author Brian
 *
 */
public abstract class FindGames {
    
    List<GameData> myGames;

    public FindGames(){
        myGames = new ArrayList<GameData>();
    }


    /**
     * 
     * @param filePath - The global filepath required to search a particular folder
     * @param gameType - The name of the game's type (the original game's name, not the saved version)
     * @param userName - current user's name
     * @return A list of GameData objects that are available to be played
     */
    protected List<GameData> findGames(String filePath, String gameType, String userName){
        myGames = new ArrayList<GameData>();
        File games = new File(filePath);
        if (games.isDirectory()){
            for (File f : games.listFiles()) {
                String defaultName = f.toString();
                String gameName = defaultName.substring(defaultName.lastIndexOf(File.separator) + 1);
                loadGames(gameType, userName, gameName);
            }
        }
        return myGames;
    }

    /**
     * 
     * @param packageName - folder to search for game type in
     * @param userName - current User's name
     * @param gameName - name of game (either default or saved)
     * 
     * Loads the current available games into the myGames object
     */
    protected abstract void loadGames(String packageName, String userName, String gameName);
}

