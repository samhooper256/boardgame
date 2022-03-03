package minigames.rewards;

import java.util.*;

import base.panes.*;
import fxutils.Fadeable;
import javafx.scene.Node;
import javafx.util.Duration;
import medals.*;
import minigames.MinigameResult;
import players.*;

/** The {@link #imagePanes()} used as part of this {@link RewardsDisplay} must be added to {@link ImageLayer} explicitly
 * for them to appear. */
public final class RewardsDisplay {
	
	public static final Duration FADE_TIME = Duration.millis(500);
	
	private final Title title;
	private final PressSpace pressSpace;
	private final Background background;
	private final FadeablePlayerIcon[] players;
	private final FadeableMedalIcon[] medals;
	private final PositionManager pm;
	private final List<ImagePane> imagePanes;
	private final List<Node> fxNodes;
	
	public RewardsDisplay() {
		pm = new PositionManager(this);
		title = new Title();
		pressSpace = new PressSpace();
		players = new FadeablePlayerIcon[Player.maxCount()];
		background = new Background();
		for(int i = 0; i < Player.maxCount(); i++) {
			players[i] = new FadeablePlayerIcon(i + 1, FADE_TIME);
			players[i].multiplyIdealSize(2);
		}
		medals = new FadeableMedalIcon[Medal.count()];
		for(int i = 0; i < Medal.count(); i++)
			medals[i] = new FadeableMedalIcon(Medal.withIndex(i));
		imagePanes = Collections.unmodifiableList(Arrays.asList(background, players[0], players[1], players[2],
				players[3], medals[0], medals[1], medals[2]));
		fxNodes = Collections.unmodifiableList(Arrays.asList(title, pressSpace));
		hide();
	}
	
	public void show(MinigameResult mr) {
		pm.positionFor(mr);
		int mcount = mr.medals().size();
		for(int i = 0; i < mcount; i++)
			medals[i].fader().fadeIn();
		for(MedalReward r : mr.rewards())
			player(r.playerNumber()).fader().fadeIn();
		background.fader().fadeIn();
		title.fader().fadeIn();
		pressSpace.fader().fadeIn();
	}

	public FadeablePlayerIcon player(int player) {
		return players[player - 1];
	}
	
	public MedalIcon medal(Medal medal) {
		return medals[medal.index()];
	}
	
	public void hide() {
		for(ImagePane ip : imagePanes())
			if(ip instanceof Fadeable)
				((Fadeable) ip).fader().disappear();
			else
				ip.setVisible(false);
		for(Node n : fxNodes())
			if(n instanceof Fadeable)
				((Fadeable) n).fader().disappear();
			else
				n.setVisible(false);
	}
	
	public List<ImagePane> imagePanes() {
		return imagePanes;
	}
	
	public List<Node> fxNodes() {
		return fxNodes;
	}
	
}
