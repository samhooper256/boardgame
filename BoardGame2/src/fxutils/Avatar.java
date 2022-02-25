package fxutils;

import java.util.*;

import base.panes.ImagePane;
import players.Player;

public class Avatar extends ImagePane {
	
	public Avatar(int player) {
		super(Images.avatar(player));
	}
	
}
