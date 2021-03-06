package game.board.imagelayer;

import java.util.*;

import base.*;
import base.panes.*;
import events.*;
import fxutils.*;
import game.board.*;
import game.helper.*;
import javafx.geometry.Point2D;
import medals.*;
import players.Player;
import tiles.*;

/** uses packets 0, 1 (events), and 2 (helper). */
public class BoardImageLayer extends AbstractImageLayer implements Updatable {

	private static final long HELPER_SHOW_DELAY = (long) 5e9;
	
	private final Ring[] rings; //index i is the ring for player i;
	private final Helper helper;
	
	/** [total player count][player number] */
	private final List<ImagePane>[][] medalAreaImages;
	
	private List<Tile> tiles;
	private WalkAnimation currentWalk;
	private long elapsed;
	
	@SuppressWarnings("unchecked")
	public BoardImageLayer() {
		helper = new Helper();
		this.rings = new Ring[Board.maxPlayerCount() + 1];
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			rings[i] = new Ring();
		medalAreaImages = (List<ImagePane>[][]) new List[Player.maxCount() + 1][];
		for(int pc = 2; pc <= Player.maxCount(); pc++)
			medalAreaImages[pc] = (List<ImagePane>[]) new List[pc + 1];
		createMedalAreas();
		tiles = Collections.emptyList();
	}
	
	/** Should only be called once. */
	public void init() {
		add(new ImagePane(Images.BOARD_BACKGROUND));
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			add(rings[i]);
		add(DieGlow.get()); //RollableDie/FixedDies are added by BoardAnimations.
		add(1, EventBackground.get());
		for(int i = 1; i <= Player.maxCount(); i++)
			rings[i].lockCoordinatesTo(Player.get(i));
		add(2, helper);
		helper.pointTo(RollableDie.get());
	}
	
	public void start() {
		removeAll(tiles);
		tiles = generateTiles();
		placeTiles();
		clearTileReferencesToPlayers();
		removePlayers();
		addPlayers();
		movePlayersToStart();
		removeMedalAreas();
		addMedalAreas();
		EventBackground.get().fader().disappear();
		removeRings();
		addRings();
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			rings[i].fader().disappear();
		rings[1].fader().fadeIn();
		currentWalk = null;
		helper.setVisible(false);
		elapsed = 0;
	}
	
	private void placeTiles() {
		for(int i = 0; i < Board.TILE_COUNT; i++) {
			Tile t = tiles.get(i);
			Point2D point = Main.POINTS.get(i);
			t.setIdealCoords(point);
			add(t);
		}
	}
	
	private void clearTileReferencesToPlayers() {
		for(Tile t : tiles)
			t.players().clear();
	}

	private void addPlayers() {
		for(int i = 1; i <= gamePane().playerCount(); i++)
			add(Player.get(i));
	}
	
	private void removePlayers() {
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			remove(Player.get(i));
	}
	
	private void movePlayersToStart() {
		for(int i = 1; i <= gamePane().playerCount(); i++)
			movePlayer(Player.get(i), tileAt(0));
	}
	
	private List<Tile> generateTiles() {
		List<Tile> order = new ArrayList<>(Board.TILE_COUNT);
		order.add(StartTile.get());
		for(TileSection section : TileSection.createSectionList())
			order.addAll(section.randomOrder());
		return order;
	}

	private void removeRings() {
		for(int i = 1; i <= Player.maxCount(); i++)
			remove(rings[i]);
	}
	
	private void addRings() {
		for(int i = 1; i <= Player.maxCount(); i++)
			add(rings[i]);
	}
	
	private void createMedalAreas() {
		for(int pc = 2; pc <= Player.maxCount(); pc++) {
			for(int p = 1; p <= pc; p++) {
				MedalCoords coords = MedalCoords.forPlayer(pc, p);
				ImagePane pip = new ImagePane(Images.player(p));
				MedalIcon gold = new MedalIcon(Medal.GOLD, .25);
				MedalIcon silver = new MedalIcon(Medal.SILVER, .25);
				MedalIcon bronze = new MedalIcon(Medal.BRONZE, .25);
				pip.setIdealCenter(coords.player());
				gold.setIdealCenter(coords.gold());
				silver.setIdealCenter(coords.silver());
				bronze.setIdealCenter(coords.bronze());
				medalAreaImages[pc][p] = Arrays.asList(pip, gold, silver, bronze);
			}
		}
	}
	
	private void removeMedalAreas() {
		for(List<ImagePane>[] row : medalAreaImages)
			if(row != null)
				for(List<ImagePane> list : row)
					if(list != null)
						removeAll(list);
	}
	
	private void addMedalAreas() {
		int pc = gamePane().playerCount();
		for(int p = 1; p <= pc; p++)
			addAll(medalAreaImages[pc][p]);
	}
	
	@Override
	public void updatePane(long diff) {
		elapsed += diff;
		DieGlow.get().update(diff);
		if(!HelperInfo.get().hasRolledRollableDie() && elapsed >= HELPER_SHOW_DELAY) {
			helper.setVisible(true);
			helper.update(diff);
		}
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			rings[i].update(diff);
		RollableDie.get().update(diff);
		if(currentWalk != null)
			currentWalk.update(diff);
	}
	
	public void notifyRollableDieRolled() {
		helper.setVisible(false);
		HelperInfo.get().setRolledRollableDie();
	}
	
	public void showSimpleTextEvent(SimpleTextEvent event) {
		EventBackground.get().fader().fadeIn();
	}
	
	public void showComplexEvent(ComplexEvent event) {
		EventBackground.get().fader().fadeIn();
		List<ImagePane> ips = event.imagePanes();
		for(ImagePane ip : ips)
			if(ip instanceof Fadeable)
				((Fadeable) ip).fader().fadeIn();
		addAll(1, ips);
	}
	
	public boolean requestEventFinish() {
		Fader f = EventBackground.get().fader();
		if(f.isFadingIn() || f.isFadingOut())
			return false;
		Event event = Board.get().currentEvent();
		if(event instanceof ComplexEvent) {
			ComplexEvent ce = (ComplexEvent) event;
			for(ImagePane ip : ce.imagePanes())
				if(ip instanceof Fadeable)
					((Fadeable) ip).fader().fadeOutAndHide();
		}
		f.fadeOutAndHide();
		return true;
	}
	
	/** Should only be called by {@link Board}. Assumes {@link Board#currentEvent()} has not yet been set to
	 * {@code null}. */
	public void eventFinished() {
		Event event = Board.get().currentEvent();
		if(event instanceof ComplexEvent) {
			List<ImagePane> ips = ((ComplexEvent) event).imagePanes();
			removeAll(ips);
		}
	}
	
	/** The {@link Tile} at the given 0-based index, where the tile at index 0 is the start tile. Wraps around if
	 * {@code (index >= Board.TILE_COUNT)}.*/
	public Tile tileAt(int index) {
		return tiles.get(index % Board.TILE_COUNT);
	}

	public int tileIndex(Tile tile) {
		return tiles.indexOf(tile);
	}
	
	/** Moves the given {@link Player} to the given {@link Tile}.*/
	public void movePlayer(Player p, Tile t) {
		Tile oldTile = p.tile();
		oldTile.players().remove(p);
		p.setTile(t);
		t.players().add(p);
		t.arrangePlayers();
		oldTile.arrangePlayers();
	}
	
	/** Moves the given {@link Player} to the {@link Tile} at the given index. Updates the player's
	 * {@link Player#tile() current tile}.*/
	public void movePlayer(Player p, int tileIndex) {
		movePlayer(p, tileAt(tileIndex));
	}
	
	/** Updates the given {@link Player Player's} {@link Player#tile() current tile}. */
	public void walk(Player p, int distance) {
		currentWalk = new WalkAnimation(p, distance);
	}
	
	/** Called by {@link WalkAnimation} to notify this {@link Board} that the animation has finished. */
	public void walkFinished() {
		Tile destTile = tileAt(currentWalk.destTileIndex());
		Board.get().playerLanded(destTile); //calls playerLanded in this class.
		currentWalk = null;
		if(destTile instanceof SafeTile)
			gamePane().incrementTurn(); //Minigames and events will call this when they finish.
		if(destTile instanceof StartTile)
			Board.get().endGame();
	}
	
	/** Called to notify this {@link BoardImageLayer} that the turn has been set to a different number. */
	public void turnSetTo(int turn) {
		for(int i = 1; i < rings.length; i++)
			if(rings[i].fader().isShowing())
				rings[i].fader().fadeOutAndHide();
		if(!rings[turn].fader().isShowing())
			rings[turn].fader().fadeIn();
	}
	
	@Override
	public Board gamePane() {
		return (Board) super.gamePane();
	}
	
}
