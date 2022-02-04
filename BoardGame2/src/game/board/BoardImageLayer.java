package game.board;

import java.util.*;

import base.*;
import base.panes.*;
import events.SimpleTextEvent;
import fxutils.*;
import game.MainScene;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import medals.*;
import players.Player;
import tiles.*;
import utils.Screen;

public class BoardImageLayer extends AbstractImageLayer implements Updatable {

	/** The delay between when a player lands on a minigame tile and when
	 * {@link MainScene#startMinigame(minigames.Minigame)} is called.*/
	private static final Duration LAND_DELAY_TO_MINIGAME = Duration.millis(500);
	
	private final Ring[] rings; //index i is the ring for player i;
	
	/** Maps each player number to a {@link List} of all the {@link ImagePane} that make up that player's medal area.
	 * (Specifically, each list will contain four images: three medals and a player icon).*/
	private final Map<Integer, List<ImagePane>> medalAreaImages;
	
	private List<Tile> tileOrder;
	
	public BoardImageLayer() {
		this.rings = new Ring[Board.maxPlayerCount() + 1];
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			rings[i] = new Ring();
		medalAreaImages = new HashMap<>();
		createMedalAreas();
	}
	
	void init() {
		RollableDie die = RollableDie.get();
		Screen.center(die);
		tileOrder = generateTileOrder();
		add(new ImagePane(Images.BACKGROUND));
		placeTiles();
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			add(rings[i]);
		addPlayers();
		movePlayersToStart();
		rings[1].lockCoordinatesTo(Player.get(1));
	}
	
	@Override
	public void updatePane(long diff) {
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			rings[i].update(diff);
	}
	
	private void placeTiles() {
		for(int i = 0; i < Board.TILE_COUNT; i++) {
			Tile t = tileOrder.get(i);
			Point2D point = Main.POINTS.get(i);
			t.setIdealCoords(point);
			add(t);
		}
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
	

	private List<Tile> generateTileOrder() {
		List<Tile> order = new ArrayList<>(Board.TILE_COUNT);
		order.add(StartTile.get());
		for(TileSection section : TileSection.ORDER)
			order.addAll(section.randomOrder());
		return order;
	}
	
	private void createMedalAreas() {
		for(int i = 1; i <= Board.maxPlayerCount(); i++) {
			MedalCoords coords = MedalCoords.forPlayer(i);
			ImagePane p = new ImagePane(Images.player(i));
			MedalDisplay gold = new MedalDisplay(Medal.GOLD, .25);
			MedalDisplay silver = new MedalDisplay(Medal.SILVER, .25);
			MedalDisplay bronze = new MedalDisplay(Medal.BRONZE, .25);
			p.setIdealCenter(coords.player());
			gold.setIdealCenter(coords.gold());
			silver.setIdealCenter(coords.silver());
			bronze.setIdealCenter(coords.bronze());
			medalAreaImages.put(i, Arrays.asList(p, gold, silver, bronze));
		}
	}
	
	public void start() {
		removePlayers();
		addPlayers();
		movePlayersToStart();
		removeMedalAreas();
		addMedalAreas();
		addIfAbsent(EventBackground.get());
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			rings[i].fader().disappear();
		rings[1].fader().fadeIn();
	}
	
	private void removeMedalAreas() {
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			removeAll(medalAreaImages.get(i));
	}
	
	private void addMedalAreas() {
		for(int i = 1; i <= gamePane().playerCount(); i++)
			addAll(medalAreaImages.get(i));
	}
	
	public void clearAllImages() {
		getChildren().clear();
		images.clear();
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
		return tileOrder.get(index);
	}

	public int tileIndex(Tile tile) {
		return tileOrder.indexOf(tile);
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
	
	/** Called by {@link WalkAnimation} to notify this {@link Board} that the animation has finished. */
	public void walkFinished(WalkAnimation w) {
		Tile destTile = tileAt(w.destTileIndex());
		Timing.doAfterDelay(LAND_DELAY_TO_MINIGAME, () -> {
			Board.get().playerLanded(destTile); //calls playerLanded in this class.
			if(destTile instanceof SafeTile) //TODO this is not how this should be done.
				gamePane().incrementTurn(); //Minigames and events will call this when they finish.
		});
	}
	
	/** Called to notify this {@link BoardImageLayer} that the turn has been incremented. */
	public void turnIncrementedTo(int turn) {
		rings[Board.prevTurn(turn)].fader().fadeOutAndHide();
		Ring ring = rings[turn];
		ring.lockCoordinatesTo(Player.get(turn));
		if(!ring.fader().isShowing())
			ring.fader().fadeIn();
	}
	
	@Override
	public Board gamePane() {
		return (Board) super.gamePane();
	}
	
}
