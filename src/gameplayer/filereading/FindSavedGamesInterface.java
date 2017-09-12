package gameplayer.filereading;

import java.util.List;

import gamedata.data.GameData;

/**
 * Interface specifying the method needed to be called in order to get all of the saved games
 * @author Brian
 *
 */
public interface FindSavedGamesInterface {

    /**
     * 
     * @param gameName - Name of the type of game that you are searching for
     * @param userName - Name of the current user
     * @return A list of all the previously saved games of that type for that user
     */
    List<GameData> findSavedGames (String gameName, String userName);
}
