package medals;

import base.panes.ImagePane;

public class MedalIcon extends ImagePane {

	private final Medal medal;
	
	public MedalIcon(Medal medal, double scale) {
		super(medal.image());
		setIdealWidth(getIdealWidth() * scale);
		setIdealHeight(getIdealHeight() * scale);
		this.medal = medal;
	}
	
	public Medal medal() {
		return medal;
	}
	
}
