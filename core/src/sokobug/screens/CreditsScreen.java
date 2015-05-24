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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class CreditsScreen implements Screen, InputProcessor {

	private Sokobug game;
	private BitmapFont font32;
	private BitmapFont font60;
	private Stage stage;
	private MenuButton backToMenu;
	private MenuButton sound;
	private Sprite background;
	private InputMultiplexer multiplexer;

	public CreditsScreen(Sokobug myGame) {
		game = myGame;
		stage = new Stage(game.viewport);
		multiplexer = new InputMultiplexer();

		font32 = game.assetManager.get("fonts/Japonesa32.fnt", BitmapFont.class);
		font60 = game.assetManager.get("fonts/Japonesa60.fnt", BitmapFont.class);
		background = new Sprite(game.assetManager.get("backgrounds/potatoes.png", Texture.class));
		background.setPosition(0, 0);

		backToMenu = new MenuButton(game, "", MenuButton.BACKTOMENU, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "menu-back");
		backToMenu.setPosition(0, 0);
		sound = new MenuButton(game, "", MenuButton.SOUNDONOFF, game.assetManager.get("ui/buttons/buttons.json",
				Skin.class), "soundOn");
		sound.setPosition(game.VIRTUAL_WIDTH - sound.getWidth() * 3.f / 2.f, game.VIRTUAL_HEIGHT - sound.getHeight()
				* 3.f / 2.f);

		stage.addActor(backToMenu);
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

		font32.setColor(Color.BLACK);
		font60.setColor(Color.BLACK);
		String text = "Lead programming and game design: Bogdan Guranda\n"
				+ "Graphics and Audio: Andrei Guranda\n"
				+ "Additional programming: Ciprian Corvin Tiperciuc\n"
				+ "Testing: Ciprian Corvin Tiperciuc, Clapa Lucian, Andrei Guranda\n"
				+ "Composers (newgrounds): IPSBLT, Sephirot24, slaleky, Inoni_Bird, mhb";
		

		game.batch.begin();
		background.draw(game.batch);
		font32.drawMultiLine(game.batch, text, backToMenu.getX() + backToMenu.getWidth(),
				font32.getBounds(text).height * 6.7f);
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
