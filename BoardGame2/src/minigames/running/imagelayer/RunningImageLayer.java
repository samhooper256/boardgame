package minigames.running.imagelayer;

import java.util.*;
import java.util.function.IntToLongFunction;

import base.panes.ImagePane;
import game.MainScene;
import game.board.Board;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.running.Running;
import minigames.running.imagelayer.obstacles.*;
import players.Player;

public class RunningImageLayer extends MinigameImageLayer {
	
	private final List<Obstacle>[] obstacles;
	
	private static final double OBSTACLE_SPAWN_X = MainScene.DEFAULT_WIDTH * 1.2;
	
	/** nanos */
	private static final long FIRST_DELAY = (long) 2e9; 
	/** seconds */
	private static final long DELAY_CONSTANT = 5L; //in seconds
	/** nanos. */
	private static final IntToLongFunction DELAY_PRECEDING =
			i -> (long) ((1e9) * (DELAY_CONSTANT - Math.pow(Math.E, -2) * Math.log(i)));
	
	private int obstacleIndex;
	private long obstacleDelay, obstacleTimer;
	
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
		for(Runner r : Runner.LIST)
			r.reset();
		int playerCount = gamePane().players().size();
		for(List<Obstacle> o : obstacles) {
			removeAll(o);
			o.clear();
		}
		ObstacleGenerator g = ObstacleGenerator.randomAboveGround();
		for(int p : gamePane().players()) {
			Obstacle o = g.create(p, 0);
			o.setIdealX(1000);
			o.alignFor(playerCount);
			obstaclesFor(p).add(o);
			add(o);
		}
		obstacleIndex = 1;
		obstacleDelay = FIRST_DELAY;
	}

	private <T extends ImagePane & Alignable> void setupFromList(List<T> list) {
		removeAll(list);
		for(int i = 0, playerCount = gamePane().players().size(); i < playerCount; i++) {
			list.get(i).alignFor(playerCount);
			add(list.get(i));
		}
	}
	
	private void generateObstacle() {
		ObstacleGenerator g = ObstacleGenerator.randomAboveGround();
		for(int p : gamePane().playersRemaining()) {
			Obstacle o = g.create(p, obstacleIndex);
			o.setIdealX(OBSTACLE_SPAWN_X);
			o.alignFor(gamePane().players().size());
			obstaclesFor(p).add(o);
			add(o);
		}
		obstacleIndex++;
		obstacleDelay = DELAY_PRECEDING.applyAsLong(obstacleIndex);
		obstacleTimer = 0;
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
		for(int i = gamePane().playersRemaining().size() - 1; i >= 0; i--) {
			int r = gamePane().playersRemaining().get(i);
			Runner.get(r).update(diff);
			for(Obstacle o : obstaclesFor(r))
				o.update(diff);
		}
		obstacleTimer += diff;
		if(obstacleTimer >= obstacleDelay) {
			obstacleDelay -= obstacleDelay;
			generateObstacle();
		}
	}
	
	public void kill(Runner r) {
		trash(r);
	}
	
	/** Not {@link Player#validate(int) validated}. */
	public List<Obstacle> obstaclesFor(int player) {
		return obstacles[player - 1];
	}
	
	@Override
	public Running gamePane() {
		return (Running) super.gamePane();
	}
	
}
