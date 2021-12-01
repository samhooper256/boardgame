package fxutils;

import javafx.scene.Node;
import javafx.scene.layout.Region;

public final class Nodes {

	private Nodes() {
		
	}
	
	public static void setMaxSize(Region r, double maxWidth, double maxHeight) {
		r.setMaxWidth(maxWidth);
		r.setMaxHeight(maxHeight);
	}
	
	public static void setLayout(Node n, double layoutX, double layoutY) {
		n.setLayoutX(layoutX);
		n.setLayoutY(layoutY);
	}
	
}
