package game.win.imagelayer;

import java.util.List;
import java.util.stream.Collectors;

import base.panes.*;
import fxutils.*;
import game.MainScene;
import game.win.WinPane;
import medals.*;
import players.Player;

public class WinImageLayer extends AbstractImageLayer {

	private static final double PRESS_SPACE_BOTTOM_PADDING = 20, MEDAL_Y_DOWN = -5;
	
	private final Avatar[] avatars;
	private final ImagePane
		background,
		/* first place */ podium1, /* second place */ podium2, /* third place */ podium3,
		pressSpace;
	private final MedalIcon medal1, medal2, medal3;
	
	public WinImageLayer() {
		avatars = new Avatar[] {new Avatar(1), new Avatar(2), new Avatar(3), new Avatar(4)};
		background = new ImagePane(Images.WIN_BACKGROUND);
		podium1 = new ImagePane(Images.PODIUM_1);
		podium2 = new ImagePane(Images.PODIUM_2);
		podium3 = new ImagePane(Images.PODIUM_3);
		medal1 = new MedalIcon(Medal.GOLD);
		medal2 = new MedalIcon(Medal.SILVER);
		medal3 = new MedalIcon(Medal.BRONZE);
		pressSpace = new ImagePane(Images.PRESS_SPACE);
		pressSpace.setIdealCenterX(MainScene.CENTER_X);
		pressSpace.setIdealBottomY(MainScene.DEFAULT_HEIGHT - PRESS_SPACE_BOTTOM_PADDING);
		add(background);
	}
	
	@Override
	public void updatePane(long diff) {
		// TODO Auto-generated method stub
	}
	
	public void setupFor(List<Player> ranking) {
		removePodiums();
		removeAvatars();
		remove(pressSpace);
		podium1.setIdealCenterX(MainScene.CENTER_X);
		podium1.setIdealBottomY(MainScene.DEFAULT_HEIGHT);
		podium2.setIdealRightX(podium1.getIdealX());
		podium2.setIdealBottomY(MainScene.DEFAULT_HEIGHT);
		podium3.setIdealX(podium1.getIdealRightX());
		podium3.setIdealBottomY(MainScene.DEFAULT_HEIGHT);
		medal1.setIdealCoords(podium1.getIdealX() + .5 * podium1.getIdealWidth() - .5 * medal1.getIdealWidth(),
				podium1.getIdealY() + MEDAL_Y_DOWN);
		medal2.setIdealCoords(podium2.getIdealX() + .5 * podium2.getIdealWidth() - .5 * medal2.getIdealWidth(),
				podium2.getIdealY() + MEDAL_Y_DOWN);
		medal3.setIdealCoords(podium3.getIdealX() + .5 * podium3.getIdealWidth() - .5 * medal3.getIdealWidth(),
				podium3.getIdealY() + MEDAL_Y_DOWN);
		addAll(podium3, medal3, podium2, medal2, podium1, medal1);
		
		List<Avatar> avatars = ranking.stream().map(p -> avatar(p.number())).collect(Collectors.toList());
		
		for(int i = 0, max = Math.min(avatars.size(), 3); i < max; i++) {
			setupAvatarOnPodium(avatars.get(i), i + 1);
			add(avatars.get(i));
		}
		
		add(pressSpace);
	}
	
	private void removePodiums() {
		removeAll(podium1, podium2, podium3);
	}
	
	private void removeAvatars() {
		removeAll(avatars);
	}
	
	private void setupAvatarOnPodium(Avatar a, int place) {
		ImagePane p = podium(place);
		a.setIdealCenterX(p.getIdealCenterX());
		a.setIdealBottomY(p.getIdealY());
	}
	
	private ImagePane podium(int place) {
		if(place == 1)
			return podium1;
		if(place == 2)
			return podium2;
		if(place == 3)
			return podium3;
		throw new IllegalArgumentException(String.format("place: %d", place));
	}

	private Avatar avatar(int number) {
		return avatars[Player.validate(number) - 1];
	}
	
	@Override
	public WinPane gamePane() {
		return (WinPane) super.gamePane();
	}

}
