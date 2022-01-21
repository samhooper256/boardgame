package medals;

import base.panes.ImagePane;

public final class MedalDisplay extends ImagePane {

	private final Medal medal;
	
	public MedalDisplay(Medal medal) {
		super(medal.image());
		this.medal = medal;
	}
	
	public Medal medal() {
		return medal;
	}
	
}
