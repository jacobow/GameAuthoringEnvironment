package gameplayer.filereading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import configuration.MenuLanguage;
import gamedata.data.GameData;
import gamedata.exceptions.FileIOError;
import gamedata.exceptions.LoadWorldException;
import gamedata.fileIO.FilePath;
import gamedata.fileIO.XMLFileIO;

/**
 * Retrieves and loads all of the previously saved games for a given user in a specific game type
 * @author Brian
 *
 */
public class FindSavedGames extends FindGames implements FindSavedGamesInterface{

    public FindSavedGames(){
        super();
    }

    public List<GameData> findSavedGames(String gameType, String userName){
        findGames(FilePath.GAMEDATA.filepath() + gameType + File.separator + userName, gameType, userName);
        return myGames;
    }

    @Override
    protected void loadGames(String gameType, String userName, String gameName){
        if(gameName.contains(".")) {
            String newGameName = gameName.split("\\.")[0];
            if (!newGameName.equals(gameType)){
                XMLFileIO xmlFileIO = new XMLFileIO();
                try {
                    GameData game = xmlFileIO.loadGame(gameType, userName, newGameName);
                    myGames.add(game);
                }
                catch (LoadWorldException e) {
                    FileIOError loadLevelError = new FileIOError(MenuLanguage.getInstance().getValue("ErrorLoadGame"));
                    loadLevelError.displayMessage();
                }
            }
        }

    }
}
