package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.LevelLoader;
import sokobug.domain.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TitleScreen implements Screen, InputProcessor {

	private Sokobug game;
	
	private Sprite titleImage;
	private float titleScreenMinDuration;
	private float pasedTimeCounter;
	private boolean readyToSkip;
	
	public TitleScreen(Sokobug myGame) {
		game = myGame;
		readyToSkip = false;
		
		TextureParameter param = new TextureParameter();
		param.minFilter = TextureFilter.Linear;
		param.magFilter = TextureFilter.Linear;
		
		game.assetManager.load("backgrounds/title.png", Texture.class, param);
		game.assetManager.finishLoading();
		
		titleImage = new Sprite(game.assetManager.get("backgrounds/title.png", Texture.class));
		titleImage.setPosition(0.f, 0.f);
		pasedTimeCounter = 0.0f;
		titleScreenMinDuration = 3.5f;
		
		game.assetManager.load("fonts/Japonesa24.fnt", BitmapFont.class);
		game.assetManager.load("fonts/Japonesa32.fnt", BitmapFont.class);
		game.assetManager.load("fonts/Japonesa60.fnt", BitmapFont.class);
		game.assetManager.load("fonts/Japonesa120.fnt", BitmapFont.class);
		
		game.assetManager.load("ui/buttons/buttons.pack", TextureAtlas.class);
		game.assetManager.load("ui/buttons/buttons.json", Skin.class, new SkinLoader.SkinParameter("ui/buttons/buttons.pack"));
		
		game.assetManager.load("backgrounds/menu.png", Texture.class, param);
		game.assetManager.load("backgrounds/potatoes.png", Texture.class, param);
		
		game.assetManager.load("level/animations/bug/bug.pack", TextureAtlas.class);
		game.assetManager.load("level/tiles/sarcophagus.png", Texture.class, param);
		game.assetManager.load("level/tiles/wall.png", Texture.class, param);
		game.assetManager.load("level/tiles/free.png", Texture.class, param);
		game.assetManager.load("level/animations/spot/spotGlow.pack", TextureAtlas.class);
		game.assetManager.load("level/tiles/spotOn.png", Texture.class, param);
		game.assetManager.load("level/tiles/spotOff.png", Texture.class, param);
		
		game.assetManager.load("level/topBar.png", Texture.class, param);
		game.assetManager.load("level/pad.png", Texture.class, param);
		game.assetManager.load("level/victoryWing.png", Texture.class, param);
		game.assetManager.load("level/finish.png", Texture.class, param);
		game.assetManager.load("level/animations/victory/victoryGlow.pack", TextureAtlas.class);
		
//		game.assetManager.load("sound/music/newgrounds_egypt.mp3", Music.class);
//		game.assetManager.load("sound/music/lonely_desert.mp3", Music.class);
//		game.assetManager.load("sound/music/arabian_nights.mp3", Music.class);
//		game.assetManager.load("sound/music/egyptian_nights.mp3", Music.class);
//		game.assetManager.load("sound/music/newgrounds_arabia.mp3", Music.class);
//		game.assetManager.load("sound/music/gardens_of_sand.mp3", Music.class);
//		game.assetManager.load("sound/music/pyramid.mp3", Music.class);
		
		game.assetManager.load("sound/effects/bugMove.wav", Sound.class);
		game.assetManager.load("sound/effects/buttonClick.wav", Sound.class);
		game.assetManager.load("sound/effects/sarcophagusMove.wav", Sound.class);
		game.assetManager.load("sound/effects/victory.wav", Sound.class);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
		pasedTimeCounter += delta;
		if (game.assetManager.update()) {
	        if(pasedTimeCounter > titleScreenMinDuration) {
	        	if (!readyToSkip) {
	        		game.mainMenuScreen = new MainMenuScreen(game);
	        		game.creditsScreen = new CreditsScreen(game);
	        		game.chooseChapterScreen = new ChooseChapterScreen(game);
	        		game.chooseLevelScreen = new ChooseLevelScreen(game);
	        		game.ingameScreen = new IngameScreen(game);
	        		game.victoryScreen = new VictoryScreen(game);
	        		readyToSkip = true;
	        	}
	        }
		}

		game.batch.begin();
		titleImage.draw(game.batch);
		game.batch.end();
		
		if (readyToSkip) {
			game.setScreen(game.mainMenuScreen);
		}
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void dispose() {
		game.assetManager.unload("backgrounds/title.png");
		game.titleScreen = null; /* we are not going to use the TitleScreen so we can set this to null
									for the garbage collector if it happens that it needs to clear memory */
	}
	
	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f, game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}



}
