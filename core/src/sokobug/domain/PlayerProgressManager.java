package sokobug.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class PlayerProgressManager {
	private static PlayerProgressManager playerProgressManager = new PlayerProgressManager();
	private String filePath;
	private String prefFileName;
	private PlayerProgress playerProgress;
	
	{
		filePath = "data.bin";
		prefFileName = "sokobug";
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
		savePlayerProgress();
	}
	
	private void loadPlayerProgress() {
		Preferences pref = Gdx.app.getPreferences(prefFileName);
		String codedKey = Base64Coder.encodeString("currentLevel");
		String stringUncodedValue = Base64Coder.decodeString(pref.getString(codedKey));
		if (stringUncodedValue.compareTo("") == 0) {
			savePlayerProgress();
		}
		else {
			int uncodedValue = Integer.valueOf(stringUncodedValue);
			playerProgress.setCurrentLevel(uncodedValue);
		}
	}
	
	public void savePlayerProgress() {
		Preferences pref = Gdx.app.getPreferences(prefFileName);
		pref.putString(Base64Coder.encodeString("currentLevel"), 
				Base64Coder.encodeString(String.valueOf(playerProgress.getCurrentLevel())));
		pref.flush();
	}
	
//	private void loadPlayerProgress() {
//		FileHandle file = Gdx.files.local(filePath);
//		Json json = new Json();
//		if (file.exists()) {
//			String progressAsCode = file.readString();
//			String progressAsText = Base64Coder.decodeString(progressAsCode);
//			playerProgress = json.fromJson(PlayerProgress.class, progressAsText);
//		}
//		else {
//			savePlayerProgress();
//		}
//	}
//	
//	public void savePlayerProgress() {
//		FileHandle file = Gdx.files.local(filePath);
//		Json json = new Json();
//		String progressAsText = json.toJson(playerProgress);
//		String progressAsCode = Base64Coder.encodeString(progressAsText);
//		file.writeString(progressAsCode, false);
//	}
}
