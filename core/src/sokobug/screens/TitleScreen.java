package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.LevelLoader;
import sokobug.domain.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
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

		game.assetManager.load(Resources.BACKGROUNDS_TITLE.getPath(), Texture.class, param);
		game.assetManager.finishLoading();

		titleImage = new Sprite(game.assetManager.get(Resources.BACKGROUNDS_TITLE.getPath(), Texture.class));
		titleImage.setPosition(0.f, 0.f);
		pasedTimeCounter = 0.0f;
		titleScreenMinDuration = 3.5f;

		game.assetManager.load(Resources.FONTS_JAPONESA24.getPath(), BitmapFont.class);
		game.assetManager.load(Resources.FONTS_JAPONESA32.getPath(), BitmapFont.class);
		game.assetManager.load(Resources.FONTS_JAPONESA60.getPath(), BitmapFont.class);
		game.assetManager.load(Resources.FONTS_JAPONESA120.getPath(), BitmapFont.class);

		game.assetManager.load(Resources.UI_BUTTONS_PACK.getPath(), TextureAtlas.class);
		game.assetManager.load(Resources.UI_BUTTONS_JSON.getPath(), Skin.class, new SkinLoader.SkinParameter(
				Resources.UI_BUTTONS_PACK.getPath()));

		game.assetManager.load(Resources.BACKGROUNDS_MENU.getPath(), Texture.class, param);
		game.assetManager.load(Resources.BACKGROUNDS_POTATOES.getPath(), Texture.class, param);

		game.assetManager.load(Resources.ANIMATIONS_PACK_BUG.getPath(), TextureAtlas.class);
		game.assetManager.load(Resources.ANIMATIONS_PACK_SPOTGLOW.getPath(), TextureAtlas.class);
		game.assetManager.load(Resources.ANIMATIONS_PACK_VICTORYGLOW.getPath(), TextureAtlas.class);
		
		game.assetManager.load(Resources.TILES_SARCOPHAGUS.getPath(), Texture.class, param);
		game.assetManager.load(Resources.TILES_WALL.getPath(), Texture.class, param);
		game.assetManager.load(Resources.TILES_FREE.getPath(), Texture.class, param);
		game.assetManager.load(Resources.TILES_SPOTON.getPath(), Texture.class, param);
		game.assetManager.load(Resources.TILES_SPOTOFF.getPath(), Texture.class, param);

		game.assetManager.load(Resources.METAITEMS_TOPBAR.getPath(), Texture.class, param);
		game.assetManager.load(Resources.METAITEMS_PAD.getPath(), Texture.class, param);
		game.assetManager.load(Resources.METAITEMS_VICTORYWING.getPath(), Texture.class, param);
		
		game.assetManager.load(Resources.LABELS_FINISH.getPath(), Texture.class, param);	

		game.assetManager.load(Resources.SOUND_BUGMOVE.getPath(), Sound.class);
		game.assetManager.load(Resources.SOUND_BUTTONCLICK.getPath(), Sound.class);
		game.assetManager.load(Resources.SOUND_SARCOPHAGUSMOVE.getPath(), Sound.class);
		game.assetManager.load(Resources.SOUND_VICTORY.getPath(), Sound.class);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);

		pasedTimeCounter += delta;
		if (game.assetManager.update()) {
			if (pasedTimeCounter > titleScreenMinDuration) {
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
		game.assetManager.unload(Resources.BACKGROUNDS_TITLE.getPath());
		game.titleScreen = null;
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
