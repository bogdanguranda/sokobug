package sokobug.domain;

import sokobug.screens.ChooseChapterScreen;
import sokobug.screens.ChooseLevelScreen;

public class PlayerProgress {
	public enum LevelType {
		OPENED_LEVEL, LOCKED_LEVEL, SKIPPED_LEVEL
	}

	private static final int DEFAULT_STARTING_LEVEL = 30;
	public static final int MAXIMUM_SKIPPED_LEVELS_ALLOWED = 3;
	
	public static final int NUMBER_OF_LEVELS = 30;
	public static final int NUMBER_OF_CHAPTERS = 2;
	
	private int levelsStatus[][];

	public PlayerProgress() {
		levelsStatus = new int[ChooseChapterScreen.NUMBER_OF_CHAPTERS][ChooseLevelScreen.NUMBER_OF_LEVELS];
		for (int i = 0; i < levelsStatus.length; i++) {
			for (int j = 0; j < levelsStatus[i].length; j++) {
				if (j < DEFAULT_STARTING_LEVEL) {
					levelsStatus[i][j] = LevelType.OPENED_LEVEL.ordinal();
				} else {
					levelsStatus[i][j] = LevelType.LOCKED_LEVEL.ordinal();
				}
			}
		}
	}

	public int[] getLevelsStatus(int chapter) {
		return levelsStatus[chapter - 1];
	}
	
	public void setLevelsStatus(int levelsStatus[], int chapter) {
		this.levelsStatus[chapter - 1] = levelsStatus;
	}
	
	public int getCurrentLevel(int chapter) {
		for (int i = 0; i < levelsStatus[chapter - 1].length; i++) {
			if (levelsStatus[chapter - 1][i] == LevelType.LOCKED_LEVEL.ordinal()) {
				return i;
			}
		}
		return ChooseLevelScreen.NUMBER_OF_LEVELS;
	}

	public void skipCurrentLevel(int chapter) {
		if (getCurrentLevel(chapter) < ChooseLevelScreen.NUMBER_OF_LEVELS) {
			levelsStatus[chapter - 1][getCurrentLevel(chapter) - 1] = LevelType.SKIPPED_LEVEL.ordinal();
			levelsStatus[chapter - 1][getCurrentLevel(chapter)] = LevelType.OPENED_LEVEL.ordinal();
		}
	}

	public void markAsFinished(int level, int chapter) {
		if (level == getCurrentLevel(chapter) && level < ChooseLevelScreen.NUMBER_OF_LEVELS) {
			levelsStatus[chapter - 1][level] = LevelType.OPENED_LEVEL.ordinal();
		} else {
			levelsStatus[chapter - 1][level - 1] = LevelType.OPENED_LEVEL.ordinal();
		}
	}

	public int numberOfSkippedLevels(int chapter) {
		int numberOfLevelsSkipped = 0;
		for (int i = 0; i < levelsStatus[chapter - 1].length; i++) {
			if (levelsStatus[chapter - 1][i] == LevelType.SKIPPED_LEVEL.ordinal()) {
				numberOfLevelsSkipped++;
			}
		}
		return numberOfLevelsSkipped;
	}

	public boolean isLevelSkipped(int level, int chapter) {
		return levelsStatus[chapter - 1][level - 1] == LevelType.SKIPPED_LEVEL.ordinal();
	}
}
