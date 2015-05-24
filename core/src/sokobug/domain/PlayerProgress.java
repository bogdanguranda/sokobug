package sokobug.domain;

import java.util.ArrayList;
import java.util.List;

public class PlayerProgress {
	private static final int DEFAULT_STARTING_LEVEL = 29;
	public static final int MAXIMUM_SKIPPED_LEVELS_ALLOWED = 3;
	private int currentLevel = DEFAULT_STARTING_LEVEL;
	private List<Integer> skippedLevels = new ArrayList<Integer>();

	public PlayerProgress() {

	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int level) {
		currentLevel = level;
	}

	public List<Integer> getSkippedLevels() {
		return skippedLevels;
	}

	public void setSkippedLevels(List<Integer> skippedLevels) {
		this.skippedLevels = skippedLevels;
	}

}
