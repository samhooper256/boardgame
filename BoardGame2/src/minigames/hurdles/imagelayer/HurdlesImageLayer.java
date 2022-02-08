package minigames.hurdles.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.hurdles.Hurdles;
import players.Player;

public class HurdlesImageLayer extends MinigameImageLayer {

	public static final double GROUND_HEIGHT = Images.HURDLES_GROUND.getHeight();
	
	private final ImagePane ground;
	private final Map<Integer, Jumper> jumpers;
	
	public HurdlesImageLayer() {
		super(MiniTag.HURDLES);
		ground = new ImagePane(Images.HURDLES_GROUND);
		ground.setIdealY(MainScene.DEFAULT_HEIGHT - GROUND_HEIGHT);
		jumpers = new LinkedHashMap<>();
		for(int p = 1; p <= Player.maxCount(); p++) {
			jumpers.put(p, new Jumper(p));
		}
		Jumper one = jumpers.get(1);
		add(one);
		one.setIdealCenter(200, 600);
		add(ground);
	}
	
	@Override
	public void startMinigame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressedIngame(KeyCode kc) {
		for(Jumper j : jumpers.values())
			j.keyPressed(kc);
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIngame(long diff) {
		for(Jumper j : jumpers.values())
			j.update(diff);
	}

	public ImagePane ground() {
		return ground;
	}
	
	@Override
	public Hurdles gamePane() {
		return (Hurdles) super.gamePane();
	}
	
}
