package players.list;

public interface ImmutablePlayerList extends ReadOnlyPlayerList {
	
	@Override
	default ImmutablePlayerList frozen() {
		return this;
	}
	
}
