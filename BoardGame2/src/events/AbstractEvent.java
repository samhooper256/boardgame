package events;

abstract class AbstractEvent implements Event {

	private final EventTag tag;
	
	protected AbstractEvent(EventTag tag) {
		this.tag = tag;
	}
	
	@Override
	public EventTag tag() {
		return tag;
	}
	
}
