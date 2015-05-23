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

	public int getCurrentLevel() {
		return playerProgress.getCurrentLevel();
	}

	public void setCurrentLevel(int level) {
		playerProgress.setCurrentLevel(level);
		saveCurrentLevel();
	}

	public void skipCurentLevel() {
		if (playerProgress.getSkippedLevels().size() < PlayerProgress.MAXIMUM_SKIPPED_LEVELS_ALLOWED) {
			playerProgress.getSkippedLevels().add(playerProgress.getCurrentLevel());
			playerProgress.setCurrentLevel(playerProgress.getCurrentLevel() + 1);
			savePlayerProgress();
		}
	}

	public void markAsFinished(int level) {
		playerProgress.getSkippedLevels().remove(Integer.valueOf(level));
		saveSkippedLevels();
	}

	public boolean isLevelSkipped(int level) {
		return playerProgress.getSkippedLevels().contains(level);
	}

	private void loadPlayerProgress() {
		loadCurrentLevel();
		loadSkippedLevels();
	}

	public void savePlayerProgress() {
		saveCurrentLevel();
		saveSkippedLevels();
	}

	private void loadCurrentLevel() {
		String codedKey = Base64Coder.encodeString("currentLevel");
		String stringUncodedValue = Base64Coder.decodeString(pref.getString(codedKey));
		if (stringUncodedValue.compareTo("") == 0) {
			savePlayerProgress();
		} else {
			playerProgress.setCurrentLevel(Integer.valueOf(stringUncodedValue));
		}
	}

	private void saveCurrentLevel() {
		pref.putString(Base64Coder.encodeString("currentLevel"),
				Base64Coder.encodeString(String.valueOf(playerProgress.getCurrentLevel())));
		pref.flush();
	}

	private void loadSkippedLevels() {
		String codedKey = Base64Coder.encodeString("skippedLevels");
		String stringUncodedValue = Base64Coder.decodeString(pref.getString(codedKey));
		if (stringUncodedValue.compareTo("") == 0) {
			saveSkippedLevels();
		} else {
			List<Integer> skippedLevels = new ArrayList<Integer>();
			String stringSkippedLevels[] = stringUncodedValue.split(" ");
			for (String stringLevel : stringSkippedLevels) {
				skippedLevels.add(Integer.valueOf(stringLevel));
			}
			playerProgress.setSkippedLevels(skippedLevels);
		}
	}

	private void saveSkippedLevels() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Integer integerLevel : playerProgress.getSkippedLevels()) {
			stringBuilder.append(String.valueOf(integerLevel)).append(" ");
		}
		if (stringBuilder.length() != 0) {
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		pref.putString(Base64Coder.encodeString("skippedLevels"), Base64Coder.encodeString(stringBuilder.toString()));
		pref.flush();
	}
}
