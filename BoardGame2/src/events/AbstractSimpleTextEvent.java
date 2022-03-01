package events;

public abstract class AbstractSimpleTextEvent extends AbstractEvent implements SimpleTextEvent {

	private final String name, description;
	
	protected AbstractSimpleTextEvent(EventTag tag, String name, String description) {
		super(tag);
		this.name = name;
		this.description = description;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String description() {
		return description;
	}
	
}
