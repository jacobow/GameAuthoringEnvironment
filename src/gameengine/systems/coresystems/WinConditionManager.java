package gameengine.systems.coresystems;

import java.util.HashSet;
import java.util.Set;

import gameengine.attributes.Player;
import gameengine.attributes.Team;
import gameengine.entities.EntityInterface;
import gameengine.interfaces.WinConditionSubject;
import gameengine.systems.coresystems.interfaces.SystemsInterface;
import gameengine.systems.coresystems.interfaces.WinConditionManagerInterface;
import gameengine.systems.wincondition_handlers.WinConditionInterface;
import gameengine.utilities.LiveObserver;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author DavidYoon
 *
 */
public class WinConditionManager implements SystemsInterface, WinConditionManagerInterface, LiveObserver {

	private Set<WinConditionInterface> winConditions;
	private BooleanProperty gameOver;
	private EntityInterface winner;
	private Set<EntityInterface> myPlayers;
	private String winningTeam;
	private DoubleProperty gameTime = new SimpleDoubleProperty();
	private WinConditionSubject myWorld;

    public WinConditionManager (WinConditionSubject subject) {
    	myWorld = subject;
    	gameOver = new SimpleBooleanProperty(false);
    	winConditions = new HashSet<WinConditionInterface>();
		myPlayers = new HashSet<EntityInterface>();
		myWorld.getWinConditions().registerObserver(this);
		myWorld.getEntities().registerObserver(this);
		amend();
    }


	public void update (double timeElapsed) {
        winConditions.forEach((winCondition) -> {
        	//weigh the win conditions or store them in a sorted list according to their priority
        	if (winCondition.isGameOver() && !gameOver.get()){
        		setWinner(winCondition.getWinner());
        		recordVictoryandDefeat();
        		gameOver.set(true);
        	}
        });

        trackTime(timeElapsed);
    }

	private void trackTime(double timeElapsed) {
		getGameTime().set(gameTime.get()+ timeElapsed);
	}


	private void recordVictoryandDefeat() {
		if (winner !=null && winner.containsAttribute(Team.class)){
			winningTeam = winner.getAttribute(Team.class).getTeam();
		}
		myPlayers.forEach((player)-> {
			checkTeam(player);
		});
	}

	private void checkTeam(EntityInterface player) {
		if (player.containsAttribute(Team.class)){
			if (player.getAttribute(Team.class).checkSameTeam(winningTeam)){
				player.getAttribute(Player.class).assignWinStatus(1);
			}
			else {
				player.getAttribute(Player.class).assignWinStatus(-1);
			}
		}
	}

	public void register(WinConditionInterface winCondition) {
		winConditions.add(winCondition);
	}

	public BooleanProperty isGameOver() {
		return gameOver;
	}

	public void setGameOver(BooleanProperty gameOver) {
		this.gameOver = gameOver;
	}

	public EntityInterface getWinner() {
		return winner;
	}

	private void setWinner(EntityInterface winner) {
		this.winner = winner;
	}

	public DoubleProperty getGameTime() {
		return gameTime;
	}

	@Override
	public void amend() {
		myPlayers.clear();
		myWorld.getEntities().forEach(e->{
			if(e.containsAttribute(Player.class)) myPlayers.add(e);
		});
		winConditions = myWorld.getWinConditions();
	}
}
