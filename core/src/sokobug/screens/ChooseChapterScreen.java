package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.MenuButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class ChooseChapterScreen implements Screen, InputProcessor {

	public Sokobug game;

	private Stage stage;
	private MenuButton backToMenu;
	private MenuButton chapter1;
	private MenuButton sound;
	private Sprite background;
	private InputMultiplexer multiplexer;

	public ChooseChapterScreen(Sokobug myGame) {
		game = myGame;
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();

		background = new Sprite(game.assetManager.get("backgrounds/menu.png", Texture.class));
		backToMenu = new MenuButton(game, "", MenuButton.BACKTOMENU, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "menu-back");
		backToMenu.setPosition(0, 0);

		chapter1 = new MenuButton(game, "", MenuButton.CHAPTER, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "default-chapter1");
		chapter1.setPosition(chapter1.getWidth() / 2.f, game.VIRTUAL_HEIGHT - chapter1.getHeight() * 3.f / 2.f);
		sound = new MenuButton(game, "", MenuButton.SOUNDONOFF, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "soundOn");
		sound.setPosition(game.VIRTUAL_WIDTH - sound.getWidth() * 3.f / 2.f, game.VIRTUAL_HEIGHT - sound.getHeight()
				* 3.f / 2.f);

		stage.addActor(backToMenu);
		stage.addActor(chapter1);
		stage.addActor(sound);

		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
	}

	@Override
	public void render(float delta) {
		game.soundManager.updateMusicState();

		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);

		game.batch.begin();
		background.draw(game.batch);
		game.batch.end();

		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f, game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		if (game.soundManager.isMuted()) {
			sound.setStyle(game.assetManager.get("ui/buttons/buttons.json", Skin.class).get("soundOff",
					TextButtonStyle.class));
		} else {
			sound.setStyle(game.assetManager.get("ui/buttons/buttons.json", Skin.class).get("soundOn",
					TextButtonStyle.class));
		}

		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
