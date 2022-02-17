package minigames.running.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import game.board.Board;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.running.Running;
import minigames.running.imagelayer.obstacles.*;
import players.Player;

public class RunningImageLayer extends MinigameImageLayer {
	
	private final List<Obstacle>[] obstacles;
	
	@SuppressWarnings("unchecked")
	public RunningImageLayer() {
		super(MiniTag.RUNNING);
		obstacles = (List<Obstacle>[]) new List<?>[Board.maxPlayerCount()];
		for(int i = 0; i < obstacles.length; i++)
			obstacles[i] = new ArrayList<>();
	}
	
	@Override
	public void startMinigame() {
		setupFromList(Sky.LIST);
		setupFromList(Ground.LIST);
		setupFromList(Runner.LIST);
		int playerCount = gamePane().players().size();
		ObstacleGenerator g = ObstacleGenerator.randomAboveGround();
		for(int p : gamePane().players()) {
			Obstacle o = g.create(p);
			o.setIdealX(1000);
			o.alignFor(playerCount);
			obstaclesFor(p).add(o);
			add(o);
		}
	}

	private <T extends ImagePane & Alignable> void setupFromList(List<T> list) {
		removeAll(list);
		for(int i = 0, playerCount = gamePane().players().size(); i < playerCount; i++) {
			list.get(i).alignFor(playerCount);
			add(list.get(i));
		}
	}
	
	@Override
	public void keyPressedIngame(KeyCode kc) {
		for(int r : gamePane().playersRemaining())
			Runner.get(r).keyPressed(kc);
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		for(int r : gamePane().playersRemaining())
			Runner.get(r).keyReleased(kc);
	}

	@Override
	public void updateIngame(long diff) {
		for(int r : gamePane().playersRemaining()) {
			Runner.get(r).update(diff);
			for(Obstacle o : obstaclesFor(r))
				o.update(diff);
		}
	}
	
	/** Not {@link Player#validate(int) validated}. */
	private List<Obstacle> obstaclesFor(int player) {
		return obstacles[player - 1];
	}
	
	@Override
	public Running gamePane() {
		return (Running) super.gamePane();
	}
	
}
