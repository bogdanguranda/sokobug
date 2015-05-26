package sokobug.screens;

import sokobug.Sokobug;
import sokobug.domain.MenuButton;
import sokobug.domain.SoundManager;

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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenuScreen implements Screen, InputProcessor {
	private Table table;
	private Stage stage;
	private Sprite background;
	private InputMultiplexer multiplexer;

	private Sokobug game;

	private MenuButton play;
	private MenuButton credits;
	private MenuButton exit;
	private MenuButton sound;
	private boolean firstStart = true;

	public MainMenuScreen(Sokobug game) {
		this.game = game;
		table = new Table();
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();

		background = new Sprite(game.assetManager.get("backgrounds/menu.png", Texture.class));
		play = new MenuButton(game, "Play", MenuButton.Type.PLAY, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "default-menu");
		credits = new MenuButton(game, "Credits", MenuButton.Type.CREDITS, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "default-menu");
		exit = new MenuButton(game, "Exit", MenuButton.Type.EXIT, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "default-menu");
		sound = new MenuButton(game, "", MenuButton.Type.SOUNDONOFF, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "soundOn");
		sound.setPosition(game.VIRTUAL_WIDTH - sound.getWidth() * 3.f / 2.f, game.VIRTUAL_HEIGHT - sound.getHeight()
				* 3.f / 2.f);

		table.add(play).padBottom(20).row();
		table.add(credits).padBottom(20).row();
		table.add(exit).padBottom(20).row();
		table.setFillParent(true);

		stage.addActor(sound);
		stage.addActor(table);

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
	public void resize(int width, int height) {
		game.viewport.update(width, height);
		game.camera.position.set(game.VIRTUAL_WIDTH / 2.f, game.VIRTUAL_HEIGHT / 2.f, 0.f);
	}

	@Override
	public void show() {
		if (firstStart) {
			game.soundManager = new SoundManager(game.assetManager);
			game.soundManager.startPlayingMusic();
			firstStart = false;
		}
		
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
	public void dispose() {
		stage.dispose();
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
