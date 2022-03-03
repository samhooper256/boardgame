package events;

import java.util.*;
import java.util.function.Supplier;

import events.athenasblessing.AthenasBlessingEvent;
import events.injury.InjuryEvent;
import events.steal.StealEvent;
import utils.RNG;

public enum EventTag {
	ATHENAS_BLESSING(AthenasBlessingEvent::new),
	INJURY(InjuryEvent::new),
	STEAL(StealEvent::new);

	private static final EnumSet<EventTag> ENABLED = EnumSet.of(EventTag.STEAL);
//	private static final EnumSet<EventTag> ENABLED = EnumSet.allOf(EventTag.class);
	private static final List<EventTag> ENABLED_AS_LIST = new ArrayList<>(ENABLED);
	
	public static EventTag random() {
		return RNG.pick(ENABLED_AS_LIST);
	}
	
	private final Supplier<Event> supplier;
	
	EventTag(Supplier<Event> supplier) {
		this.supplier = supplier;
	}
	
	public Event generate() {
		return supplier.get();
	}
	
}
