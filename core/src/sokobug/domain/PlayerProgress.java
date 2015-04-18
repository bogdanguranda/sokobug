package sokobug.domain;

public class PlayerProgress {
	private int currentLevel = 1;
	
	public PlayerProgress() {
		
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public void setCurrentLevel(int level) {
		currentLevel = level;
	}

//	@Override
//	public void write(Json json) {
//		json.writeValue("currentLevel", currentLevel);
//	}
//
//	@Override
//	public void read(Json json, JsonValue jsonData) {
//		currentLevel = json.readValue("currentLevel", int.class, jsonData);
//	}
}
