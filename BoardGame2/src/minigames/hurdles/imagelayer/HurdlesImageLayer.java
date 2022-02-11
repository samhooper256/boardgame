package minigames.hurdles.imagelayer;

import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.hurdles.Hurdles;

public class HurdlesImageLayer extends MinigameImageLayer {

	private final ImagePane ground;
	
	private Hurdle hurdle = new Hurdle(540);
	
	public HurdlesImageLayer() {
		super(MiniTag.HURDLES);
		ground = new ImagePane(Images.HURDLES_GROUND);
		ground.setIdealY(Coords.get().groundY());
		hurdle.setX(1200);
		add(ground);
		addAll(Jumper.LIST);
		addAll(JumpBarBackground.LIST);
		addAll(JumpBar.LIST);
		addAll(JumpBarTick.LIST);
		addAll(hurdle.imagePanes());
	}
	
	@Override
	public void startMinigame() {
		for(Jumper j : Jumper.LIST)
			j.fixToGroundLevel();
	}

	@Override
	public void keyPressedIngame(KeyCode kc) {
		for(Jumper j : Jumper.LIST)
			j.keyPressed(kc);
		for(JumpBar jb : JumpBar.LIST)
			jb.keyPressed(kc);
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		for(Jumper j : Jumper.LIST)
			j.keyPressed(kc);
		for(JumpBar jb : JumpBar.LIST)
			jb.keyReleased(kc);
	}

	@Override
	public void updateIngame(long diff) {
		for(Jumper j : Jumper.LIST)
			j.update(diff);
		for(JumpBar jb : JumpBar.LIST)
			jb.update(diff);
		hurdle.update(diff);
	}

	public ImagePane ground() {
		return ground;
	}
	
	@Override
	public Hurdles gamePane() {
		return (Hurdles) super.gamePane();
	}
	
}
