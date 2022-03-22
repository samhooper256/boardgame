package minigames.running.imagelayer;

import java.util.*;
import java.util.function.IntToLongFunction;

import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;
import game.board.Board;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.running.Running;
import minigames.running.imagelayer.obstacles.*;
import players.Player;
import utils.RNG;

public class RunningImageLayer extends MinigameImageLayer {
	
	private static final double MIN_DIST_BETWEEN_OBSTACLES = Images.SPRITE_WIDTH * 2; //in pixels.
	private static final double OBSTACLE_SPAWN_X = MainScene.DEFAULT_WIDTH * 1.5;
	/** An {@code int} for use in {@link RNG#intInclusive(int, int)}. */
	private static final int OBSTACLE_X_STRAY = (int) (MainScene.DEFAULT_WIDTH * .5 - 1);
	
	/** nanos */
	private static final long FIRST_DELAY = (long) 1e9; 
	/** seconds */
	private static final long DELAY_CONSTANT = 4L; //in seconds
	/** nanos. */
	private static final IntToLongFunction DELAY_PRECEDING =
			i -> (long) ((1e9) * (DELAY_CONSTANT - Math.pow(Math.E, -1) * Math.log(i)));
	
	private final Deque<Obstacle>[] obstacles;
	private final Deque<Ground>[] grounds;
	private final List<RunningKeyIcon> keyIcons;
	
	private int obstacleIndex;
	private long obstacleDelay, obstacleTimer;
	
	@SuppressWarnings("unchecked")
	public RunningImageLayer() {
		super(MiniTag.RUNNING);
		grounds = (Deque<Ground>[]) new Deque<?>[Board.maxPlayerCount()];
		obstacles = (Deque<Obstacle>[]) new Deque<?>[Board.maxPlayerCount()];
		for(int i = 0; i < Board.maxPlayerCount(); i++) {
			obstacles[i] = new ArrayDeque<>();
			grounds[i] = new ArrayDeque<>();
		}
		keyIcons = new ArrayList<>(Player.maxCount());
		for(int i = 1; i <= Player.maxCount(); i++)
			keyIcons.add(new RunningKeyIcon(i));
	}
	
	@Override
	public void startMinigame() {
		setupFromList(Sky.LIST);
		setupGrounds();
		setupFromList(Runner.LIST);
		setupFromList(keyIcons);
		for(Runner r : Runner.LIST)
			r.reset();
		int playerCount = gamePane().players().size();
		for(Deque<Obstacle> o : obstacles) {
			removeAll(o);
			o.clear();
		}
		ObstacleGenerator g = ObstacleGenerator.randomAboveGround();
		for(int p : gamePane().players()) {
			Obstacle o = g.create(p, 1);
			o.setIdealX(1000);
			o.alignFor(playerCount);
			obstaclesFor(p).add(o);
			add(o);
		}
		obstacleIndex = 2;
		obstacleDelay = FIRST_DELAY;
	}

	private <T extends ImagePane & Alignable> void setupFromList(List<T> list) {
		removeAll(list);
		for(int i = 0, playerCount = gamePane().players().size(); i < playerCount; i++) {
			list.get(i).alignFor(playerCount);
			add(list.get(i));
		}
	}
	
	private void setupGrounds() {
		for(int i = 0, pc = gamePane().players().size(); i < pc; i++) {
			Deque<Ground> deque = grounds[i];
			removeAll(deque);
			deque.clear();
			for(int v = 1; v <= Ground.NUM_VARIANTS; v++) {
				Ground g = addGround(i + 1, v);
				g.setIdealX((v - 1) * MainScene.DEFAULT_WIDTH);
			}
			Ground last = addGround(i + 1, 1);
			last.setIdealX(Ground.NUM_VARIANTS * MainScene.DEFAULT_WIDTH);
		}
	}
	
	private Ground addGround(int number, int variant) {
		Ground g = new Ground(number, variant);
		g.alignFor(gamePane().players().size());
		groundsFor(number).addLast(g);
		add(g);
		return g;
	}
	
	private void generateObstacle() {
		int triesLeft = 5;
		outer:
		while(true) {
			double x = OBSTACLE_SPAWN_X + RNG.intInclusive(-OBSTACLE_X_STRAY, OBSTACLE_X_STRAY);
			ObstacleGenerator g = ObstacleGenerator.randomAboveGround();
			if(triesLeft > 0) {
				double width = g.create(1, -1).getIdealWidth();
				for(Obstacle o : obstaclesFor(gamePane().playersRemaining().get(0))) {
					if(	x >= o.getIdealX() - width - MIN_DIST_BETWEEN_OBSTACLES &&
						x <= o.getIdealX() + o.getIdealWidth() + MIN_DIST_BETWEEN_OBSTACLES) {
						triesLeft--;
						continue outer;
					}
				}
			}
			for(int p : gamePane().playersRemaining()) {
				Obstacle o = g.create(p, obstacleIndex);
				o.setIdealX(x);
				o.alignFor(gamePane().players().size());
				obstaclesFor(p).add(o);
				add(o);
			}
			break outer;
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
			Deque<Obstacle> deque = obstaclesFor(r);
			for(Obstacle o : deque)
				o.update(diff);
			while(!deque.isEmpty() && deque.getFirst().getIdealRightX() < 0)
				deque.removeFirst();
		}
		obstacleTimer += diff;
		if(obstacleTimer >= obstacleDelay) {
			obstacleDelay -= obstacleDelay;
			generateObstacle();
		}
		updateGrounds(diff);
	}
	
	private void updateGrounds(long diff) {
		double sec = diff / 1e9;
		for(int p : gamePane().playersRemaining()) {
			Deque<Ground> deque = groundsFor(p);
			for(Ground g : deque)
				g.setIdealX(g.getIdealX() + sec * Running.get().velocity());
			if(deque.getFirst().getIdealRightX() < 0) {
				deque.removeFirst();
				Ground currentLast = deque.getLast();
				Ground newLast = addGround(p, Ground.nextVariant(currentLast.variant()));
				newLast.setIdealX(currentLast.getIdealRightX());
			}
		}
	}
	
	public void kill(Runner r) {
		trash(r);
	}
	
	/** Not {@link Player#validate(int) validated}. */
	public Deque<Obstacle> obstaclesFor(int player) {
		return obstacles[player - 1];
	}
	
	/** Not {@link Player#validate(int) validated}. */
	public Deque<Ground> groundsFor(int player) {
		return grounds[player - 1];
	}
	
	@Override
	public Running gamePane() {
		return (Running) super.gamePane();
	}
	
}
