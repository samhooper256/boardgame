package minigames.archery.waves;

import minigames.archery.TargetPath;

final class ArcheryWaveImpl implements ArcheryWave {

	private final TargetPath path;
	
	ArcheryWaveImpl(TargetPath path) {
		this.path = path;
	}
	
	@Override
	public TargetPath path() {
		return path;
	}
	
}
