package sokobug.domain;

public class PlayerProgress {
	private int currentLevel = 30;
	
	public PlayerProgress() {
		
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public void setCurrentLevel(int level) {
		currentLevel = level;
	}
}
