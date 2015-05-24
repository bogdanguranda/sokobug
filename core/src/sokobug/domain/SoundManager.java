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
	private String[] startingMusic = { "newgrounds_egypt.ogg", "lonely_desert.ogg", "egyptian_nights.ogg" };
	private String[] allMusic = { "newgrounds_egypt.ogg", "lonely_desert.ogg", "egyptian_nights.ogg",
			"newgrounds_arabia.ogg", "pyramid.ogg" };
	private Random random = new Random();
	private static final float DEFAULT_MUSIC_VOLUME = 50.f / 100.f;
	private boolean mute = false;

	public SoundManager(AssetManager assetManager) {
		this.assetManager = assetManager;
		int randomIndex = random.nextInt(startingMusic.length);
		assetManager.load(musicAssetDirectory + startingMusic[randomIndex], Music.class);
		assetManager.finishLoading();
		currentPlayingMusic = assetManager.get(musicAssetDirectory + startingMusic[randomIndex], Music.class);
	}

	public void startPlayingMusic() {
		currentPlayingMusic.setVolume(DEFAULT_MUSIC_VOLUME);
		currentPlayingMusic.play();
	}

	public Music getCurrentPlayingMusic() {
		return currentPlayingMusic;
	}

	public void updateMusicState() {
		if (!currentPlayingMusic.isPlaying() && !isMuted()) {
			currentPlayingMusic.dispose();
			int randomIndex = random.nextInt(allMusic.length);
			assetManager.load(musicAssetDirectory + allMusic[randomIndex], Music.class);
			assetManager.finishLoading();
			currentPlayingMusic = assetManager.get(musicAssetDirectory + allMusic[randomIndex], Music.class);
			startPlayingMusic();
		}
	}

	public Sound getSound(String fileName) {
		return assetManager.get(soundEffectsAssetDirectory + fileName, Sound.class);
	}

	public boolean isMuted() {
		return mute;
	}

	public void setMute(boolean mute) {
		if (mute) {
			currentPlayingMusic.pause();
		} else {
			currentPlayingMusic.play();
		}
		this.mute = mute;
	}
}
