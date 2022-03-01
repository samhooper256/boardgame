package events;

import java.util.function.Supplier;

import events.athenasblessing.AthenasBlessingEvent;
import events.injury.InjuryEvent;
import utils.RNG;

public enum EventTag {
	ATHENAS_BLESSING(AthenasBlessingEvent::new),
	INJURY(InjuryEvent::new);

	public static EventTag random() {
		EventTag[] values = values();
		return values[RNG.intExclusive(values.length)];
	}
	
	private final Supplier<Event> supplier;
	
	EventTag(Supplier<Event> supplier) {
		this.supplier = supplier;
	}
	
	public Event generate() {
		return supplier.get();
	}
	
}
