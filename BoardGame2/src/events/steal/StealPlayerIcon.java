package events.steal;

import java.util.*;

import players.*;

public class StealPlayerIcon extends PlayerIcon {

	public static final List<StealPlayerIcon> LIST = Collections.unmodifiableList(Arrays.asList(
			new StealPlayerIcon(1), new StealPlayerIcon(2), new StealPlayerIcon(3), new StealPlayerIcon(4)));
	
	public static StealPlayerIcon of(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private StealPlayerIcon(int number) {
		super(number);
	}

	public IconBackground background() {
		return IconBackground.of(number());
	}
	
}
