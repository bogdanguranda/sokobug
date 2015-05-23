package sokobug.domain;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	private AssetManager assetManager;
	private Music currentPlayingMusic;
	private String musicAssetDirectory = "sound/music/";
	private String soundEffectsAssetDirectory = "sound/effects/";
	private String[] startingMusic = { "newgrounds_egypt.mp3", "lonely_desert.mp3", "arabian_nights.mp3" };
	private String[] allMusic = { "newgrounds_egypt.mp3", "lonely_desert.mp3", "arabian_nights.mp3",
			"newgrounds_arabia.mp3", "gardens_of_sand.mp3", "pyramid.mp3" };
	private Random random = new Random();
	private static final float DEFAULT_MUSIC_VOLUME = 50.f;
	private static final float MAX_MUSIC_VOLUME = 100.f;

	public SoundManager(AssetManager assetManager) {
		this.assetManager = assetManager;
		this.currentPlayingMusic = assetManager.get(
				musicAssetDirectory + startingMusic[random.nextInt(startingMusic.length)], Music.class);
	}

	public void startPlayingMusic() {
		currentPlayingMusic.setVolume(DEFAULT_MUSIC_VOLUME / MAX_MUSIC_VOLUME);
		currentPlayingMusic.play();
	}
	
	public Music getCurrentPlayingMusic() {
		return currentPlayingMusic;
	}

	public void updateMusicState() {
		if (!currentPlayingMusic.isPlaying()) {
			currentPlayingMusic = assetManager.get(musicAssetDirectory + allMusic[random.nextInt(allMusic.length)],
					Music.class);
		}
		currentPlayingMusic.play();
	}

	public Sound getSound(String fileNamePath) {
		return assetManager.get(fileNamePath, Sound.class);
	}
}
