package base;

import javafx.scene.input.*;

public interface AcceptsInput {

	/** no-op by default. */
	default void keyPressed(KeyCode kc) {}
	
	/** no-op by default. */
	default void keyReleased(KeyCode kc) {}
	
	/** no-op by default. */
	default void mouseClicked(MouseEvent me) {}
	
	/** no-op by default. */
	default void mouseReleased(MouseEvent me) {}
	
	/** no-op by default. */
	default void mousePressed(MouseEvent me) {}
	
}
