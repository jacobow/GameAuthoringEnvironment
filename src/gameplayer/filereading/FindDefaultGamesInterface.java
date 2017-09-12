package gameplayer.filereading;

import java.util.List;

import gamedata.data.GameData;

/**
 * Interface specifying the method needed to be called in order to find all of the default games
 * @author Brian
 *
 */
public interface FindDefaultGamesInterface {

    /**
     * @return A list of all the "default" created games that have been built in the authoring environment
     */
	List<GameData> findDefaultGames();
}
