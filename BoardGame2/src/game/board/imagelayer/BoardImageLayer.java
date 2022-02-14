package game.board.imagelayer;

import java.util.*;

import base.*;
import base.panes.*;
import events.SimpleTextEvent;
import fxutils.*;
import game.board.*;
import javafx.geometry.Point2D;
import medals.*;
import players.Player;
import tiles.*;

public class BoardImageLayer extends AbstractImageLayer implements Updatable {

	private final Ring[] rings; //index i is the ring for player i;
	
	/** Maps each player number to a {@link List} of all the {@link ImagePane} that make up that player's medal area.
	 * (Specifically, each list will contain four images: three medals and a player icon).*/
	private final Map<Integer, List<ImagePane>> medalAreaImages;
	
	private List<Tile> tiles;
	private WalkAnimation currentWalk;
	
	public BoardImageLayer() {
		this.rings = new Ring[Board.maxPlayerCount() + 1];
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			rings[i] = new Ring();
		medalAreaImages = new HashMap<>();
		createMedalAreas();
		tiles = Collections.emptyList();
	}
	
	/** Should only be called once. */
	public void init() {
		add(new ImagePane(Images.BOARD_BACKGROUND));
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			add(rings[i]);
		add(1, EventBackground.get());
		for(int i = 1; i <= Player.maxCount(); i++)
			rings[i].lockCoordinatesTo(Player.get(i));
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
		for(int i = 1; i <= Board.maxPlayerCount(); i++) {
			MedalCoords coords = MedalCoords.forPlayer(i);
			ImagePane p = new ImagePane(Images.player(i));
			MedalIcon gold = new MedalIcon(Medal.GOLD, .25);
			MedalIcon silver = new MedalIcon(Medal.SILVER, .25);
			MedalIcon bronze = new MedalIcon(Medal.BRONZE, .25);
			p.setIdealCenter(coords.player());
			gold.setIdealCenter(coords.gold());
			silver.setIdealCenter(coords.silver());
			bronze.setIdealCenter(coords.bronze());
			medalAreaImages.put(i, Arrays.asList(p, gold, silver, bronze));
		}
	}
	
	private void removeMedalAreas() {
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			removeAll(medalAreaImages.get(i));
	}
	
	private void addMedalAreas() {
		for(int i = 1; i <= gamePane().playerCount(); i++)
			addAll(medalAreaImages.get(i));
	}
	
	@Override
	public void updatePane(long diff) {
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			rings[i].update(diff);
		RollableDie.get().update(diff);
		if(currentWalk != null)
			currentWalk.update(diff);
	}
	
	public void showSimpleTextEvent(SimpleTextEvent event) {
		EventBackground.get().fader().fadeIn();
	}
	
	public boolean requestEventFinish() {
		Fader f = EventBackground.get().fader();
		if(f.isFadingIn() || f.isFadingOut())
			return false;
		f.fadeOutAndHide();
		return true;
	}
	
	/** The {@link Tile} at the given 0-based index, where the tile at index 0 is the start tile. */
	public Tile tileAt(int index) {
		return tiles.get(index);
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
		if(destTile instanceof SafeTile) //TODO this is not how this should be done.
			gamePane().incrementTurn(); //Minigames and events will call this when they finish.
		currentWalk = null;
	}
	
	/** Called to notify this {@link BoardImageLayer} that the turn has been incremented. */
	public void turnIncrementedTo(int turn) {
		rings[Board.prevTurn(turn)].fader().fadeOutAndHide();
		if(!rings[turn].fader().isShowing())
			rings[turn].fader().fadeIn();
	}
	
	@Override
	public Board gamePane() {
		return (Board) super.gamePane();
	}
	
}
