package sokobug.domain;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	private AssetManager assetManager;
	private Music currentPlayingMusic;
	private Random random = new Random();
	private static final float DEFAULT_MUSIC_VOLUME = 0.5f;
	private static final float DEFAULT_SOUNDS_VOLUME = 0.5f;
	private boolean mute = false;
	private boolean paused = false;
	
	private String[] startingMusic = { Resources.MUSIC_NEWGROUNDS_EGYPT.getPath(),
			Resources.MUSIC_LONELY_DESERT.getPath(), Resources.MUSIC_EGYPTIAN_NIGHTS.getPath() };
	private String[] allMusic = { Resources.MUSIC_NEWGROUNDS_EGYPT.getPath(), Resources.MUSIC_LONELY_DESERT.getPath(),
			Resources.MUSIC_EGYPTIAN_NIGHTS.getPath(), Resources.MUSIC_NEWGROUNDS_ARABIA.getPath(),
			Resources.MUSIC_PYRAMID.getPath() };

	public SoundManager(AssetManager assetManager) {
		this.assetManager = assetManager;
		int randomIndex = random.nextInt(startingMusic.length);
		assetManager.load(startingMusic[randomIndex], Music.class);
		assetManager.finishLoading();
		currentPlayingMusic = assetManager.get(startingMusic[randomIndex], Music.class);
	}

	public void startPlayingMusic() {
		currentPlayingMusic.setVolume(DEFAULT_MUSIC_VOLUME);
		currentPlayingMusic.play();
	}

	public Music getCurrentPlayingMusic() {
		return currentPlayingMusic;
	}

	public void updateMusicState() {
		if (!currentPlayingMusic.isPlaying() && !isPaused() && !isMuted()) {
			currentPlayingMusic.dispose();
			int randomIndex = random.nextInt(allMusic.length);
			assetManager.load(allMusic[randomIndex], Music.class);
			assetManager.finishLoading();
			currentPlayingMusic = assetManager.get(allMusic[randomIndex], Music.class);
			startPlayingMusic();
		}
	}

	public void playSound(String filePath) {
		assetManager.get(filePath, Sound.class).play(DEFAULT_SOUNDS_VOLUME);
	}

	public boolean isMuted() {
		return mute;
	}

	public void setMute(boolean mute) {
		setPaused(mute);
		this.mute = mute;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		if (paused) {
			currentPlayingMusic.pause();
		} else {
			currentPlayingMusic.play();
		}
		this.paused = paused;
	}
}
