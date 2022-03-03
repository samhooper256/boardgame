package events;

import java.util.List;

import base.panes.ImagePane;
import javafx.scene.Node;
import players.Player;

public interface ComplexEvent extends Event {

	List<ImagePane> imagePanes();
	
	List<Node> fxNodes();
	
	void setup(Player player);
	
	@Override
	default void play(Player player) {
		setup(player);
		Event.super.play(player);
	}
	
}
