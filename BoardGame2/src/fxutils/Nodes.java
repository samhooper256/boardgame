package fxutils;

import javafx.scene.Node;
import javafx.scene.layout.Region;

public final class Nodes {

	private Nodes() {
		
	}
	
	public static void setMinSize(Region r, double minWidth, double minHeight) {
		r.setMinWidth(minWidth);
		r.setMinHeight(minHeight);
	}
	
	public static void setMaxSize(Region r, double maxWidth, double maxHeight) {
		r.setMaxWidth(maxWidth);
		r.setMaxHeight(maxHeight);
	}
	
	public static void setPrefSize(Region r, double prefWidth, double prefHeight) {
		r.setPrefWidth(prefWidth);
		r.setPrefHeight(prefHeight);
	}
	
	public static void setPrefSize(Region r, double prefSize) {
		setPrefSize(r, prefSize, prefSize);
	}
	
	public static void setLayout(Node n, double layoutX, double layoutY) {
		n.setLayoutX(layoutX);
		n.setLayoutY(layoutY);
	}
	
}
