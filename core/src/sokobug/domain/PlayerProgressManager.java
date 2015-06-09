package sokobug.domain;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;

public class PlayerProgressManager {
	private static PlayerProgressManager playerProgressManager = new PlayerProgressManager();
	private String prefFileName;
	private Preferences pref;
	private PlayerProgress playerProgress;

	{
		prefFileName = "sokobug";
		pref = Gdx.app.getPreferences(prefFileName);
		playerProgress = new PlayerProgress();
		loadPlayerProgress();
	}

	private PlayerProgressManager() {

	}

	public static PlayerProgressManager getPlayerProgressManager() {
		return playerProgressManager;
	}

	public int getCurrentLevel(int chapter) {
		return playerProgress.getCurrentLevel(chapter);
	}

	public void skipCurentLevel(int chapter) {
		if (playerProgress.numberOfSkippedLevels(chapter) < PlayerProgress.MAXIMUM_SKIPPED_LEVELS_ALLOWED) {
			playerProgress.skipCurrentLevel(chapter);
			savePlayerProgress();
		}
	}

	public void markAsFinished(int level, int chapter) {
		playerProgress.markAsFinished(level, chapter);
		saveLevelsStatus();
	}

	public boolean isLevelSkipped(int level, int chapter) {
		return playerProgress.isLevelSkipped(level, chapter);
	}

	private void loadPlayerProgress() {
		loadLevelsStatus();
	}

	public void savePlayerProgress() {
		saveLevelsStatus();
	}

	public boolean hasOlderVersionSavingSystem() {
		String codedKey = Base64Coder.encodeString("currentLevel");
		String stringUncodedValue = Base64Coder.decodeString(pref.getString(codedKey));
		
		String codedKey2 = Base64Coder.encodeString("chapter1");
		String stringUncodedValue2 = Base64Coder.decodeString(pref.getString(codedKey2));
		
		if (stringUncodedValue.compareTo("") == 0) {
			return false;
		} else {
			if (stringUncodedValue2.compareTo("") == 0) {
				return true;
			}
			else {
				return false;
			}			
		}
	}

	private void loadOlderVersionChapter1() {
		int currentLevel;

		// loadCurrentLevel
		String codedKey = Base64Coder.encodeString("currentLevel");
		String stringUncodedValue = Base64Coder.decodeString(pref.getString(codedKey));
		currentLevel = Integer.valueOf(stringUncodedValue);

		int chapter1LevelsStatus[] = new int[PlayerProgress.NUMBER_OF_LEVELS];

		// loadSkippedLevels
		String codedKey2 = Base64Coder.encodeString("skippedLevels");
		String stringUncodedValue2 = Base64Coder.decodeString(pref.getString(codedKey2));
		String stringSkippedLevels[] = stringUncodedValue2.split(" ");
		for (int i = 0; i < chapter1LevelsStatus.length; i++) {
			for (int j = 0; j < stringSkippedLevels.length; j++) {
				if (i + 1 <= currentLevel) {
					if (stringSkippedLevels[j].compareTo("") != 0) {
						if (i + 1 == Integer.valueOf(stringSkippedLevels[j])) {
							chapter1LevelsStatus[i] = PlayerProgress.LevelType.SKIPPED_LEVEL.ordinal();
						} else {
							chapter1LevelsStatus[i] = PlayerProgress.LevelType.OPENED_LEVEL.ordinal();
						}
					}
				} else {
					chapter1LevelsStatus[i] = PlayerProgress.LevelType.LOCKED_LEVEL.ordinal();
				}
			}
		}

		playerProgress.setLevelsStatus(chapter1LevelsStatus, 1);
		savePlayerProgress();
	}

	private void loadLevelsStatus() {
		if (hasOlderVersionSavingSystem()) {
			loadOlderVersionChapter1();
		}

		for (int i = 0; i < PlayerProgress.NUMBER_OF_CHAPTERS; i++) {
			String codedKey = Base64Coder.encodeString("chapter" + (i + 1));
			String stringUncodedValue = Base64Coder.decodeString(pref.getString(codedKey));
			if (stringUncodedValue.compareTo("") == 0) {
				saveLevelsStatus();
				break;
			} else {
				String stringLevelsStatus[] = stringUncodedValue.split(" ");
				int levelsStatus[] = new int[stringLevelsStatus.length];
				for (int j = 0; j < stringLevelsStatus.length; j++) {
					levelsStatus[j] = Integer.valueOf(stringLevelsStatus[j]);
				}
				playerProgress.setLevelsStatus(levelsStatus, i + 1);
			}
		}
	}

	private void saveLevelsStatus() {
		for (int i = 0; i < PlayerProgress.NUMBER_OF_CHAPTERS; i++) {
			StringBuilder stringBuilder = new StringBuilder();
			for (Integer levelStatus : playerProgress.getLevelsStatus(i + 1)) {
				stringBuilder.append(String.valueOf(levelStatus)).append(" ");
			}
			if (stringBuilder.length() != 0) {
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			}
			pref.putString(Base64Coder.encodeString("chapter" + (i + 1)),
					Base64Coder.encodeString(stringBuilder.toString()));
		}
		pref.flush();
	}
}
