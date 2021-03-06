package events.steal;

import java.util.*;
import java.util.stream.Collectors;

import events.*;
import game.MainScene;
import game.board.Board;
import players.*;

import static game.MainScene.*;

public class StealEvent extends AbstractComplexEvent implements ComplexEvent {
	
	public static final double INSTRUCTIONS_Y = DEFAULT_HEIGHT * .39;
	
	private static final double
		DONT_STEAL_Y = DEFAULT_HEIGHT * .59,
		ICON_SPACING = 32,
		ICON_SIZE = 128,
		ICON_CENTER_Y = CENTER_Y + 3;
	
	private final FadeablePlayerIcon[] icons;
	private final Instructions instructions;
	private final DontStealButton dontSteal;
	
	public StealEvent() {
		super(EventTag.STEAL);
		icons = new FadeablePlayerIcon[Player.maxCount()];
		for(int i = 0; i < icons.length; i++) {
			icons[i] = new FadeablePlayerIcon(i + 1, Board.EVENT_FADE_DURATION);
			icons[i].setIdealSize(ICON_SIZE);
		}
		instructions = new Instructions();
		dontSteal = new DontStealButton(this);
		dontSteal.setIdealCenterX(MainScene.CENTER_X);
		dontSteal.setIdealY(DONT_STEAL_Y);
		Collections.addAll(fxNodes, instructions);
	}

	@Override
	public void setup(Player player) {
		imagePanes.clear();
		List<Player> players = Board.get().players().collect(Collectors.toList());
		double width = (players.size() - 1) * icon(1).getIdealWidth() + ICON_SPACING * (players.size() - 2);
		double cx = CENTER_X - width * .5 + icon(1).getIdealWidth() * .5;
		for(int i : players.stream().mapToInt(Player::number).toArray()) {
			if(i == player.number())
				continue;
			PlayerIcon icon = icon(i);
			icon.setIdealCenter(cx, ICON_CENTER_Y);
			IconBackground background = IconBackground.of(icon.number());
			background.setIdealCenter(cx, ICON_CENTER_Y);
			imagePanes.add(background);
			imagePanes.add(icon);
			cx += icon.getIdealWidth() + ICON_SPACING;
		}
		imagePanes.add(dontSteal);
	}
	
	
	private PlayerIcon icon(int number) {
		return icons[number - 1];
	}
	
	@Override
	public String name() {
		return "Steal";
	}
	
}
