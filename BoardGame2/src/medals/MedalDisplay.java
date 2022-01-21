package medals;

import base.panes.ImagePane;

public final class MedalDisplay extends ImagePane {

	private final Medal medal;
	
	public MedalDisplay(Medal medal, double scale) {
		super(medal.image());
		setIdealWidth(getIdealWidth() * scale);
		setIdealHeight(getIdealHeight() * scale);
		this.medal = medal;
	}
	
	public Medal medal() {
		return medal;
	}
	
}
