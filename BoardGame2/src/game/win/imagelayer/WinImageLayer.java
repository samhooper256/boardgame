package game.win.imagelayer;

import java.util.List;
import java.util.stream.Collectors;

import base.panes.*;
import fxutils.*;
import game.MainScene;
import game.win.WinPane;
import players.Player;

public class WinImageLayer extends AbstractImageLayer {

	private static final double PRESS_SPACE_BOTTOM_PADDING = 20;
	
	private final Avatar[] avatars;
	private final ImagePane background, podium1, podium2, podium3, pressSpace;
	
	public WinImageLayer() {
		avatars = new Avatar[] {new Avatar(1), new Avatar(2), new Avatar(3), new Avatar(4)};
		background = new ImagePane(Images.WIN_BACKGROUND);
		podium1 = new ImagePane(Images.PODIUM_1);
		podium2 = new ImagePane(Images.PODIUM_2);
		podium3 = new ImagePane(Images.PODIUM_3);
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
		addAll(podium3, podium2, podium1);
		
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
