package gameplayer.filereading;

import java.util.List;
import configuration.MenuLanguage;
import gamedata.data.GameData;
import gamedata.exceptions.FileIOError;
import gamedata.exceptions.LoadWorldException;
import gamedata.fileIO.FilePath;
import gamedata.fileIO.XMLFileIO;

/**
 * 
 * @author Brian
 *
 */
public class FindDefaultGames extends FindGames implements FindDefaultGamesInterface {

    public FindDefaultGames(){
        super();
    }

    public List<GameData> findDefaultGames(){
        findGames(FilePath.GAMEDATA.filepath(), "", "Default");
        return myGames;
    }

    @Override
    protected void loadGames(String gameType, String userName, String gameName){
        XMLFileIO xmlFileIO = new XMLFileIO();
        try {
            GameData game = xmlFileIO.loadGame(gameName, userName, gameName);
            myGames.add(game);
        }
        catch (LoadWorldException e) {
            FileIOError loadLevelError = new FileIOError(MenuLanguage.getInstance().getValue("ErrorLoadLevel"));
            loadLevelError.displayMessage();
        }
    }
}
